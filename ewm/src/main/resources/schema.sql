DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    title VARCHAR(128) NOT NULL,
    annotation VARCHAR(1024) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    paid BOOLEAN NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT REFERENCES users (id),
    description VARCHAR(2048),
    participant_limit INTEGER DEFAULT 0,
    state VARCHAR(32) NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    request_moderation DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    title VARCHAR(128) NOT NULL,
    pinned BOOLEAN NOT NULL,
    event_id BIGINT REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    event_id BIGINT REFERENCES events (id)
    requester_id BIGINT REFERENCES requests (id)
    status VARCHAR(32) NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL
);