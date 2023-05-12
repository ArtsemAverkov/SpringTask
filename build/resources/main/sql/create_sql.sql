CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       email TEXT NOT NULL,
                       password TEXT NOT NULL
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        cost DOUBLE PRECISION NOT NULL,
                        purchase_time TIMESTAMP NOT NULL,
                        user_id BIGINT NOT NULL,
                        gift_certificates_id BIGINT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        FOREIGN KEY (gift_certificates_id) REFERENCES gift_certificates(id) ON DELETE CASCADE
);

CREATE TABLE gift_certificates (
                                   id SERIAL PRIMARY KEY,
                                   name TEXT NOT NULL,
                                   description TEXT NOT NULL,
                                   price DOUBLE PRECISION NOT NULL,
                                   duration BIGINT NOT NULL,
                                   create_date TEXT NOT NULL,
                                   last_update_date TEXT NOT NULL,
                                   tag_id BIGINT NOT NULL,
                                   FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

CREATE TABLE tags (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL
);