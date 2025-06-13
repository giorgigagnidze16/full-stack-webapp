const form = document.getElementById('loginForm');
const loginError = document.getElementById('loginError');

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    loginError.style.display = 'none';

    const username = form.username.value.trim();
    const password = form.password.value.trim();
    const credentials = btoa(`${username}:${password}`);

    try {
        const response = await fetch('/api/admin/login', {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${credentials}`
            }
        });

        if (response.ok) {
            localStorage.setItem('basicAuth', credentials);
            window.location.href = "../dashboard.html";
        } else {
            loginError.textContent = 'Invalid username or password';
            loginError.style.display = 'block';
        }
    } catch (err) {
        loginError.textContent = 'Server error, please try again later.';
        loginError.style.display = 'block';
    }
});
