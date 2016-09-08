DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS phones;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS friend_requests;

CREATE TABLE accounts(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  lastname VARCHAR(25),
  email VARCHAR(65) UNIQUE NOT NULL,
  password VARCHAR(45) NOT NULL,
  home_address VARCHAR(255),
  work_address VARCHAR(255),
  birth_date DATE,
  icq VARCHAR(35),
  skype VARCHAR(45),
  add_info VARCHAR(255),
  registration_date DATE,
  role VARCHAR(15) NOT NULL,
  img BLOB,
  CONSTRAINT role_check CHECK (role='ADMIN' OR role='USER')
);

CREATE TABLE phones (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  phone VARCHAR(15) NOT NULL,
  owner_id INT NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES accounts(id)
);

CREATE TABLE friends (
  acc_id INT NOT NULL,
  friend_id INT NOT NULL,
  FOREIGN KEY (acc_id) REFERENCES accounts (id),
  FOREIGN KEY (friend_id) REFERENCES accounts (id)
);

CREATE TABLE messages(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  message VARCHAR(255) NOT NULL NULL,
  creating_date DATETIME NOT NULL,
  sender_id INT NOT NULL,
  receiver_id INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  is_read BIT(1) NOT NULL,
  FOREIGN KEY (sender_id) REFERENCES accounts(id),
  FOREIGN KEY (receiver_id) REFERENCES accounts(id)
);

CREATE TABLE friend_requests (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  acc_id INT NOT NULL,
  friend_id INT NOT NULL,
  FOREIGN KEY (acc_id) REFERENCES accounts (id),
  FOREIGN KEY (friend_id) REFERENCES accounts (id)
);



INSERT INTO accounts (id, name, lastname, email, password, home_address, work_address, birth_date, icq, skype, add_info, registration_date, role, img)
VALUES  (1, 'Daenerys', 'Targaryen', 'dt@gmail.com', 'motherOfDragons', 'Dragonstone 11b',
            'Dragonstone 11b', '1991-02-11', NULL, NULL, 'widow', '2015-11-12', 'ADMIN', NULL);

INSERT INTO accounts (id, name, lastname, email, password, home_address, work_address, birth_date, icq, skype, add_info, registration_date, role, img)
VALUES  (2, 'Artem', 'Vel', 'av@gmail.com', '1234', 'Dragonstone 11b', 'Dragonstone 11b',
            '1991-11-17', NULL, NULL, 'not merried', '2015-11-12', 'ADMIN', NULL);

INSERT INTO accounts (id, name, lastname, email, password, home_address, work_address, birth_date, icq, skype, add_info, registration_date, role, img)
VALUES  (3, 'Andrei', 'Tolstyi', 'at@gmail.com', '1234', 'Dragonstone 11b', 'Dragonstone 11b',
            '1991-02-11', NULL, NULL, 'not m', '2015-11-12', 'USER', NULL);

INSERT INTO accounts (id, name, lastname, email, password, home_address, work_address, birth_date, icq, skype, add_info, registration_date, role, img)
VALUES  (4, 'Antuan', 'Grizmann', 'ag@gmail.com', '1234', 'Dragonstone 11b', 'Dragonstone 11b',
            '1991-02-11', NULL, NULL, 'not m', '2015-11-12', 'USER', NULL);

INSERT INTO accounts (id, name, lastname, email, password, home_address, work_address, birth_date, icq, skype, add_info, registration_date, role, img)
VALUES  (5, 'Andrei', 'Shevchenko', 'asheva@gmail.com', '1234', 'Dragonstone 11b', 'Dragonstone 11b',
            '1991-02-11', NULL, NULL, 'not m', '2015-11-12', 'USER', NULL);





INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (1, '2016-08-24 16:13:00', 1, 2, 'Hello', 'ACC_PRIVATE', TRUE);

INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (2, '2016-08-24 16:14:00', 2, 1, 'Hello, who are u?', 'ACC_PRIVATE', TRUE );

INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (3, '2016-08-24 16:15:00', 1, 2, 'We study together, remember?', 'ACC_PRIVATE', FALSE);

INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (4, '2016-08-24 16:17:00', 2, 1, 'Actually no, but... let me think', 'ACC_PRIVATE', FALSE);

INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (7, '2016-08-24 16:17:00', 2, 1, 'Check ACC_PRIVATE UNREAD MSG', 'ACC_PRIVATE', FALSE);



INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (5, '2016-08-24 16:17:00', 4, 3, 'wall message', 'ACC_WALL', FALSE);
INSERT INTO messages (id, creating_date, sender_id, receiver_id, message, type, is_read)
VALUES (6, '2016-08-24 16:17:00', 4, 3, 'wall message2', 'ACC_WALL', FALSE);


INSERT INTO friend_requests (id ,acc_id, friend_id) VALUES (1, 1, 2);
INSERT INTO friend_requests (id, acc_id, friend_id) VALUES (2, 2, 4);
INSERT INTO friend_requests (id, acc_id, friend_id) VALUES (3, 4, 1);
INSERT INTO friend_requests (id, acc_id, friend_id) VALUES (4, 3, 1);
INSERT INTO friend_requests (id, acc_id, friend_id) VALUES (5, 4, 2);