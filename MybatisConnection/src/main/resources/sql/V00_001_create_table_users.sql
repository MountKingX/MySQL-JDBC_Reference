-- CREATE DATABASE db_dev_mybatis_conn CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE `users`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK-id',
    `username`  varchar(32) DEFAULT NULL COMMENT 'username',
    `password`  varchar(32) DEFAULT NULL COMMENT 'password',
    `user_role` varchar(32) DEFAULT NULL,
    `nick_name` varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;



