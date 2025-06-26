CREATE TABLE tb_orders (
    order_id SERIAL PRIMARY KEY,
    external_id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_document VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,

    billing_street VARCHAR(255) NOT NULL,
    billing_number VARCHAR(50) NOT NULL,
    billing_city VARCHAR(100) NOT NULL,
    billing_state VARCHAR(100) NOT NULL,
    billing_zip VARCHAR(20) NOT NULL,

    shipping_street VARCHAR(255) NOT NULL,
    shipping_number VARCHAR(50) NOT NULL,
    shipping_city VARCHAR(100) NOT NULL,
    shipping_state VARCHAR(100) NOT NULL,
    shipping_zip VARCHAR(20) NOT NULL,

    total NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE tb_order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES tb_orders(order_id) ON DELETE CASCADE,

    product_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price NUMERIC(12, 2) NOT NULL
);