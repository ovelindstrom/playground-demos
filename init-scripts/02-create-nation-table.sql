-- 07-create-nation-table.sql
-- Creates the NATION table for PostgreSQL

CREATE TABLE nation (
    n_nationkey  INTEGER PRIMARY KEY,
    n_name       CHAR(25) NOT NULL,
    n_regionkey  INTEGER NOT NULL,
    n_comment    VARCHAR(152) NOT NULL,
    FOREIGN KEY (n_regionkey) REFERENCES region(r_regionkey)
);
