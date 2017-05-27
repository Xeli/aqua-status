-- description: create_metrics_table
-- authoredAt: 1495899002375
-- up:

CREATE TABLE metrics (
  userId uuid,
  timeAdded bigint,
  temperature double,
  waterLevel double,
  pH double,
  PRIMARY KEY (userId, timeAdded)
) WITH CLUSTERING ORDER BY (timeAdded ASC)

-- down:

DROP TABLE metrics

