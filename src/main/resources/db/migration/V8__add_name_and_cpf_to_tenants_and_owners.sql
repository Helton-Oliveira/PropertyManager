-- Adicionando colunas name e cpf à tabela tenants
ALTER TABLE tenants ADD COLUMN name VARCHAR(100);
ALTER TABLE tenants ADD COLUMN cpf VARCHAR(14);

-- Adicionando colunas name e cpf à tabela owners
ALTER TABLE owners ADD COLUMN name VARCHAR(100);
ALTER TABLE owners ADD COLUMN cpf VARCHAR(14);
