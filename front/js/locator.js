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

function createPartnerCard(partner) {
    const card = document.createElement('div');
    card.className = 'partner-card';

    card.innerHTML = `
    <img src="${partner.imgUrl}" alt="${partner.company} logo" class="partner-logo" />
    <div class="partner-info">
      <a href="${partner.website}" target="_blank" rel="noopener noreferrer" class="partner-company">${partner.company}</a>
      <div class="partner-details">
        <span class="partner-country"><i class="fas fa-globe"></i> ${partner.country}</span>
        <span class="partner-phone"><i class="fas fa-phone"></i> ${partner.number}</span>
        <span><i class="fas fa-link"></i> <a href="${partner.website}" target="_blank" rel="noopener noreferrer">${partner.website}</a></span>
      </div>
    </div>
  `;

    return card;
}

function groupByRegion(partners) {
    return partners.reduce((acc, partner) => {
        const region = partner.region || 'Unknown';
        if (!acc[region]) acc[region] = [];
        acc[region].push(partner);
        return acc;
    }, {});
}

async function loadAndRenderPartners() {
    const container = document.querySelector('.partners-list-container');
    container.innerHTML = '';

    try {
        const resp = await fetch('/api/partners');
        if (!resp.ok) {
            showToast('error', `Failed to load partners: ${resp.status}`);
            return;
        }

        const partners = await resp.json();
        const grouped = groupByRegion(partners);

        for (const [region, partnersInRegion] of Object.entries(grouped)) {
            const regionGroup = document.createElement('div');
            regionGroup.className = 'partner-region-group';

            const regionTitle = document.createElement('h2');
            regionTitle.className = 'partner-region-title';
            regionTitle.textContent = region;
            regionGroup.appendChild(regionTitle);

            partnersInRegion.forEach(partner => {
                const card = createPartnerCard(partner);
                regionGroup.appendChild(card);
            });

            container.appendChild(regionGroup);
        }

        showToast('success', 'Partners loaded successfully!');
    } catch (error) {
        showToast('error', `Error loading partners: ${error.message}`);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadAndRenderPartners();
});
