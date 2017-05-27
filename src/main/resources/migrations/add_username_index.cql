-- description: add_username_index
-- authoredAt: 1495899308131
-- up:

CREATE INDEX userUsername ON user (name)

-- down:

DROP INDEX IF EXISTS userUsername
