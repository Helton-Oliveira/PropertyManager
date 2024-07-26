-- Removendo os triggers existentes
DROP TRIGGER IF EXISTS trigger_inserir_em_tabela_correta ON users;
DROP TRIGGER IF EXISTS trigger_atualizar_tabelas_corretas ON users;

-- Criando ou substituindo a função que insere ou atualiza nas tabelas corretas
CREATE OR REPLACE FUNCTION inserir_ou_atualizar_em_tabelas_corretas()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.role = 'TENANT' THEN
            INSERT INTO tenants (user_id, name, cpf) VALUES (NEW.id, NEW.name, NEW.cpf);
        ELSIF NEW.role = 'OWNER' THEN
            INSERT INTO owners (user_id, name, cpf) VALUES (NEW.id, NEW.name, NEW.cpf);
        END IF;
    ELSIF TG_OP = 'UPDATE' THEN
        IF NEW.role = 'TENANT' THEN
            IF NEW.name IS NOT NULL THEN
                UPDATE tenants SET name = NEW.name WHERE user_id = NEW.id;
            END IF;
            IF NEW.cpf IS NOT NULL THEN
                UPDATE tenants SET cpf = NEW.cpf WHERE user_id = NEW.id;
            END IF;
        ELSIF NEW.role = 'OWNER' THEN
            IF NEW.name IS NOT NULL THEN
                UPDATE owners SET name = NEW.name WHERE user_id = NEW.id;
            END IF;
            IF NEW.cpf IS NOT NULL THEN
                UPDATE owners SET cpf = NEW.cpf WHERE user_id = NEW.id;
            END IF;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criando o trigger que chama a função após a inserção na tabela users
CREATE TRIGGER trigger_inserir_em_tabela_correta
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION inserir_ou_atualizar_em_tabelas_corretas();

-- Criando o trigger que chama a função após a atualização na tabela users
CREATE TRIGGER trigger_atualizar_tabelas_corretas
AFTER UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION inserir_ou_atualizar_em_tabelas_corretas();
