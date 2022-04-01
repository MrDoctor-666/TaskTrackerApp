CREATE TABLE IF NOT EXISTS tasks (
    id                     INT  DEFAULT RANDOM_UUID() PRIMARY KEY,
    TASK_NAME               VARCHAR(255)      NOT NULL,
    END_DATE            VARCHAR(60)       NOT NULL,
    REPEAT                 INT          NOT NULL DEFAULT 1,
    CAN_SKIP                 BIT         NOT NULL DEFAULT 0,
    REPEAT_DAYS              INT         NOT NULL DEFAULT 0,
    tag                    VARCHAR(255) DEFAULT NULL,
    color                   INT         NOT NULL DEFAULT 0,
    note                   VARCHAR(255) DEFAULT 0
    );