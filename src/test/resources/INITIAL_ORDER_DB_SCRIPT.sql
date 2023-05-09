INSERT INTO users (name, email, password)
VALUES ('John Doe', 'example.com', 'password123'),
       ('Jane Smith', 'example.com', 'password456');

INSERT INTO orders (cost, purchase_time, user_id, gift_certificates_id)
SELECT 20.0, '2022-05-10 13:00:00', id, 1
FROM users
WHERE name = 'John Doe';

INSERT INTO orders (cost, purchase_time, user_id, gift_certificates_id)
SELECT 15.0, '2022-05-10 14:00:00', id, 2
FROM users
WHERE name = 'Jane Smith';

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date, tag_id)
VALUES ('Restaurant Voucher', 'Enjoy a meal at a local restaurant', 50.0, 365, '2022-05-10 13:00:00', '2022-05-10 13:00:00', 1),
       ('Grocery Store Gift Card', 'Stock up on groceries for the week', 100.0, 30, '2022-05-10 14:00:00', '2022-05-10 14:00:00', 1);

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date, tag_id)
VALUES ('Massage Session', 'Relax with a one-hour massage', 75.0, 60, '2022-05-10 13:00:00', '2022-05-10 13:00:00', 2),
       ('Spa Package', 'Treat yourself to a day of pampering', 200.0, 180, '2022-05-10 14:00:00', '2022-05-10 14:00:00', 2);

INSERT INTO tags (name)
VALUES ('food'),
       ('spa');