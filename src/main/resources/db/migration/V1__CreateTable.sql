-- V1__create_table_produtos.sql
CREATE TABLE produtos (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       descricao VARCHAR(500) NOT NULL
);
