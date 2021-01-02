CREATE TABLE save_data
(
    id         varchar(36) PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ワールドデータ

CREATE TABLE world
(
    id           bigserial PRIMARY KEY,
    save_data_id varchar(36) NOT NULL REFERENCES save_data (id) ON DELETE CASCADE,
    game_date    int         NOT NULL,
    state        varchar(20) NOT NULL,
    created_at   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE guild
(
    world_id   bigint PRIMARY KEY REFERENCES world (id) ON DELETE CASCADE,
    money      int NOT NULL,
    reputation int NOT NULL
);

CREATE TABLE person
(
    id            bigserial PRIMARY KEY,
    world_id      bigint  NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    first_name    text    NOT NULL,
    family_name   text    NOT NULL,
    -- 所持金・名声
    money         int     NOT NULL,
    reputation    int     NOT NULL,
    -- 能力値
    battle        int     NOT NULL,
    battle_exp    int     NOT NULL,
    knowledge     int     NOT NULL,
    knowledge_exp int     NOT NULL,
    support       int     NOT NULL,
    support_exp   int     NOT NULL,
    -- 体力
    max_energy    int     NOT NULL,
    energy        int     NOT NULL,
    -- その他
    is_actioned   boolean NOT NULL default false
);

CREATE TABLE applicant
(
    person_id bigint PRIMARY KEY REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE quest
(
    id           bigserial PRIMARY KEY,
    world_id     bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    content      varchar(40) NOT NULL,
    type         varchar(10) NOT NULL,
    place        varchar(40) NOT NULL,
    difficulty   int         NOT NULL,
    danger       int         NOT NULL,
    rewards      int         NOT NULL,
    processed_at int         NOT NULL, -- ゲーム日付
    is_closed    boolean     NOT NULL DEFAULT false
);

CREATE TABLE quest_order
(
    id        bigserial PRIMARY KEY,
    quest_id  bigint      NOT NULL REFERENCES quest (id) ON DELETE CASCADE,
    person_id bigint      NOT NULL REFERENCES person (id) ON DELETE CASCADE,
    rewards   int         NOT NULL,
    state     varchar(20) NOT NULL
);

CREATE TABLE opened_place
(
    world_id bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    place    varchar(40) NOT NULL,
    PRIMARY KEY (world_id, place)
);

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

-- ゲーム管理

CREATE TABLE scenario_progress
(
    world_id    bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    scenario_id varchar(40) NOT NULL,
    PRIMARY KEY (world_id, scenario_id)
);

-- 会計

CREATE TABLE quest_payment
(
    quest_id  bigint NOT NULL REFERENCES quest (id) ON DELETE CASCADE,
    person_id bigint REFERENCES person (id) ON DELETE CASCADE,
    game_date int    NOT NULL,
    value     bigint NOT NULL
);

CREATE TABLE quest_income
(
    quest_id  bigint NOT NULL REFERENCES quest (id) ON DELETE CASCADE,
    game_date int    NOT NULL,
    value     bigint NOT NULL
);

CREATE TABLE guild_balance
(
    world_id  bigint NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    game_date int    NOT NULL,
    value     bigint NOT NULL,
    UNIQUE (world_id, game_date)
);