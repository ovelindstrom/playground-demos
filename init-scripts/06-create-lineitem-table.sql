-- 06-create-lineitem-table.sql
-- Creates the LINEITEM table for PostgreSQL

CREATE TABLE lineitem (
    l_orderkey      INTEGER NOT NULL,
    l_partkey       INTEGER NOT NULL,
    l_suppkey       INTEGER NOT NULL,
    l_linenumber    INTEGER NOT NULL,
    l_quantity      DECIMAL NOT NULL,
    l_extendedprice DECIMAL NOT NULL,
    l_discount      DECIMAL NOT NULL,
    l_tax           DECIMAL NOT NULL,
    l_returnflag    CHAR(1) NOT NULL,
    l_linestatus    CHAR(1) NOT NULL,
    l_shipdate      DATE NOT NULL,
    l_commitdate    DATE NOT NULL,
    l_receiptdate   DATE NOT NULL,
    l_shipinstruct  CHAR(25) NOT NULL,
    l_shipmode      CHAR(10) NOT NULL,
    l_comment       VARCHAR(44) NOT NULL,
    PRIMARY KEY (l_orderkey, l_linenumber),
    FOREIGN KEY (l_orderkey) REFERENCES orders(o_orderkey),
    FOREIGN KEY (l_partkey) REFERENCES part(p_partkey),
    FOREIGN KEY (l_suppkey) REFERENCES supplier(s_suppkey),
    FOREIGN KEY (l_partkey, l_suppkey) REFERENCES partsupp(ps_partkey, ps_suppkey)
);
