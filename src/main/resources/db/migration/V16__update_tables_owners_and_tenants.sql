-- alterando tabela de tenants
ALTER TABLE tenants ADD COLUMN email VARCHAR(100);
ALTER TABLE tenants ADD COLUMN phone VARCHAR(14);

-- alterando tabela de owners
ALTER TABLE owners ADD COLUMN email VARCHAR(100);
ALTER TABLE owners ADD COLUMN phone VARCHAR(14);
