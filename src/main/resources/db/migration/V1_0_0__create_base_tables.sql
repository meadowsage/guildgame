CREATE TABLE save_data
(
    id         varchar(36) PRIMARY KEY
);

-- ワールドデータ

CREATE TABLE world
(
    id           bigserial PRIMARY KEY,
    save_data_id varchar(36) NOT NULL REFERENCES save_data (id) ON DELETE CASCADE,
    game_date    int         NOT NULL,
    state        varchar(20) NOT NULL,
    updated_at   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP -- 排他制御用
);

-- ギルド

CREATE TABLE guild
(
    world_id   bigint PRIMARY KEY REFERENCES world (id) ON DELETE CASCADE,
    money      int NOT NULL,
    reputation int NOT NULL
);

CREATE TABLE guild_balance
(
    world_id  bigint NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    game_date int    NOT NULL,
    value     bigint NOT NULL,
    UNIQUE (world_id, game_date)
);

-- ゲーム管理

CREATE TABLE opened_place
(
    world_id bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    place    varchar(40) NOT NULL,
    PRIMARY KEY (world_id, place)
);

CREATE TABLE scenario_progress
(
    world_id    bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    scenario_id varchar(40) NOT NULL,
    PRIMARY KEY (world_id, scenario_id)
);
