-- Criando a tabela users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    cpf VARCHAR(14),
    phone VARCHAR(15),
    role VARCHAR(50) -- Este campo define se é inquilino ou proprietário
);

-- Criando a tabela tenants
CREATE TABLE tenants (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Criando a tabela owners
CREATE TABLE owners (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Criando a função que será chamada pelo trigger
CREATE OR REPLACE FUNCTION inserir_em_tabela_correta()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.role = 'TENANT' THEN
        INSERT INTO tenants (user_id)
        VALUES (NEW.id);
    ELSIF NEW.role = 'OWNER' THEN
        INSERT INTO owners (user_id)
        VALUES (NEW.id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criando o trigger que chama a função após a inserção na tabela users
CREATE TRIGGER trigger_inserir_em_tabela_correta
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION inserir_em_tabela_correta();
