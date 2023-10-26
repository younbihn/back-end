DROP TABLE IF EXISTS SITE_USER;
DROP TABLE IF EXISTS MATCHING;
DROP TABLE IF EXISTS AUTH;
DROP TABLE IF EXISTS APPLY;
DROP TABLE IF EXISTS NOTIFICATION;
DROP TABLE IF EXISTS WEATHER;
DROP TABLE IF EXISTS CONFIRM;
DROP TABLE IF EXISTS WEATHER;
DROP TABLE IF EXISTS MANNER_SCORE;
DROP TABLE IF EXISTS PENALTY_SCORE;
DROP TABLE IF EXISTS REVIEW;

CREATE TABLE `SITE_USER` (
                             `ID`	BIGINT	NOT NULL,
                             `PASSWORD`	VARCHAR(1023)	NOT NULL COMMENT '암호화해야함',
                             `NICKNAME`	varchar(50)	NOT NULL	COMMENT '숫자와 문자로만',
                             `EMAIL`	varchar(255)	NOT NULL,
                             `PHONE_NUMBER`	varchar(50)	NOT NULL,
                             `MANNER_SCORE`	INT	NULL	DEFAULT 0,
                             `PENALTY_SCORE`	INT	NULL	DEFAULT 0,
                             `GENDER`	varchar(50)	NOT NULL	COMMENT 'MALE, FEMALE',
                             `NTRP`	DECIMAL(2,1)	NOT NULL	COMMENT '전체 2자리, 소숫점 1자리',
                             `LOCATION_SI`	varchar(50)	NOT NULL,
                             `LOCATION_GU`	varchar(50)	NOT NULL,
                             `AGE_GROUP`	varchar(50)	NOT NULL	COMMENT 'TWENTIES, THIRTIES , FORTIES , SENIOR',
                             `PROFILE_IMG`	varchar(1023)	NULL,
                             `CREATE_DATE`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS',
                             `PHONE_VERIFICATION`	BOOL	NULL	DEFAULT 0	COMMENT 'true = 1 / false = 0'
);

CREATE TABLE `MATCHING` (
                            `ID`	BIGINT	NOT NULL,
                            `SITE_USER_ID`	BIGINT	NOT NULL,
                            `TITLE`	varchar(50)	NOT NULL,
                            `CONTENT`	varchar(1023)	NULL,
                            `LOCATION`	varchar(255)	NOT NULL,
                            `LOCATION_IMG`	varchar(1023)	NULL,
                            `DATE`	DATE	NULL	COMMENT 'YYYY-MM-DD',
                            `START_TIME`	TIME	NOT NULL	COMMENT 'HH:MM:SS',
                            `END_TIME`	TIME	NOT NULL	COMMENT 'HH:MM:SS',
                            `RECRUIT_NUM`	INT	NOT NULL,
                            `COST`	INT	NOT NULL,
                            `RESERVATION_STATUS`	BOOL	NULL	DEFAULT 0	COMMENT 'true = 1 / false = 0',
                            `NTRP`	VARCHAR(50)	NULL	COMMENT '등록자가 범위를 입력할 수 있으므로',
                            `AGE`	VARCHAR(50)	NULL	COMMENT '등록자가 범위를 입력할 수 있으므로',
                            `RECRUIT_STATUS`	VARCHAR(50)	NULL,
                            `CREATE_TIME`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS',
                            `TYPE`	VARCHAR(50)	NULL	COMMENT 'SINGLE, DOUBLE, MIXED_DOUBLE, OTHER',
                            `APPLY_NUM`	INT	NULL	DEFAULT 0
);

CREATE TABLE `AUTH` (
                        `ID`	BIGINT	NOT NULL,
                        `REFRESH_TOKEN`	VARCHAR(1023)	NULL	COMMENT '유효시간 14시간',
                        `SITE_USER_ID`	BIGINT	NOT NULL
);

CREATE TABLE `APPLY` (
                         `ID`	BIGINT	NOT NULL,
                         `MATCHING_ID`	BIGINT	NOT NULL,
                         `SITE_USER_ID`	BIGINT	NOT NULL,
                         `APPLY_DATE`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS',
                         `STATUS`	VARCHAR(50)	NOT NULL	COMMENT 'PENDING, ACCEPTED, REJECTED'
);

CREATE TABLE `NOTIFICATION` (
                                `ID`	BIGINT	NOT NULL,
                                `SITE_USER_ID`	VARCHAR(255)	NOT NULL,
                                `MATCHING_ID`	BIGINT	NULL	COMMENT '*특정 매칭과 관련없는 알림의 경우 null',
                                `CODE`	VARCHAR(50)	NOT NULL,
                                `CONTENT`	VARCHAR(255)	NOT NULL,
                                `CREATE_DATE`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `WEATHER` (
                           `ID`	BIGINT	NOT NULL,
                           `MATCHING_ID`	BIGINT	NOT NULL,
                           `WEATHER`	VARCHAR(255)	NOT NULL,
                           `TEMPERATURE`	DECIMAL(3,1)	NULL
);

CREATE TABLE `CONFIRM` (
                           `ID`	BIGINT	NOT NULL,
                           `MATCHING_ID`	BIGINT	NOT NULL,
                           `SITE_USER_ID`	BIGINT	NOT NULL,
                           `CREATE_TIME`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS'
);

CREATE TABLE `MANNER_SCORE` (
                                `ID`	VARCHAR(255)	NOT NULL,
                                `MATCHING_ID`	BIGINT	NOT NULL,
                                `SITE_USER_ID`	BIGINT	NOT NULL,
                                `SCORE`	INT	NOT NULL,
                                `CREATE_TIME`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS'
);

CREATE TABLE `PENALTY_SCORE` (
                                 `ID`	VARCHAR(255)	NOT NULL,
                                 `SITE_USER_ID`	BIGINT	NOT NULL,
                                 `SCORE`	INT	NOT NULL,
                                 `STATUS`	VARCHAR(50)	NULL	DEFAULT 'PENDING'	COMMENT 'PENDING, ACCEPTED, REJECTED',
                                 `CREATE_TIME`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS',
                                 `TYPE`	VARCHAR(50)	NULL	COMMENT 'OFFENSE_CHAT, DELETE_MATCH, CANCEL_AFTER_COMFIRM'
);

CREATE TABLE `REVIEW` (
                          `ID`	BIGINT	NOT NULL,
                          `OBJECT_USER_ID`	BIGINT	NOT NULL,
                          `MATCHING_ID`	BIGINT	NOT NULL,
                          `SUBJECT_USER_ID`	BIGINT	NOT NULL,
                          `SCORE`	INT	NOT NULL,
                          `CREATE_TIME`	TIMESTAMP	NOT NULL	COMMENT 'YYYY-MM-DD HH:MM:SS',
);

ALTER TABLE `SITE_USER` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                              `ID`
    );

ALTER TABLE `MATCHING` ADD CONSTRAINT `PK_MATCHING` PRIMARY KEY (
                                                                 `ID`
    );

ALTER TABLE `AUTH` ADD CONSTRAINT `PK_AUTH` PRIMARY KEY (
                                                         `ID`
    );

ALTER TABLE `APPLY` ADD CONSTRAINT `PK_APPLY` PRIMARY KEY (
                                                           `ID`
    );

ALTER TABLE `NOTIFICATION` ADD CONSTRAINT `PK_NOTIFICATION` PRIMARY KEY (
                                                                         `ID`
    );

ALTER TABLE `WEATHER` ADD CONSTRAINT `PK_WEATHER` PRIMARY KEY (
                                                               `ID`
    );

ALTER TABLE `CONFIRM` ADD CONSTRAINT `PK_CONFIRM` PRIMARY KEY (
                                                               `ID`
    );

ALTER TABLE `MANNER_SCORE` ADD CONSTRAINT `PK_MANNER_SCORE` PRIMARY KEY (
                                                                         `ID`
    );

ALTER TABLE `PENALTY_SCORE` ADD CONSTRAINT `PK_PENALTY_SCORE` PRIMARY KEY (
                                                                           `ID`
    );

ALTER TABLE `REVIEW` ADD CONSTRAINT `PK_REVIEW` PRIMARY KEY (
                                                             `ID`
    );

