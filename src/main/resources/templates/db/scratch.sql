##DB Table DDL

CREATE TABLE `user_account`
(
    `id`        varchar(100)                                                 NOT NULL,
    `password`  varchar(300)                                                 NOT NULL COMMENT '유저가 입력한 패스워드 암호화되어 저장',
    `userName`  varchar(100)                                                          DEFAULT NULL,
    `signDate`  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일자',
    `dbSts`     varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT 'A',
    `userEmail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



CREATE TABLE `user_review`
(
    `seq`        int          NOT NULL AUTO_INCREMENT,
    `star_count` float        NOT NULL,
    `movie_cd`   varchar(100) NOT NULL,
    `title`      varchar(100) NOT NULL,
    `user_id`    varchar(100) NOT NULL,
    `detail`     varchar(300) NOT NULL,
    PRIMARY KEY (`seq`),
    KEY `fk_user_review_user_account` (`user_id`),
    CONSTRAINT `fk_user_review_user_account` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;




CREATE TABLE `user_token`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `userId`     varchar(100) NOT NULL,
    `token`      varchar(500) NOT NULL,
    `expiryDate` date         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_user_token_to_user_account` (`userId`),
    CONSTRAINT `fk_user_token_to_user_account` FOREIGN KEY (`userId`) REFERENCES `user_account` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

