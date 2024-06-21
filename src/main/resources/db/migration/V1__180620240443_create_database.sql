CREATE TABLE IF NOT EXISTS cliente (
        id BIGSERIAL PRIMARY KEY,
        uuid UUID  NOT NULL,
        nome text NOT NULL,
        email text NOT NULL,
        ativo BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS tag (
       id BIGSERIAL PRIMARY KEY,
       nome text NOT NULL,
       ativo BOOLEAN NOT NULL
   );

CREATE TABLE IF NOT EXISTS cliente_tag (
        id BIGSERIAL PRIMARY KEY,
        cliente_id BIGINT NOT NULL,
        tag_id BIGINT NOT NULL,
        FOREIGN KEY (cliente_id) REFERENCES cliente(id),
        FOREIGN KEY (tag_id) REFERENCES tag(id)
    );