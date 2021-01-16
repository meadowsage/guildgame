CREATE TABLE game_log
(
    world_id   bigint       NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    person_id  bigint REFERENCES person (id) ON DELETE CASCADE,
    quest_id   bigint REFERENCES quest (id) ON DELETE CASCADE,
    message    varchar(100) NOT NULL,
    game_date  int          NOT NULL,
    log_level  smallint     NOT NULL,
    created_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
