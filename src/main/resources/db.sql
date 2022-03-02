SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE(`email`),
    UNIQUE(`phoneNumber`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1,"root","test1@test.com","1234567","dbb1c112a931eeb16299d9de1f30161d","0",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (2,"root","test2@test.com","12345678","dbb1c112a931eeb16299d9de1f30161d","1",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (3,"root","test3@test.com","123456789","dbb1c112a931eeb16299d9de1f30161d","2",'2022-02-28 10:00:00');


DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_time` datetime(0) NULL DEFAULT NULL,
    `worker_amount` int(11) NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_user_project`(`user_id`) USING BTREE,
    CONSTRAINT `fk_user_project` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `project` VALUES (1,2,"test_project1","open","testproject1",'2022-03-28 10:00:00',0,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (2,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (3,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (4,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (5,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (6,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (7,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (8,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (9,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (10,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (11,2,"test_project2","open","testproject2",'2022-03-28 10:00:00',2000,'2022-02-28 10:00:00');






DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'unfinished',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_step` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `device_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_user_report`(`user_id`) USING BTREE,
    INDEX `fk_task_report`(`task_id`) USING BTREE,
    CONSTRAINT `fk_user_report` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_task_report` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `report` VALUES (1,1,3,"report1_task1_project1","open","test_report1","123","123",'2022-02-28 10:00:00');
INSERT INTO `report` VALUES (2,2,3,"report2_task2_project1","open","test_report2","123","123",'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `project_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resource_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_project_file`(`project_id`) USING BTREE,
    CONSTRAINT `fk_project_file` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `project_file` value (1,1,"project1","txt","file/project/project1/project1.txt",'2022-02-28 10:00:00');
INSERT INTO `project_file` value (2,2,"project2","txt","file/project/project2/project2.txt",'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `task_file`;
CREATE TABLE `task_file` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resource_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_task_file`(`task_id`) USING BTREE,
    CONSTRAINT `fk_task_file` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `task_file` value (1,1,"task1","txt","file/task/task1/task1.txt",'2022-02-28 10:00:00');
INSERT INTO `task_file` value (2,2,"task2","txt","file/task/task2/task2.txt",'2022-02-28 10:00:00');


DROP TABLE IF EXISTS `report_file`;
CREATE TABLE `report_file` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `report_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resource_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_report_file`(`report_id`) USING BTREE,
    CONSTRAINT `fk_report_file` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `report_file` value (1,1,"report1","txt","file/report/report1/report1.txt",'2022-02-28 10:00:00');
INSERT INTO `report_file` value (2,2,"report2","txt","file/report/report2/report2.txt",'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `project_id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '进行中',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_time` datetime(0) NULL DEFAULT NULL,
    `worker_amount` int(11) NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_project_task`(`project_id`) USING BTREE,
    CONSTRAINT `fk_project_task` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `task` VALUES (1,1,"task1_project1","open","test_task1",'2022-03-28 10:00:00',10,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (2,1,"task2_project1","open","test_task2",'2022-03-28 10:00:00',10,'2022-02-28 10:00:00');


DROP TABLE IF EXISTS `user_project`;
CREATE TABLE `user_project`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `project_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_project_user`(`project_id`) USING BTREE,
    INDEX `fk_project_user1`(`user_id`) USING BTREE,
    CONSTRAINT `fk_project_user` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_project_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user_project` VALUES (1,2,3);
INSERT INTO `user_project` VALUES (2,2,2);

DROP TABLE IF EXISTS `user_task`;
CREATE TABLE `user_task`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_task_user`(`task_id`) USING BTREE,
    INDEX `fk_task_user1`(`user_id`) USING BTREE,
    CONSTRAINT `fk_task_user` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_task_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user_task` VALUES (1,1,3);
INSERT INTO `user_task` VALUES (2,2,3);

SET FOREIGN_KEY_CHECKS = 1;