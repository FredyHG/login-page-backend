CREATE TABLE tb_token (
    id UUID PRIMARY KEY,
    token VARCHAR(255),
    token_type VARCHAR(20),
    revoked BOOLEAN DEFAULT FALSE,
    expired BOOLEAN DEFAULT FALSE,
    account_id UUID,
    FOREIGN KEY (account_id) REFERENCES tb_account(id)
);