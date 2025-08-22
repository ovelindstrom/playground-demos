-- 02-create-supplier-table.sql
-- Creates the SUPPLIER table for PostgreSQL

CREATE TABLE supplier (
    s_suppkey    INTEGER PRIMARY KEY,
    s_name       CHAR(25) NOT NULL,
    s_address    VARCHAR(40) NOT NULL,
    s_nationkey  INTEGER NOT NULL,
    s_phone      CHAR(15) NOT NULL,
    s_acctbal    DECIMAL NOT NULL,
    s_comment    VARCHAR(101) NOT NULL,
    FOREIGN KEY (s_nationkey) REFERENCES nation(n_nationkey)
);
