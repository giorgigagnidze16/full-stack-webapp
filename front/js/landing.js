fetch('/api/prices')
    .then(response => {
        if (!response.ok) throw new Error('Network response was not ok');
        return response.json();
    })
    .then(data => {
        console.log('Fetched data:', data);
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
