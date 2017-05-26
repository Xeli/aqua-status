-- description: Create Metrics tables
-- authoredAt: 1495699465000
-- up:

CREATE TABLE user (
  userId uuid,
  name text,
  PRIMARY KEY (userId)
);

CREATE TABLE metrics (
  userId uuid,
  timeAdded bigint,
  temperature double,
  waterLevel double,
  pH double,
  PRIMARY KEY (userId, timeAdded)
) WITH CLUSTERING ORDER BY (timeAdded ASC);

-- down:

DROP TABLE metrics;
DROP TABLE user;
