CREATE TABLE sql_challenge_users(
  userid varchar(250),
  email varchar(30),
  password varchar(30)
);

-- Use a strong hash for storing passwords, e.g.: 
INSERT INTO sql_challenge_users VALUES ('larry', 'larry@webgoat.org', '<bcrypt_hash_here>');
INSERT INTO sql_challenge_users VALUES ('tom', 'tom@webgoat.org', '<bcrypt_hash_here>');
INSERT INTO sql_challenge_users VALUES ('alice', 'alice@webgoat.org', '<bcrypt_hash_here>');
INSERT INTO sql_challenge_users VALUES ('eve', 'eve@webgoat.org', '<bcrypt_hash_here>');