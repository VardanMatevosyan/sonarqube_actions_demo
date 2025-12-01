CREATE TABLE users (
   id INT PRIMARY KEY,
   username VARCHAR2(255),
   email VARCHAR2(255)
);

INSERT INTO users(id, username, email)
VALUES  (1, 'Alice', 'alice@example.com'),
        (2, 'Bob', 'bob@example.com'),
        (3, 'Admin', 'admin@example.com');