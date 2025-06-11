drop table IF EXISTS pricing_plans;

CREATE TABLE IF NOT EXISTS pricing_plans
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    price    DECIMAL      NOT NULL,
    features JSON         NOT NULL,
    CONSTRAINT pk_pricing_plan PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS partner_requests
(
    id        SERIAL       NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname  VARCHAR(255) NOT NULL,
    company   VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    country   VARCHAR(255) NOT NULL,
    phone     VARCHAR(255) NOT NULL,
    message   VARCHAR(255),
    consent   BOOLEAN      NOT NULL,
    CONSTRAINT pk_partner_request PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email),
    CONSTRAINT uq_company UNIQUE (company)
);