DROP TABLE IF EXISTS pricing_plans CASCADE;
DROP TABLE IF EXISTS partner_requests CASCADE;
DROP TABLE IF EXISTS regions CASCADE;
DROP TABLE IF EXISTS partners CASCADE;

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
    approved   BOOLEAN      NOT NULL,
    CONSTRAINT pk_partner_request PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email),
    CONSTRAINT uq_company UNIQUE (company)
);


CREATE TABLE regions
(
    id   SERIAL       NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_region_id PRIMARY KEY (id)
);

CREATE TABLE partners
(
    id        SERIAL       NOT NULL PRIMARY KEY,
    company   VARCHAR(255) NOT NULL,
    country   VARCHAR(255) NOT NULL,
    number    VARCHAR(255) NOT NULL,
    website   VARCHAR(255) NOT NULL,
    img_url   TEXT         NOT NULL,
    region_id INT          NOT NULL,
    CONSTRAINT fk_region_key FOREIGN KEY (region_id) REFERENCES regions (id) on DELETE CASCADE
);
