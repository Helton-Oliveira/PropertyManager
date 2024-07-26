-- V11__add_active_column.sql
ALTER TABLE users ADD COLUMN active BOOLEAN DEFAULT true;
ALTER TABLE tenants ADD COLUMN active BOOLEAN DEFAULT true;
ALTER TABLE owners ADD COLUMN active BOOLEAN DEFAULT true;

UPDATE users SET active = TRUE;
UPDATE tenants SET active = TRUE;
UPDATE owners SET active = TRUE;
