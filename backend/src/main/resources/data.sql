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
    ('Acme Corp', 'USA', '+1-555-123-4567', 'https://www.acmecorp.com', 'https://example.com/images/acme.png', 1),
    ('Globex Ltd', 'Canada', '+1-416-555-7890', 'https://www.globex.ca', 'https://example.com/images/globex.png', 2),
    ('Initech', 'UK', '+44-20-7946-0958', 'https://www.initech.co.uk', 'https://example.com/images/initech.png', 1),
    ('Umbrella Inc', 'Germany', '+49-30-1234-5678', 'https://www.umbrella.de', 'https://example.com/images/umbrella.png', 1),
    ('Stark Industries', 'USA', '+1-310-555-0110', 'https://www.starkindustries.com', 'https://example.com/images/stark.png', 2);

