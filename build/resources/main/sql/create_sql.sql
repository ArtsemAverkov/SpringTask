CREATE TABLE IF NOT EXISTS tag (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS giftCertificates (
                                                id SERIAL PRIMARY KEY,
                                                name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    duration  BIGINT,
    create_date VARCHAR(255) NOT NULL,
    last_update_date VARCHAR(255) NOT NULL,
    tag_id BIGINT REFERENCES tag(id) ON DELETE SET NULL);