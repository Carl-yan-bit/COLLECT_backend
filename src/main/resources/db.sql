SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
set @@GLOBAL.sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `exp` int(5) DEFAULT 0,
    `level` int(3) DEFAULT 0,
    `project_preference_id` int(11) UNIQUE,
    `activity` int(6) DEFAULT 0,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_email`(`email`) USING BTREE,
    INDEX `index_phone`(`phoneNumber`) USING BTREE,
    INDEX `index_name`(`name`) USING BTREE,
    UNIQUE(`email`),
    UNIQUE(`phoneNumber`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1,12,2,NULL,0,"root","root1@test.com","1234567","dbb1c112a931eeb16299d9de1f30161d","0",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (2,0,0,NULL,0,"发包方","test2@test.com","12345678","36a61e11fb03704d18e12bd98459727a","1",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (3,0,0,NULL,0,"第一个用户","test3@test.com","123456789","2f0698cf3a9bfb73ad0167306fdd2ce4","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (4,13,2,NULL,0,"employee1","employee1@test.com","74751","521217f86e7105b50785b02d0659a054","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (5,1,0,NULL,0,"employee2","employee2@test.com","74752","720556b32d40f48dc6f3b2b83fc94c82","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (6,3,1,NULL,0,"employee3","employee3@test.com","74753","9df1097747222bce16c6265bab52e121","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (7,3,1,NULL,0,"employee4","employee4@test.com","74754","3c557a63f2887866055d79834e19e8f3","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (8,600,6,NULL,0,"employeeIOS","IOS@test.com","74755","aa320d82002497c267e77fed2f2c774f","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (9,600,6,NULL,0,"employeeAndroid","Android@test.com","74756","2d8712176552abeead274aa3bdb72c26","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (10,10,2,NULL,0,"employeeMAC","MAC@test.com","74757","0fa8ed9b1788d84bb9ec5becc19bff3d","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (11,12,2,NULL,0,"employeeLINUX","LINUX@test.com","74758","10808c670f25cef8ed5e8b4cc3d6eb66","2",'2022-02-28 10:00:00');
INSERT INTO `user` VALUES (12,1000,6,NULL,0,"employer1","employer1@test.com","418511","07f3fbbb15d8766730745f1d970b890b","1",'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `project_preference`;
CREATE TABLE `project_preference`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `difficulty` float DEFAULT 0.0,
    `device_id` float DEFAULT 0.0,
    `type` float DEFAULT 0.0,
    INDEX `fk_user_project_preference`(`user_id`) USING BTREE,
    CONSTRAINT `fk_user_project_preference` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `project_preference` VALUES (1,1,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (2,2,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (3,3,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (4,4,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (5,5,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (6,6,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (7,7,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (8,8,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (9,9,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (10,10,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (11,11,0.0,0.0,0.0)
INSERT INTO `project_preference` VALUES (12,12,0.0,0.0,0.0)

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `click_times` int(11) NOT NULL DEFAULT 0,
    `user_id` int(11) NOT NULL,
    `difficulty` int(4) DEFAULT 0,
    `device_id` int(5) DEFAULT 8,
    `type` int(5) DEFAULT 0,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `description` varchar(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_time` datetime(0) NULL DEFAULT NULL,
    `worker_amount` int(11) NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_user_project`(`user_id`) USING BTREE,
    INDEX `fk_click_times`(`click_times`) USING BTREE,
    CONSTRAINT `fk_user_project` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `project` VALUES (1,11,2,1,1,1,"财新（企业版）-7.0.0-BUG探索-IOS","open","1、可以用iPad和苹果手机进行测试，生产环境测试，从App store下载781版本测试，千万别用平台包测试
                                                                          2、所有功能都测试，重点测试支付、FM、音频播放器、文章正文页、我的播单等功能",'2022-04-28 10:00:00',15,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (2,100,2,3,3,1,"财新测试-7.8.0-BUG探索-鸿蒙系统","open","1、可以用平板和安卓手机进行测试，生产环境测试，从各个应用市场下载781版本测试，千万别用平台包测试。
                                                                           2、所有功能都测试，重点测试支付、FM、音频播放器、文章正文页、我的播单等功能",'2022-04-28 10:00:00',35,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (3,11,2,7,1,1,"Welink-5.59.5-Bug探索-IOS","open","【重要】测试前必看，WeLink探索测试指南
                                                                          https://kdocs.cn/l/cnHhUKkDXLJa
                                                                          1.Android安装包用任务的，IOS包用群里Testflght账号的
                                                                          2.基础操作手册：https://support.huaweicloud.com/usermanual-welink/welink_appuse.html
                                                                          3.详细需求：https://kdocs.cn/l/ccpzZosO3vlA
                                                                          4.所有人都需要自主注册选择员工，必须加入该测试公司，邀请码：ZRS8JMA9",'2022-04-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (4,1,2,7,3,1,"Welink-5.59.3-Bug探索-HarmonyOS","open","【重要】测试前必看，WeLink探索测试指南
                                                                                https://kdocs.cn/l/cnHhUKkDXLJa
                                                                                1.Android安装包用任务的，IOS包用群里Testflght账号的
                                                                                2.基础操作手册：https://support.huaweicloud.com/usermanual-welink/welink_appuse.html
                                                                                3.详细需求：https://kdocs.cn/l/ccpzZosO3vlA
                                                                                4.所有人都需要自主注册，必须加入该测试公司，邀请码：ZRS8JMA9",'2022-04-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (5,0,2,1,4,1,"WeLink-7.14.0.280-Bug探索-PC","open","安装包：PC版本7.14.0.280
                                                                             测试平台：Windows
                                                                             版本地址： https://softclient.obs.cn-north-4.myhuaweicloud.com:443/temp/WeLink_Win_cloud_202201301518_4790_master_7.14.0_280.exe?AccessKeyId=UH1ZMWZA2MLTCGRVEBJM&Expires=1644461315&Signature=UmBfx24upyAazfHxfUxsv2lXPJM%3D
                                                                             所有的bug必须带上日志【登录页点击登陆设置-收集日志，收集后自动打开日志所在目录了】
                                                                             WeLinkPC_0216各模块需求列表
                                                                             https://kdocs.cn/l/cp7SZzdCBxRf",'2022-04-28 10:00:00',200,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (6,0,12,1,5,1,"58同城-11.0.0-Bug探索-LINUX","open","此次版本更新内容
                                                                         1、使用到微聊的业务线需要回归测试：微聊登录登出、两台手机账号互踢、未读消息数、聊天页跳转（业务线帖子详情页点“微聊”，进入会话页）、语音消息播放
                                                                         2、在屏幕顶部弹出定位权限使用说明，在屏幕底部弹出定位权限使用弹窗，可参考https://note.youdao.com/s/cAlsf6sN，新增脚本1；
                                                                         3、首次打开app,不同意隐私协议，进入app首页前不再展示引导页面，目前灰度80%用户同意隐私协议可以看到引导页面。
                                                                         4、安卓单端：前提条件：允许58app使用定位信息；手动切换城市到不是当前定位的城市，例如：当前定位在北京，切换至上海市首页，然后杀掉app，重新冷启动app;",'2022-04-28 10:00:00',25,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (7,0,12,1,6,1,"58同城-11.0.0-Bug探索-MAC","open","
期望结果：冷启动后弹出城市切换弹窗；
5、首页底部菜单使用新版UI，UI展示正常；
6、登录SDK升级需求
1）账号密码登录、手机号一键登录、第三方登录、切换账号登录
2）注册功能
3）账号安全页面相关功能
4）登录相关页面滑动返回（右滑返回）
7、点击手机接收到的push消息，可以调起58app，正常进入落地页；

重点测试模块
1）App桌面底部tabUI；
2）登录模块；
3）个人中心--我的收藏页面；
4）消息中心：",'2022-04-28 10:00:00',2,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (8,0,12,9,2,1,"1905电影网-6.4.0-Bug探索-Android","open","视频平台改版涉及：
                                                                          1、首页-电影页
                                                                          2、首页-电影-为你推荐-（更多）为你推荐列表页
                                                                          3、首页-电视剧页
                                                                          4、首页-电影-电影榜单-（更多）榜单集合页
                                                                          5、首页-电影/电视剧底部检索
                                                                          6、首页-CCTV6页
                                                                          7、直播-电影直播页
                                                                          8、直播-CCTV6电视直播页
                                                                          9、电影号（视频）各分栏页，视频详情瀑布流
                                                                          10、电影号（视频）-短视频播放页
                                                                          11、搜索页，搜索结果页-短视频
                                                                          12、电影号，关注（取消关注）电影号，号主页相关
                                                                          测试以上改版页，核心重点：
                                                                          1、短视频播放页--分享；收藏；播放器（横屏、清屏、进度条、单击屏幕（暂停/播放）、双击屏幕（点赞））；评论；上下滑动（切换播放内容）；左滑屏幕（退出播放页）
                                                                          2、短视频关注、推荐、热门三页的视频流中短视频更多操作交互和长视频收藏交互
                                                                          3、首页精选页相关短视频播放
                                                                          4、测试长视频电影/电视剧影片播放、下载、收藏，直播页面播放，分享、投屏等各功能是否正常。
                                                                          5、电影号（视频）各分栏页面瀑布流加载
                                                                          6、网络测试（流量、WiFi、断网及弱网）
                                                                          测试请忽略端内H5页面（1905网页及第三方合作，含我的-立即签到/积分商城/应用宝箱/在线客服模块）",'2022-03-28 10:00:00',28,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (9,0,12,1,1,1,"EventXtra-Bug探索(含回归)-IOS","open","重点测试内容：会议
                                                                            会议主要测试内容：1主持会议多人发言，会议发起活动，会议分享，会议内所有的功能测试，包含分享，不同网络，不同设备参与。
                                                                            测试地址：https://portal.eventx.io/

                                                                            移动端测试需链接VPN
                                                                            60.205.254.158
                                                                            pptp vpn （账号：zhongce 密码：zc123666)
                                                                            l2tp vpn （账号：zhongce 密码：zc123666 秘钥：testin）
                                                                            DNS",'2022-03-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (10,0,12,1,2,1,"EventXtra-Bug探索(含回归)-Android","open","重点测试内容：会议
                                                                                 会议主要测试内容：1主持会议多人发言，会议发起活动，会议分享，会议内所有的功能测试，包含分享，不同网络，不同设备参与。
                                                                                 测试地址：https://portal.eventx.io/

                                                                                 移动端测试需链接VPN
                                                                                 60.205.254.158
                                                                                 pptp vpn （账号：zhongce 密码：zc123666)
                                                                                 l2tp vpn （账号：zhongce 密码：zc123666 秘钥：testin）
                                                                                 DNS",'2022-03-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `project` VALUES (11,0,12,1,8,1,"EventXtra-Bug探索(含回归)-其他","open","重点测试内容：会议
                                                                            会议主要测试内容：1主持会议多人发言，会议发起活动，会议分享，会议内所有的功能测试，包含分享，不同网络，不同设备参与。
                                                                            测试地址：https://portal.eventx.io/

                                                                            移动端测试需链接VPN
                                                                            60.205.254.158
                                                                            pptp vpn （账号：zhongce 密码：zc123666)
                                                                            l2tp vpn （账号：zhongce 密码：zc123666 秘钥：testin）
                                                                            DNS",'2022-03-28 10:00:00',200,'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `device_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `device` VALUES (1,"IOS/IpadOS设备");
INSERT INTO `device` VALUES (2,"安卓设备");
INSERT INTO `device` VALUES (3,"鸿蒙设备");
INSERT INTO `device` VALUES (4,"Windows设备");
INSERT INTO `device` VALUES (5,"Linux设备");
INSERT INTO `device` VALUES (6,"Mac设备");
INSERT INTO `device` VALUES (7,"嵌入式设备");
INSERT INTO `device` VALUES (8,"其他设备");

DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `type_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `type` VALUES (1,"功能测试");
INSERT INTO `type` VALUES (2,"性能测试");
INSERT INTO `type` VALUES (3,"安全测试");
INSERT INTO `type` VALUES (4,"负载测试");
INSERT INTO `type` VALUES (5,"其他测试");

DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `parent_report` int(11) DEFAULT NULL,
    `score` float DEFAULT 2.5,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'unfinished',
    `description` varchar(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_step` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `device_id` int(5) NOT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_user_report`(`user_id`) USING BTREE,
    INDEX `fk_task_report`(`task_id`) USING BTREE,
    INDEX `fk_task_user_report`(`task_id`,`user_id`) USING BTREE,
    INDEX `fk_parentReport_report`(`parent_report`) USING BTREE,
    CONSTRAINT `fk_user_report` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_task_report` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `report` VALUES (1,1,3,NULL,2.5,"我是报告1","open","我是1号主报告,登录界面有碧油鸡,登录按钮乱飞","123","1",'2022-02-28 10:00:00');
INSERT INTO `report` VALUES (2,1,4,1,5.0,"我是报告2","open","我是1号的子报告,注册界面有碧油鸡,注册按钮乱飞","123","1",'2022-02-28 10:00:00');
INSERT INTO `report` VALUES (3,1,5,NULL,2.5,"我是报告3","open","我是2号主报告,十大科技和客户经理全文阿萨德看见了回去","123","1",'2022-02-28 10:00:00');
INSERT INTO `report` VALUES (4,1,6,1,5.0,"我是报告4","open","我是1号的子报告,到处都是碧油鸡，到处都是乱飞","123","1",'2022-02-28 10:00:00');
INSERT INTO `report` VALUES (5,1,7,2,5.0,"我是报告5","open","我是2号的子报告，1号的孙子","123","1",'2022-02-28 10:00:00');
DROP TABLE IF EXISTS `report_comment`;
CREATE TABLE `report_comment`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `report_id` int(11) NOT NULL,
    `score` float NOT NULL,
    `content` varchar(4095),
    `create_time` datetime(0) NOT NULL,
    INDEX `fk_report_comment`(`report_id`) USING BTREE,
    CONSTRAINT `fk_report_comment` FOREIGN KEY(`report_id`) REFERENCES `report`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `report_comment` VALUES (1,1,2,3.5,"中规中矩",'2022-02-28 10:00:00');
INSERT INTO `report_comment` VALUES (2,2,2,3.6,"我觉得楼上说的对",'2022-02-28 11:00:00');
INSERT INTO `report_comment` VALUES (3,3,2,1.7,"他employee1什么的都在写报告，能写吗？写不了，没那个能力知道吗？",'2022-02-28 12:00:00');
INSERT INTO `report_comment` VALUES (4,10,2,0.7,"这份报告脸都不要了",'2022-02-28 13:00:00');
INSERT INTO `report_comment` VALUES (5,5,2,2.7,"能上3分吗？很蓝的啦",'2022-02-28 14:00:00');
INSERT INTO `report_comment` VALUES (6,6,2,4.7,"报告牛逼！",'2022-02-28 15:00:00');
INSERT INTO `report_comment` VALUES (7,7,2,4.7,"国足牛逼！",'2022-02-28 16:00:00');
INSERT INTO `report_comment` VALUES (8,8,2,4.7,"报告牛逼（一条5毛",'2022-02-28 17:00:00');
INSERT INTO `report_comment` VALUES (9,9,2,3.7,"楼上是有钱一起赚啊",'2022-02-28 18:00:00');

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
    `difficulty` int(4) DEFAULT 0,
    `device_id` int(5) DEFAULT 8,
    `type` int(5) DEFAULT 1,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '进行中',
    `description` varchar(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_time` datetime(0) NULL DEFAULT NULL,
    `worker_amount` int(11) NULL DEFAULT NULL,
    `create_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_project_task`(`project_id`) USING BTREE,
    CONSTRAINT `fk_project_task` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `task` VALUES (1,1,1,1,1,"测试支付IOS","open","1、可以用iPad和苹果手机进行测试，生产环境测试，从App store下载781版本测试，千万别用平台包测试",'2022-04-28 10:00:00',12,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (2,1,1,1,2,"测试文章正文页IOS","closed","可以用iPad和苹果手机进行测试，生产环境测试，从App store下载781版本测试，千万别用平台包测试",'2022-04-28 10:00:00',12,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (3,2,1,3,1,"测试支付HM","open","可以用平板和安卓手机进行测试，生产环境测试，从各个应用市场下载781版本测试，千万别用平台包测试。",'2022-04-28 10:00:00',35,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (4,2,1,3,2,"测试文章正文页HM","closed","可以用平板和安卓手机进行测试，生产环境测试，从各个应用市场下载781版本测试，千万别用平台包测试。",'2022-04-28 10:00:00',35,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (5,2,0,3,3,"测试我的播单","open","可以用平板和安卓手机进行测试，生产环境测试，从各个应用市场下载781版本测试，千万别用平台包测试。",'2022-04-28 10:00:00',10,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (6,2,1,3,4,"测试音频播放","open","可以用平板和安卓手机进行测试，生产环境测试，从各个应用市场下载781版本测试，千万别用平台包测试。",'2022-04-28 10:00:00',10,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (7,3,1,1,1,"全功能测试IOS","open","【重要】测试前必看，WeLink探索测试指南 https://kdocs.cn/l/cnHhUKkDXLJa 1.Android安装包用任务的，IOS包用群里Testflght账号的 2.基础操作手册：https://support.huaweicloud.com/usermanual-welink/welink_appuse.html 3.详细需求：https://kdocs.cn/l/ccpzZosO3vlA 4.所有人都需要自主注册选择员工，必须加入该测试公司，邀请码：ZRS8JMA9",'2022-04-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (8,4,1,3,2,"全功能测试HM","open","测试前必看，WeLink探索测试指南 https://kdocs.cn/l/cnHhUKkDXLJa 1.Android安装包用任务的，IOS包用群里Testflght账号的 2.基础操作手册：https://support.huaweicloud.com/usermanual-welink/welink_appuse.html 3.详细需求：https://kdocs.cn/l/ccpzZosO3vlA 4.所有人都需要自主注册，必须加入该测试公司，邀请码：ZRS8JMA9",'2022-04-28 10:00:00',20,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (9,5,1,4,3,"Bug探索-PC","closed","所有的bug必须带上日志【登录页点击登陆设置-收集日志，收集后自动打开日志所在目录了】 WeLinkPC_0216各模块需求列表 https://kdocs.cn/l/cp7SZzdCBxRf",'2022-04-28 10:00:00',200,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (10,6,2,5,1,"Bug探索-LINUX","open","首次打开app,不同意隐私协议，进入app首页前不再展示引导页面，目前灰度80%用户同意隐私协议可以看到引导页面。",'2022-04-28 10:00:00',15,'2022-02-28 10:00:00');
INSERT INTO `task` VALUES (11,7,3,6,1,"Bug探索-MAC","open","首页底部菜单使用新版UI，UI展示正常",'2022-04-28 20:21:00',1,'2022-02-28 10:00:00');

DROP TABLE IF EXISTS `user_project`;
CREATE TABLE `user_project`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `project_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `join_time` datetime,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_project_user`(`project_id`) USING BTREE,
    INDEX `fk_project_user1`(`user_id`) USING BTREE,
    CONSTRAINT `fk_project_user` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_project_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `recommend_strategy`;
CREATE TABLE `recommend_strategy`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `on_use` int(1) NOT NULL DEFAULT 0,
    `exp` int(11) NOT NULL,
    `name` varchar(255) NOT NULL,
    `difficulty` int(11) NOT NULL,
    `activity` int(11) NOT NULL,
    `device` int(11) NOT NULL,
    `num` int(11) NOT NULL,
    `type` int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_recommend`(`on_use`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `recommend_strategy` VALUES (1,1,1,"默认策略",1,1,1,6,1);

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

DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `device_id` int(5) NOT NULL,
    INDEX `fk_device_user`(`user_id`) USING BTREE,
    INDEX `fk_device_user1`(`device_id`) USING BTREE,
    CONSTRAINT `fk_device_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_device_user1` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`(
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `msg` varchar(255),
    `activity_point` int(5),
    `time` datetime,
    INDEX `fk_user_log`(`user_id`) USING BTREE,
    CONSTRAINT `fk_user_log` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `user_log` VALUES(1,1,"登陆",10,'2022-02-28 18:00:00')
INSERT INTO `user_log` VALUES(2,1,"登陆",1,'2022-03-28 18:00:00')
INSERT INTO `user_log` VALUES(3,1,"登陆",1,'2022-03-28 18:00:00')
INSERT INTO `user_log` VALUES(4,1,"登陆",1,'2022-03-28 18:00:00')
INSERT INTO `user_log` VALUES(5,1,"登陆",1,'2022-03-28 18:00:00')
INSERT INTO `user_log` VALUES(6,1,"登陆",1,'2022-03-28 18:00:00')
-- 定时事务，用于项目的到期自动关闭
DROP EVENT IF EXISTS `auto_close_task`;
CREATE EVENT `auto_close_task`
ON SCHEDULE EVERY 1 MINUTE
DO
update task
set state = 'closed'
where test_time < now();

DROP EVENT IF EXISTS `auto_close_project`;
CREATE EVENT `auto_close_project`
ON SCHEDULE EVERY 1 MINUTE
DO
update project as p
set p.state = 'closed'
where test_time < now() and not exists(
	select *
    from task as t
    where t.project_id = p.id and t.state = 'open'
);

DROP EVENT IF EXISTS `auto_get_activity`;
CREATE EVENT `auto_get_activity`
ON SCHEDULE EVERY 1 DAY
DO
update user as u
set activity = (
	select ifnull(sum(log.activity_point),0)
    from user_log as log
    where log.time > date_sub(now(),interval 1 month) and log.user_id = u.id
)

DROP EVENT IF EXISTS `auto_project_preference_difficulty`;
CREATE EVENT `auto_project_preference_difficulty`
ON SCHEDULE EVERY 1 MINUTE
DO
update project_preference as proj
set difficulty = (
	select difficulty
    from(
	select u.user_id as user_id,avg(p.difficulty) as difficulty
	from project as p join user_project as u
	where p.id = u.project_id and u.join_time > date_sub(now(),interval 1 month)
	group by u.user_id) as temp
    where temp.user_id = proj.user_id
)

DROP EVENT IF EXISTS `auto_project_preference_device`;
CREATE EVENT `auto_project_preference_device`
ON SCHEDULE EVERY 1 MINUTE
DO
update project_preference as proj
set device_id = (
	select device_id
    from(
	select user_id,device_id
from(
    select user_id,device_id,count(device_id) as n
    from(
	    select u.user_id as user_id,p.device_id as device_id
	    from project as p join user_project as u
	    where p.id = u.project_id and u.join_time > date_sub(now(),interval 1 month)
    )temp
    group by user_id,device_id
    order by n desc,user_id
    ) ans
where ans.n >= all(
	select ans1.n
    from(

        select user_id,device_id,count(device_id) as n
        from(
	        select u.user_id as user_id,p.device_id as device_id
	        from project as p join user_project as u
	        where p.id = u.project_id and u.join_time > date_sub(now(),interval 1 month)
        )temp
        group by user_id,device_id
        order by n desc,user_id
    ) ans1
	where ans1.user_id=ans.user_id
)
group by user_id
) as temp2
    where temp2.user_id = proj.user_id
)


DROP EVENT IF EXISTS `auto_project_preference_type`;
CREATE EVENT `auto_project_preference_type`
ON SCHEDULE EVERY 1 MINUTE
DO
update project_preference as proj
set type = (
	select type
    from(
	select user_id,type
from(
    select user_id,type,count(type) as n
    from(
	    select u.user_id as user_id,p.type as type
	    from project as p join user_project as u
	    where p.id = u.project_id and u.join_time > date_sub(now(),interval 1 month)
    )temp
    group by user_id,type
    order by n desc,user_id
    ) ans
where ans.n >= all(
	select ans1.n
    from(

        select user_id,type,count(type) as n
        from(
	        select u.user_id as user_id,p.type as type
	        from project as p join user_project as u
	        where p.id = u.project_id and u.join_time > date_sub(now(),interval 1 month)
        )temp
        group by user_id,type
        order by n desc,user_id
    ) ans1
	where ans1.user_id=ans.user_id
)
group by user_id) as temp2
    where temp2.user_id = proj.user_id
)


-- 一些样例
INSERT INTO `user_device` VALUES (1,3,1)
INSERT INTO `user_device` VALUES (2,3,2)
INSERT INTO `user_device` VALUES (3,3,3)
INSERT INTO `user_device` VALUES (4,3,4)
INSERT INTO `user_device` VALUES (5,3,5)
INSERT INTO `user_device` VALUES (6,3,6)
INSERT INTO `user_device` VALUES (7,3,7)
INSERT INTO `user_device` VALUES (8,3,8)
INSERT INTO `user_device` VALUES (9,4,4)
INSERT INTO `user_device` VALUES (10,5,5)
INSERT INTO `user_device` VALUES (11,6,6)
INSERT INTO `user_device` VALUES (12,7,7)
INSERT INTO `user_device` VALUES (13,8,1)
INSERT INTO `user_device` VALUES (14,9,2)
INSERT INTO `user_device` VALUES (15,10,6)
INSERT INTO `user_device` VALUES (16,11,5)
INSERT INTO `user_project` VALUES (1,2,3,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (2,2,2,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (3,2,4,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (4,3,2,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (5,1,3,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (6,4,2,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (7,5,3,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (8,6,2,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (9,1,4,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (10,1,5,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (11,1,6,'2022-03-28 18:00:00');
INSERT INTO `user_project` VALUES (12,1,7,'2022-03-28 18:00:00');
INSERT INTO `user_task` VALUES (1,1,3);
INSERT INTO `user_task` VALUES (2,2,3);
INSERT INTO `user_task` VALUES (3,1,2);
INSERT INTO `user_task` VALUES (4,1,4);
INSERT INTO `user_task` VALUES (5,1,5);
INSERT INTO `user_task` VALUES (6,1,6);
INSERT INTO `user_task` VALUES (7,1,7);
SET FOREIGN_KEY_CHECKS = 1;