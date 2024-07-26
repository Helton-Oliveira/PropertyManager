-- Removendo o trigger existente
DROP TRIGGER IF EXISTS trigger_inserir_em_tabela_correta ON users;

-- Criando ou substituindo a função que insere nas tabelas corretas
CREATE OR REPLACE FUNCTION inserir_em_tabela_correta()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.role = 'TENANT' THEN
        INSERT INTO tenants (user_id, name, cpf) VALUES (NEW.id, NEW.name, NEW.cpf);
    ELSIF NEW.role = 'OWNER' THEN
        INSERT INTO owners (user_id, name, cpf) VALUES (NEW.id, NEW.name, NEW.cpf);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criando o trigger que chama a função após a inserção na tabela users
CREATE TRIGGER trigger_inserir_em_tabela_correta
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION inserir_em_tabela_correta();
