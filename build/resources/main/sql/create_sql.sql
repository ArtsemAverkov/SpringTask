CREATE TABLE IF NOT EXISTS tags (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS giftCertificates (
                                                id SERIAL PRIMARY KEY,
                                                name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    duration VARCHAR(255),
    create_date VARCHAR(255) NOT NULL,
    last_update_date VARCHAR(255) NOT NULL,
    tag_id BIGINT REFERENCES tags(id) ON DELETE SET NULL)