-- 04-create-customer-table.sql
-- Creates the CUSTOMER table for PostgreSQL

CREATE TABLE customer (
    c_custkey     INTEGER PRIMARY KEY,
    c_name        VARCHAR(25) NOT NULL,
    c_address     VARCHAR(40) NOT NULL,
    c_nationkey   INTEGER NOT NULL,
    c_phone       CHAR(15) NOT NULL,
    c_acctbal     DECIMAL NOT NULL,
    c_mktsegment  CHAR(10) NOT NULL,
    c_comment     VARCHAR(117) NOT NULL,
    FOREIGN KEY (c_nationkey) REFERENCES nation(n_nationkey)
);
