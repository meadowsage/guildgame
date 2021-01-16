CREATE TABLE quest
(
    id       bigserial PRIMARY KEY,
    world_id bigint      NOT NULL REFERENCES world (id) ON DELETE CASCADE,
    content  varchar(40) NOT NULL
);

CREATE TABLE quest_order
(
    id       bigserial PRIMARY KEY,
    quest_id bigint NOT NULL REFERENCES quest (id) ON DELETE CASCADE,
    party_id bigint NOT NULL REFERENCES party (id) ON DELETE CASCADE
);

CREATE TABLE quest_order_progress
(
    quest_order_id bigint NOT NULL REFERENCES quest_order (id) ON DELETE CASCADE,
    game_date      int    NOT NULL,
    progress       int    NOT NULL
);

CREATE TABLE quest_order_result
(
    quest_order_id bigint  NOT NULL REFERENCES quest_order (id) ON DELETE CASCADE,
    game_date      int     NOT NULL,
    succeeded      boolean NOT NULL
);

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
