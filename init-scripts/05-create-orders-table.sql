-- 05-create-orders-table.sql
-- Creates the ORDERS table for PostgreSQL

CREATE TABLE orders (
    o_orderkey       INTEGER PRIMARY KEY,
    o_custkey        INTEGER NOT NULL,
    o_orderstatus    CHAR(1) NOT NULL,
    o_totalprice     DECIMAL NOT NULL,
    o_orderdate      DATE NOT NULL,
    o_orderpriority  CHAR(15) NOT NULL,
    o_clerk          CHAR(15) NOT NULL,
    o_shippriority   INTEGER NOT NULL,
    o_comment        VARCHAR(79) NOT NULL,
    FOREIGN KEY (o_custkey) REFERENCES customer(c_custkey)
);
