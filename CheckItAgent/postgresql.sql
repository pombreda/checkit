CREATE TABLE servers(
    ip VARCHAR(50) PRIMARY KEY,
    post_address VARCHAR(50) NOT NULL,
    priority INT NOT NULL
);

CREATE TABLE checks(
    check_id INTEGER NOT NULL,
    data JSON NOT NULL,
    filename VARCHAR(50) NOT NULL,
    interval INT NOT NULL,
    PRIMARY KEY (check_id)
);

CREATE TABLE results(
    check_id INTEGER NOT NULL,
    time TIMESTAMP NOT NULL,
    status VARCHAR(1) NOT NULL,
    data JSON NOT NULL,
    PRIMARY KEY (check_id, time)
);
