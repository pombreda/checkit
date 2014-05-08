CREATE TABLE user_roles(
    user_role_id SERIAL PRIMARY KEY,
    authority VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    user_role_id INTEGER NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enabled BOOLEAN NOT NULL,
    created TIMESTAMP NOT NULL,
    FOREIGN KEY (user_role_id) REFERENCES user_roles(user_role_id)
);

CREATE TABLE user_activation(
    user_activation_id VARCHAR(50) PRIMARY KEY,
    user_id INTEGER NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE contacts(
    contact_id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE plugins_report(
    filename VARCHAR(50) PRIMARY KEY,
    enabled BOOLEAN NOT NULL,
    title VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE plugins_check(
    filename VARCHAR(50) PRIMARY KEY,
    enabled BOOLEAN NOT NULL,
    title VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE contact_detail(
    contact_detail_id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    data JSON NOT NULL,
    contact_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    filename VARCHAR(50) NOT NULL,
    down BOOLEAN NOT NULL,
    up BOOLEAN NOT NULL,
    regular BOOLEAN NOT NULL,
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (filename) REFERENCES plugins_report(filename) ON DELETE CASCADE
);

CREATE TABLE checks(
    check_id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    data JSON NOT NULL,
    enabled BOOLEAN NOT NULL,
    user_id INTEGER NOT NULL,
    filename VARCHAR(50) NOT NULL,
    interval INTEGER NOT NULL,
    ok BOOLEAN NOT NULL,
    checked BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (filename) REFERENCES plugins_check(filename) ON DELETE CASCADE
);

CREATE TABLE reporting(
    user_id INTEGER NOT NULL,
    check_id INTEGER NOT NULL,
    contact_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (check_id) REFERENCES checks(check_id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, check_id, contact_id)
);

CREATE RULE "reporting_on_duplicate_ignore" AS ON INSERT TO "reporting"
    WHERE EXISTS(SELECT 1 FROM reporting
        WHERE (check_id, check_id, contact_id)=(NEW.check_id, NEW.check_id, NEW.contact_id)
    ) DO INSTEAD NOTHING;

CREATE TABLE agents(
    agent_id SERIAL PRIMARY KEY,
    ip VARCHAR(50) NOT NULL,
    post_address VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE checking(
    agent_id INTEGER NOT NULL,
    check_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (agent_id) REFERENCES agents(agent_id) ON DELETE RESTRICT,
    FOREIGN KEY (check_id) REFERENCES checks(check_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    PRIMARY KEY (agent_id, check_id)
);

CREATE TABLE agent_queue(
    agent_queue_id SERIAL PRIMARY KEY,
    agent_id INTEGER NOT NULL,
    check_id INTEGER NOT NULL,
    query VARCHAR(50) NOT NULL
);

CREATE TABLE results(
    check_id INTEGER NOT NULL,
    agent_id INTEGER NOT NULL,
    time TIMESTAMP NOT NULL,
    status VARCHAR(1) NOT NULL,
    data JSON,
    FOREIGN KEY (check_id) REFERENCES checks(check_id) ON DELETE CASCADE,
    PRIMARY KEY (check_id, agent_id, time)
);
