CREATE TABLE save_data
(
  id         uuid PRIMARY KEY,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE world
(
  id           bigserial PRIMARY KEY,
  save_data_id bigint    NOT NULL REFERENCES save_data (id),
  game_date    int       NOT NULL
);

CREATE TABLE person
(
  id             bigserial PRIMARY KEY,
  world_id       bigint      NOT NULL REFERENCES world (id),
  first_name     text        NOT NULL DEFAULT '',
  family_name    text        NOT NULL DEFAULT '',
  -- 所持金・名声
  money          int         NOT NULL DEFAULT 0,
  reputation     int         NOT NULL DEFAULT 0,
    -- 能力値
  battle         int         NOT NULL DEFAULT 0,
  knowledge      int         NOT NULL DEFAULT 0,
  support        int         NOT NULL DEFAULT 0
);

CREATE TABLE quest
(
  id         bigserial PRIMARY KEY,
  world_id   bigint      NOT NULL REFERENCES world (id)
);