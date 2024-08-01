-- Remover o trigger associado à função
DROP TRIGGER IF EXISTS sync_user_data_trigger ON users;

-- Remover a função de trigger
DROP FUNCTION IF EXISTS sync_user_data();

