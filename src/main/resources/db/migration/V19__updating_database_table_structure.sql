-- Deletando as tabelas admins, owners e tenants se existirem
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS owners CASCADE;
DROP TABLE IF EXISTS tenants CASCADE;
DROP TABLE IF EXISTS contracts CASCADE;
DROP TABLE IF EXISTS properties CASCADE;

-- Criação da tabela 'users' com discriminação para a herança
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('TENANT', 'OWNER', 'ADMIN')),
    active BOOLEAN DEFAULT TRUE
);

-- Criação da tabela 'properties' com relacionamento de muitos-para-um com 'owners'
CREATE TABLE properties (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(255),
    typeProperty VARCHAR(20) NOT NULL,
    rental_value VARCHAR(255),
    description TEXT,
    rented BOOLEAN,
    owner_id BIGINT REFERENCES users(id)  -- Relacionamento com Owner
);

-- Criação da tabela 'contracts' com relacionamentos de muitos-para-um com 'tenants' e 'properties'
CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT REFERENCES users(id),  -- Relacionamento com Tenant
    property_id BIGINT REFERENCES properties(id),  -- Relacionamento com Property
    status BOOLEAN,
    negotiated_price VARCHAR(255),
    created TIMESTAMP,
    validity DATE
);
