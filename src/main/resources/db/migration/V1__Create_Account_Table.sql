CREATE TABLE tb_account (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    logradouro VARCHAR(255),
    state VARCHAR(50),
    city VARCHAR(50),
    role varchar(20)
);