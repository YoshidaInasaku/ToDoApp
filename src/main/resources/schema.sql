DROP TABLE IF EXISTS tasklist;

CREATE TABLE IF NOT EXISTS tasklist(
    id VARCHAR(256) PRIMARY KEY,
    task VARCHAR(256),
    deadline VARCHAR(10),
    done BOOLEAN
);