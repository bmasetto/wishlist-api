#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

    CREATE DATABASE wishlist;

    \c wishlist

    CREATE TABLE customers (
      id                  BIGINT CONSTRAINT pk_customer PRIMARY KEY,
      email               VARCHAR UNIQUE NOT NULL,
      name                VARCHAR NOT NULL
    );
    CREATE SEQUENCE customers_seq START 1;
    CREATE UNIQUE INDEX customers_email_idx ON customers (email);

    CREATE TABLE wishlist (
      id                  BIGINT CONSTRAINT pk_wishlist PRIMARY KEY,
      customer_id         BIGINT NOT NULL,
      external_product_id VARCHAR NOT NULL
    );
    CREATE SEQUENCE wishlist_seq START 1;
    CREATE INDEX wishlist_customer_id_idx ON wishlist (customer_id);

    CREATE USER wishlist;
    ALTER USER wishlist PASSWORD '12345';
    GRANT ALL PRIVILEGES ON DATABASE wishlist TO wishlist;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO wishlist;
    GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO wishlist;

EOSQL