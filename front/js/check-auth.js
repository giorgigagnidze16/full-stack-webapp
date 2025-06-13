async function checkAuth() {
    const credentials = localStorage.getItem('basicAuth');
    if (!credentials && !window.location.href.includes("login.html")) {
        alert('Not authorized');
        window.location.href = '../index.html';
        return;
    }

    try {
        const response = await fetch('/api/admin/login', {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${credentials}`
            }
        });

        if (!response.ok && !window.location.href.includes("login.html")) {
            alert('Not authorized');
            localStorage.removeItem('basicAuth');
            window.location.href = '../index.html';
        } else if (response.ok && window.location.href.includes("login.html")) {
            window.location.href = '../dashboard.html';
        }
    } catch (err) {
        alert('Server error, please try again later.');
        window.location.href = '../index.html';
    }
}

checkAuth();
