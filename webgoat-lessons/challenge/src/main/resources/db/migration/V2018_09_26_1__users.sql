--Challenge 5 - Creating tables for users
CREATE TABLE challenge_users(
  userid varchar(250),
  email varchar(30),
  password varchar(30)
);

-- Store hashed passwords instead:
-- INSERT INTO challenge_users VALUES ('larry', 'larry@webgoat.org', '<bcrypt_hash>');