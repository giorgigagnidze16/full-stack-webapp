document.addEventListener("DOMContentLoaded", () => {
    const pendingList = document.getElementById("pending-list");
    const partnersList = document.getElementById("partners-list");

    const addPartnerModal = new bootstrap.Modal(document.getElementById("addPartnerModal"));
    const addPartnerForm = document.getElementById("add-partner-form");

    const approveModal = new bootstrap.Modal(document.getElementById("approveApplicationModal"));
    const approveForm = document.getElementById("approve-application-form");

    function getAuthHeaders(additionalHeaders = {}) {
        const credentials = localStorage.getItem("basicAuth");
        if (!credentials) {
            showToast('error', "Not authorized");
            window.location.href = "../index.html";
            return null;
        }
        return {
            Authorization: `Basic ${credentials}`,
            ...additionalHeaders,
        };
    }

    async function loadPendingApplications() {
        try {
            const res = await fetch("/api/admin/partner-applications", {
                headers: getAuthHeaders(),
            });
            if (!res.ok) throw new Error("Failed to load");

            const data = await res.json();

            if (!data.length) {
                pendingList.innerHTML = "<p>No pending applications.</p>";
                return;
            }

            pendingList.innerHTML = data
                .map(
                    (p) => `
          <div class="list-group-item">
            <strong>${p.firstname} ${p.lastname}</strong><br/>
            <small>${p.company} | ${p.country} | ${p.email}</small><br/>
            <p>${p.message}</p>
            <button class="btn btn-success btn-sm me-2" data-id="${p.id}" data-action="approve">Approve</button>
            <button class="btn btn-danger btn-sm" data-id="${p.id}" data-action="reject">Reject</button>
          </div>
        `
                )
                .join("");
        } catch {
            pendingList.innerHTML = "<p>Error loading pending applications.</p>";
        }
    }

    async function loadPartners() {
        try {
            const res = await fetch("/api/admin/partners", {
                headers: getAuthHeaders(),
            });
            if (!res.ok) throw new Error("Failed to load");

            const partners = await res.json();

            if (!partners.length) {
                partnersList.innerHTML = "<p>No partners found.</p>";
                return;
            }

            partnersList.innerHTML = partners
                .map(
                    (p) => `
          <div class="col">
            <div class="partner-card">
              <img src="${p.imgUrl || p.img}" alt="${p.company} logo" />
              <h5>${p.company}</h5>
              <p class="country">Country: ${p.country}</p>
              <p class="region">Region: ${p.region}</p>
              <p class="number">Phone: ${p.number}</p>
              <p class="website"><a href="${p.website}" target="_blank">${p.website}</a></p>
              <div class="partner-actions">
                <button class="btn btn-warning btn-sm" data-id="${p.id}" data-action="edit">Edit</button>
                <button class="btn btn-danger btn-sm" data-id="${p.id}" data-action="delete">Delete</button>
              </div>
            </div>
          </div>
        `
                )
                .join("");
        } catch {
            partnersList.innerHTML = "<p style='text-align: center'>Error loading partners.</p>";
        }
    }

    pendingList.addEventListener("click", (e) => {
        const btn = e.target;
        if (btn.tagName !== "BUTTON") return;

        const id = btn.dataset.id;
        const action = btn.dataset.action;

        if (action === "approve") {
            approveForm.reset();
            approveForm.elements["applicationId"].value = id;
            approveModal.show();
        } else if (action === "reject") {
            fetch(`/api/admin/partner-applications/${id}/reject`, {
                method: "POST",
                headers: getAuthHeaders(),
            })
                .then((res) => {
                    if (res.ok) {
                        showToast('success', "Application rejected");
                        loadPendingApplications();
                    } else {
                        showToast('error', "Error rejecting application");
                    }
                })
                .catch(() => showToast('error', "Network error"));
        }
    });

    approveForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const formData = new FormData(approveForm);
        const website = formData.get("website").trim();
        const region = formData.get("region").trim();
        const imgUrl = formData.get("imgUrl").trim();
        const applicationId = formData.get("applicationId");

        if (!website || !region || !imgUrl) {
            showToast('error', "All fields are required");
            return;
        }

        try {
            const res = await fetch(
                `/api/admin/partner-applications/${applicationId}/approve?website=${encodeURIComponent(
                    website
                )}&region=${encodeURIComponent(region)}&imgUrl=${encodeURIComponent(imgUrl)}`,
                {
                    method: "POST",
                    headers: getAuthHeaders(),
                }
            );

            if (res.ok) {
                showToast('success', "Application approved");
                approveModal.hide();
                loadPendingApplications();
                loadPartners();
            } else {
                showToast('error', "Error approving application");
            }
        } catch {
            showToast('error', "Network error");
        }
    });

    document.getElementById("btn-add-partner").addEventListener("click", () => {
        addPartnerForm.reset();
        addPartnerForm.elements["id"].value = "";
        addPartnerModal.show();
    });

    addPartnerForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const formData = new FormData(addPartnerForm);
        const partnerData = Object.fromEntries(formData.entries());

        const isEdit = partnerData.id && partnerData.id.trim() !== "";
        if (isEdit) delete partnerData.id;

        try {
            const url = isEdit
                ? `/api/admin/partners/${formData.get("id")}`
                : "/api/admin/partners";
            const method = isEdit ? "PUT" : "POST";

            const res = await fetch(url, {
                method,
                headers: getAuthHeaders({"Content-Type": "application/json"}),
                body: JSON.stringify(partnerData),
            });

            if (res.ok) {
                showToast('success', isEdit ? "Partner updated" : "Partner added");
                addPartnerModal.hide();
                loadPartners();
                addPartnerForm.reset();
                addPartnerForm.elements["id"].value = "";
            } else {
                const error = await res.text();
                showToast('error', "Error: " + error);
            }
        } catch {
            showToast('error', "Network error");
        }
    });

    partnersList.addEventListener("click", (e) => {
        const btn = e.target;
        if (btn.tagName !== "BUTTON") return;

        const id = btn.dataset.id;
        const action = btn.dataset.action;

        if (action === "delete") {
            fetch(`/api/admin/partners/${id}`, {
                method: "DELETE",
                headers: getAuthHeaders(),
            })
                .then((res) => {
                    if (res.ok) {
                        showToast('success', "Partner deleted");
                        loadPartners();
                    } else {
                        showToast('error', "Error deleting partner");
                    }
                })
                .catch(() => showToast('error', "Network error"));
        } else if (action === "edit") {
            const card = btn.parentElement.parentElement;

            if (!card) return;

            const company = card.querySelector("h5")?.textContent || "";
            const countryText = card.querySelector("p.country")?.textContent || "";
            const regionText = card.querySelector("p.region")?.textContent || "";
            const numberText = card.querySelector("p.number")?.textContent || "";
            const websiteLink = card.querySelector("p.website a")?.href || "";
            const imgUrl = card.querySelector("img")?.src || "";

            const country = countryText.split(":")[1]?.trim() || "";
            const region = regionText.split(":")[1]?.trim() || "";
            const number = numberText.split(":")[1]?.trim() || "";

            addPartnerForm.elements["id"].value = id;
            addPartnerForm.elements["company"].value = company;
            addPartnerForm.elements["country"].value = country;
            addPartnerForm.elements["region"].value = region;
            addPartnerForm.elements["number"].value = number;
            addPartnerForm.elements["website"].value = websiteLink;
            addPartnerForm.elements["imgUrl"].value = imgUrl;

            addPartnerModal.show();
        }
    });

    loadPendingApplications();
    loadPartners();
});

function showToast(type, message) {
    const container = document.getElementById('partnerToastContainer');

    const bgClass = (type === 'error') ? 'bg-danger' : 'bg-success';

    const toastEl = document.createElement('div');
    toastEl.className = `toast align-items-center text-white ${bgClass} border-0`;
    toastEl.setAttribute('role', 'alert');
    toastEl.setAttribute('aria-live', 'assertive');
    toastEl.setAttribute('aria-atomic', 'true');

    toastEl.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">
        ${message}
      </div>
      <button
        type="button"
        class="btn-close btn-close-white me-2 m-auto"
        data-bs-dismiss="toast"
        aria-label="Close"
      ></button>
    </div>
  `;

    container.appendChild(toastEl);
    const bsToast = new bootstrap.Toast(toastEl, {delay: 3000});
    bsToast.show();

    toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
}
