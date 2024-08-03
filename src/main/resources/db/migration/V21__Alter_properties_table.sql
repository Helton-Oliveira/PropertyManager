-- Remover a coluna `address`
ALTER TABLE properties
DROP COLUMN address;

-- Adicionar as novas colunas do embeddable `Addres`
ALTER TABLE properties
ADD COLUMN publicPlace VARCHAR(255),
ADD COLUMN neighborhood VARCHAR(255),
ADD COLUMN zipCode VARCHAR(20),
ADD COLUMN number VARCHAR(20),
ADD COLUMN city VARCHAR(100),
ADD COLUMN uf VARCHAR(2);
