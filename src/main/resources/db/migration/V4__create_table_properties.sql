CREATE TABLE properties (
    id BIGSERIAL PRIMARY KEY,
    public_place VARCHAR(255) NOT NULL,  -- Rua
    neighborhood VARCHAR(255) NOT NULL,  -- Bairro
    zip_code VARCHAR(10) NOT NULL,       -- CEP
    number VARCHAR(10) NOT NULL,         -- Número
    city VARCHAR(255) NOT NULL,          -- Cidade
    uf CHAR(2) NOT NULL,                 -- Estado (UF)
    type VARCHAR(50) NOT NULL,           -- Mapeia o enum `TypeProperty`
    rental_value VARCHAR(255),
    description TEXT,
    rented BOOLEAN,

    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);

