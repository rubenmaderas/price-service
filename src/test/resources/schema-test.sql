CREATE TABLE IF NOT EXISTS prices
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    price_list INTEGER        NOT NULL,
    priority   INTEGER        NOT NULL,
    start_date TIMESTAMP      NOT NULL,
    end_date   TIMESTAMP      NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    currency   VARCHAR(10)    NOT NULL
    );

CREATE INDEX idx_prices_lookup
    ON prices (product_id, brand_id, start_date, end_date, priority);