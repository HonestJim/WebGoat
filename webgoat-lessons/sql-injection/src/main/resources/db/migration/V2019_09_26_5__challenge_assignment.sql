CREATE TABLE sql_challenge_users(
  userid varchar(250),
  email varchar(30),
  password varchar(30)
);

INSERT INTO sql_challenge_users VALUES ('larry', 'larry@webgoat.org', '<hashed-password-1>');
INSERT INTO sql_challenge_users VALUES ('tom', 'tom@webgoat.org', '<hashed-password-2>');
INSERT INTO sql_challenge_users VALUES ('alice', 'alice@webgoat.org', '<hashed-password-3>');
INSERT INTO sql_challenge_users VALUES ('eve', 'eve@webgoat.org', '<hashed-password-4>');