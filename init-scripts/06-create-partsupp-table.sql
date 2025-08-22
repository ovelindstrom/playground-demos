-- 03-create-partsupp-table.sql
-- Creates the PARTSUPP table for PostgreSQL

CREATE TABLE partsupp (
    ps_partkey     INTEGER NOT NULL,
    ps_suppkey     INTEGER NOT NULL,
    ps_availqty    INTEGER NOT NULL,
    ps_supplycost  DECIMAL NOT NULL,
    ps_comment     VARCHAR(199) NOT NULL,
    PRIMARY KEY (ps_partkey, ps_suppkey),
    FOREIGN KEY (ps_partkey) REFERENCES part(p_partkey),
    FOREIGN KEY (ps_suppkey) REFERENCES supplier(s_suppkey)
);
