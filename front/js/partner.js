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


async function handleForm() {
    const requiredKeys = ['firstName', 'lastName', 'company', 'email', 'phone', 'country', 'consent'];

    const data = {
        firstName: document.getElementById('firstName').value.trim(),
        lastName: document.getElementById('lastName').value.trim(),
        company: document.getElementById('company').value.trim(),
        email: document.getElementById('email').value.trim(),
        phone: document.getElementById('phone').value.trim(),
        country: document.getElementById('country').value.trim(),
        message: document.getElementById('message').value.trim(),
        consent: document.getElementById('consent').checked
    };

    const invalid = requiredKeys.filter(key => {
        const val = data[key];
        return (typeof val === 'string' && val === '') || (typeof val === 'boolean' && !val);
    });

    if (invalid.length) {
        showToast(
            'error',
            `Please fill out: ${invalid.join(', ')}`
        );
        return;
    }

    const resp = await fetch('/api/partners', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data),
    });

    if (resp.ok) {
        showToast("Submitted!")
    } else {
        showToast('error', "Something went wrong! " + resp.statusText + " " + resp.status)
    }
}
