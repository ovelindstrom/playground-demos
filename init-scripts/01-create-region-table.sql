-- 08-create-region-table.sql
-- Creates the REGION table for PostgreSQL

CREATE TABLE region (
    r_regionkey  INTEGER PRIMARY KEY,
    r_name       CHAR(25) NOT NULL,
    r_comment    VARCHAR(152) NOT NULL
);
