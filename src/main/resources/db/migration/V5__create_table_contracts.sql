CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT,
    property_id BIGINT,
    status BOOLEAN,
    negotiated_price VARCHAR(255),
    created TIMESTAMP,
    validity DATE,

    FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE SET NULL,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE SET NULL
);

CREATE INDEX idx_contracts_tenant_id ON contracts(tenant_id);
CREATE INDEX idx_contracts_property_id ON contracts(property_id);

