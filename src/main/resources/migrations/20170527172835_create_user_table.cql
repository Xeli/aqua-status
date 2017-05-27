-- description: create_user_table
-- authoredAt: 1495898915843
-- up:

CREATE TABLE user (
  userId uuid,
  name text,
  apiKey text,
  password text,
  PRIMARY KEY (userId)
);

-- down:

DROP TABLE user
