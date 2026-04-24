--Challenge 5 - Creating tables for users
CREATE TABLE challenge_users(
  userid varchar(250),
  email varchar(30),
  password_hash varchar(60)
);

-- Insert users with hashed passwords and do not expose real credentials
-- Example: INSERT INTO challenge_users VALUES ('larry', 'larry@webgoat.org', '$2b$12$...');