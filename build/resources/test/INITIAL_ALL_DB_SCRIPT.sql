INSERT INTO users (id, name, email, password)
VALUES (1,'John Doe', 'example.com', 'password123'),
       (2,'Jane Smith', 'example.com', 'password456');

INSERT INTO tag (id, name)
VALUES (1, 'food'),
       (2, 'spa');

INSERT INTO gift_certificates (id, name, description, price, duration, create_date, last_update_date, tag_id)
VALUES (1, 'Restaurant Voucher', 'Enjoy a meal at a local restaurant', 50.0, 365, '2022-05-10 13:00:00', '2022-05-10 13:00:00', 1),
       (2, 'Grocery Store Gift Card', 'Stock up on groceries for the week', 100.0, 30, '2022-05-10 14:00:00', '2022-05-10 14:00:00', 1);

INSERT INTO gift_certificates (id, name, description, price, duration, create_date, last_update_date, tag_id)
VALUES (3, 'Massage Session', 'Relax with a one-hour massage', 75.0, 60, '2022-05-10 13:00:00', '2022-05-10 13:00:00', 2),
       (4, 'Spa Package', 'Treat yourself to a day of pampering', 200.0, 180, '2022-05-10 14:00:00', '2022-05-10 14:00:00', 2);

INSERT INTO orders (id, cost, purchase_time, user_id, gift_certificates_id)
SELECT 1, 20.0, '2022-05-10 13:00:00', id, 1
FROM users
WHERE id = 1;

INSERT INTO orders (id, cost, purchase_time, user_id, gift_certificates_id)
SELECT 2, 15.0, '2022-05-10 14:00:00', id, 2
FROM users
WHERE id = 2;
