-- Remover triggers existentes
DROP TRIGGER IF EXISTS trigger_inserir_em_tabela_correta ON users;
DROP TRIGGER IF EXISTS trigger_atualizar_tabelas_corretas ON users;
DROP TRIGGER IF EXISTS trigger_deletar_tabelas_corretas ON users;
DROP TRIGGER IF EXISTS sync_user_data_trigger ON users;

CREATE OR REPLACE FUNCTION sync_user_data()
RETURNS TRIGGER AS $$
BEGIN
    -- Inserção
    IF TG_OP = 'INSERT' THEN
        IF NEW.role = 'TENANT' THEN
            INSERT INTO tenants (user_id, name, cpf, email, phone, active)
            VALUES (NEW.id, NEW.name, NEW.cpf, NEW.email, NEW.phone, TRUE);
        ELSIF NEW.role = 'OWNER' THEN
            INSERT INTO owners (user_id, name, cpf, email, phone, active)
            VALUES (NEW.id, NEW.name, NEW.cpf, NEW.email, NEW.phone, TRUE);
        END IF;
    END IF;

    -- Atualização de campos
    IF TG_OP = 'UPDATE' THEN
        IF NEW.role = 'TENANT' THEN
            UPDATE tenants
            SET name = NEW.name,
                cpf = NEW.cpf,
                email = NEW.email,
                phone = NEW.phone
            WHERE user_id = NEW.id;
        ELSIF NEW.role = 'OWNER' THEN
            UPDATE owners
            SET name = NEW.name,
                cpf = NEW.cpf,
                email = NEW.email,
                phone = NEW.phone
            WHERE user_id = NEW.id;
        END IF;
    END IF;

    -- Exclusão lógica
    IF TG_OP = 'DELETE' THEN
        IF OLD.role = 'TENANT' THEN
            UPDATE tenants
            SET active = FALSE
            WHERE user_id = OLD.id;
        ELSIF OLD.role = 'OWNER' THEN
            UPDATE owners
            SET active = FALSE
            WHERE user_id = OLD.id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criação do Trigger
CREATE TRIGGER sync_user_data_trigger
AFTER INSERT OR UPDATE OR DELETE ON users
FOR EACH ROW
EXECUTE FUNCTION sync_user_data();

