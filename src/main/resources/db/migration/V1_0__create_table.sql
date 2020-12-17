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
    id           bigserial PRIMARY KEY,
    world_id     bigint      NOT NULL REFERENCES world (id),
    type         varchar(10) NOT NULL,
    difficulty   int         NOT NULL,
    is_completed boolean     NOT NULL DEFAULT false,
    is_deleted   boolean     NOT NULL DEFAULT false
);

CREATE TABLE quest_order
(
    person_id bigint NOT NULL REFERENCES person (id),
    quest_id  bigint NOT NULL REFERENCES quest (id)
);

ALTER TABLE quest_order
    ADD PRIMARY KEY (person_id, quest_id);

CREATE TABLE game_log
(
    world_id  bigint       NOT NULL REFERENCES world (id),
    person_id bigint REFERENCES person (id),
    message   varchar(100) NOT NULL,
    game_date int          NOT NULL
);