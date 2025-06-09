
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
        }')


