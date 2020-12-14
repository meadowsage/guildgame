CREATE TABLE save_data
(
    id         varchar(36) PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE world
(
    id           bigserial PRIMARY KEY,
    save_data_id varchar(36) NOT NULL REFERENCES save_data (id),
    game_date    int         NOT NULL
);

CREATE TABLE guild
(
    world_id   bigint PRIMARY KEY REFERENCES world (id),
    money      int NOT NULL,
    reputation int NOT NULL
);

CREATE TABLE person
(
    id          bigserial PRIMARY KEY,
    world_id    bigint NOT NULL REFERENCES world (id),
    first_name  text   NOT NULL,
    family_name text   NOT NULL,
    -- 所持金・名声
    money       int    NOT NULL,
    reputation  int    NOT NULL,
    -- 能力値
    battle      int    NOT NULL,
    knowledge   int    NOT NULL,
    support     int    NOT NULL
);

CREATE TABLE quest
(
    id         bigserial PRIMARY KEY,
    world_id   bigint      NOT NULL REFERENCES world (id),
    type       varchar(10) NOT NULL,
    difficulty int         NOT NULL
);