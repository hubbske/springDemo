create database ecommerce;
\connect ecommerce;

CREATE TABLE products (
    sku VARCHAR(12) PRIMARY KEY NOT NULL,
    brand VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    CONSTRAINT valid_sku CHECK (sku ~ '^[a-z]{4}-[0-9]{3}-[0-9]{3}$')
);
