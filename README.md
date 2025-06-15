# WorkLinker

<h1>Demo: http://worklinker.xyz/ (I took the website down)</h1>

---

## Features

- **Admin Dashboard**
    - View and manage partner applications (approve/reject)
    - Add, edit, and delete partners by country and region
    - Secure login with Basic Authentication
    - Dynamic Pricing Plans, define plans from Admin Console (TBA)

- **Backend**
    - REST API built with Spring Boot
    - PostgreSQL database for persistence
    - Redis caching support

- **Frontend**
    - Responsive dashboard built with Bootstrap and vanilla JavaScript
    - Authentication integrated with localStorage and backend validation

- **Deployment**
    - Docker Compose configuration for backend, PostgreSQL, and Redis
    - Multi-Region deployment
    - NGINX Reverse Proxy
    - Load Balancer with CDN Cache
    - Fully automated CI\CD with GitHub Actions

<hr>

# Deployment Architecture
<img src="worklinker%20architecture.png" alt="architecture">
