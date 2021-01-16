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
    -- 立ち絵
    image_body_id int     NOT NULL,
    -- その他
    is_actioned   boolean NOT NULL default false
);

CREATE TABLE person_skill
(
    person_id bigint REFERENCES person (id) ON DELETE CASCADE,
    skill     varchar(20) NOT NULL,
    level     smallint    NOT NULL,
    exp       int         NOT NULL,
    UNIQUE (person_id, skill)
);

CREATE TABLE personality
(
    person_id bigint REFERENCES person (id) ON DELETE CASCADE,
    type      varchar(20) NOT NULL,
    UNIQUE (person_id, type)
);

CREATE TABLE party
(
    id       bigserial PRIMARY KEY,
    world_id bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    name     varchar(40) NOT NULL
);

CREATE TABLE party_member
(
    party_id  bigint REFERENCES party (id) ON DELETE CASCADE,
    person_id bigint REFERENCES person (id) ON DELETE CASCADE,
    UNIQUE (person_id)
);

CREATE TABLE applicant
(
    person_id bigint PRIMARY KEY REFERENCES person (id) ON DELETE CASCADE
);