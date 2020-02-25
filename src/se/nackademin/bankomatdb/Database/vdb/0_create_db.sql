DROP DATABASE IF EXISTS vo_bank;
CREATE DATABASE vo_bank;
USE vo_bank;

CREATE TABLE IF NOT EXISTS customer_data
(
    customer_id INT             NOT NULL AUTO_INCREMENT,
    personal_id CHAR(13) UNIQUE NOT NULL,
    pin         CHAR(4)         NOT NULL,
    name        VARCHAR(50)     NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS account_data
(
    id            INT    NOT NULL AUTO_INCREMENT,
    owner_id      INT    NOT NULL,
    balance       DOUBLE NOT NULL DEFAULT 0,
    interest_rate DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES customer_data (customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS loan_data
(
    id              INT    NOT NULL AUTO_INCREMENT,
    debtor_id       INT    NOT NULL,
    original_amount DOUBLE NOT NULL,
    granted         DATE   NOT NULL,
    deadline        DATE   NOT NULL,
    interest_rate   DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (debtor_id) REFERENCES customer_data (customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (DATEDIFF(deadline, granted) > 0),
    CHECK (original_amount > 0)
);

CREATE TABLE IF NOT EXISTS transaction_data
(
    id               INT       NOT NULL AUTO_INCREMENT,
    account_id       INT       NOT NULL,
    net_balance      DOUBLE    NOT NULL,
    transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account_data (id) ON DELETE CASCADE ON UPDATE CASCADE
);
