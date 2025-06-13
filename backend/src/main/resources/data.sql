INSERT INTO pricing_plans (name, price, features)
VALUES ('Basic',
        0.00,
        '{
          "Users": "1",
          "Workflows": "2",
          "Integrations": "2",
          "Automation Runs": "100/month",
          "Support": "Email support",
          "Custom Branding": "No"
        }');


INSERT INTO pricing_plans (name, price, features)
VALUES ('Pro',
        19.99,
        '{
          "Users": "5",
          "Workflows": "10",
          "Integrations": "10",
          "Automation Runs": "2,000/month",
          "Support": "Priority email support",
          "Custom Branding": "Yes"
        }');

INSERT INTO pricing_plans (name, price, features)
VALUES ('Enterprise',
        99.99,
        '{
          "Users": "Unlimited",
          "Workflows": "Unlimited",
          "Integrations": "Unlimited",
          "Automation Runs": "100,000/month",
          "Support": "24/7 Premium support",
          "Custom Branding": "Yes"
        }');

INSERT INTO regions (name)
VALUES ('EMEA'),
       ('AMERICAS');

INSERT INTO partners (company, country, number, website, img_url, region_id)
VALUES
    ('Acme Corp', 'USA', '+1-555-123-4567', 'https://www.acmecorp.com', 'https://static.acmecorp.com/media/logo/stores/1/acme-logo.png', 2),
    ('Globex Ltd', 'Canada', '+1-416-555-7890', 'https://www.globex.ca', 'https://mapandfire.com/wp-content/uploads/2022/09/booking-brand-logo.jpg', 2),
    ('Initech', 'UK', '+44-20-7946-0958', 'https://www.initech.co.uk', 'https://moottori.fi/wp-content/uploads/2021/11/21C0649_002-1328x747.jpg', 1),
    ('FireFox', 'Germany', '+49-30-1234-5678', 'https://www.mozilla.org/en-US/firefox/new/', 'https://www.mozilla.org/media/protocol/img/logos/firefox/browser/logo.eb1324e44442.svg', 1),
    ('Stark Industries', 'USA', '+1-310-555-0110', 'https://www.starkindustries.com', 'https://cdn.logojoy.com/wp-content/uploads/20240110153811/Red-tesla-logo-600x600.png', 2);

