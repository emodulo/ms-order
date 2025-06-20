CREATE TABLE tb_orders (
    id SERIAL PRIMARY KEY,

    customer_id VARCHAR(100) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_document VARCHAR(50) NOT NULL,

    billing_street VARCHAR(255),
    billing_number VARCHAR(50),
    billing_city VARCHAR(100),
    billing_state VARCHAR(50),
    billing_zip VARCHAR(20),

    shipping_street VARCHAR(255),
    shipping_number VARCHAR(50),
    shipping_city VARCHAR(100),
    shipping_state VARCHAR(50),
    shipping_zip VARCHAR(20),

    total NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE tb_order_items (
    order_id INTEGER NOT NULL REFERENCES tb_orders(id) ON DELETE CASCADE,
    product_id VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12, 2) NOT NULL
);