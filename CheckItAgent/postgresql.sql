CREATE TABLE servers(
    ip VARCHAR(50) PRIMARY KEY,
    priority INT NOT NULL
);

CREATE TABLE tests(
    test_id INTEGER NOT NULL,
    title VARCHAR(50) NOT NULL,
    data JSON,
    enabled BOOLEAN NOT NULL,
    user_id INTEGER NOT NULL,
    plugin_filename VARCHAR(50) NOT NULL,
    interval INT NOT NULL,
    PRIMARY KEY (test_id)
);

CREATE TABLE results(
    test_id INTEGER NOT NULL,
    time TIMESTAMP NOT NULL,
    data JSON,
    PRIMARY KEY (test_id, time)
);
