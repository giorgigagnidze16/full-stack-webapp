drop table IF EXISTS pricing_plans;

CREATE TABLE IF NOT EXISTS pricing_plans
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    price    DECIMAL      NOT NULL,
    features JSON         NOT NULL,
    CONSTRAINT pk_pricing_plan PRIMARY KEY (id)
);
