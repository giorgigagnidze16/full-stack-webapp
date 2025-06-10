function renderPlan({name, features, price}) {
    const id = name.toLowerCase();
    const isPro = id === 'pro';

    const items = Object.entries(features)
        .map(
            ([feat, val]) => `
        <li>
          <img src="assets/icon/check-icon.png" alt="✓" class="feature-icon" />
          <span>${feat}: ${val}</span>
        </li>
      `
        )
        .join('');

    return `
    <div id="package-${id}" class="price${isPro ? ' pro' : ''}">
      <h2>${name}</h2>
      <div class="features-list-container">
        <ul class="features-list">
          ${items}
        </ul>
      </div>
      <div class="price-container">
        <button class="price-button">
          ${price === 0 ? 'Free' : `$${price.toFixed(2)}`}
        </button>
      </div>
    </div>
  `;
}


fetch('/api/prices')
    .then(res => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return res.json();
    })
    .then(({prices}) => {
        const container = document.querySelector('.pricing-container');
        container.innerHTML = prices.map(renderPlan).join('');
    })
    .catch(err => {
        console.error('Fetch error:', err);
    });