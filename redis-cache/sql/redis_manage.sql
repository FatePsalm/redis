/*
 Navicat Premium Data Transfer

 Source Server         : MySql-3306-docker
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 127.0.0.1:3306
 Source Schema         : myredis

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 28/02/2020 19:01:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for redis_manage
-- ----------------------------
DROP TABLE IF EXISTS `redis_manage`;
CREATE TABLE `redis_manage`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classify` int(11) NULL DEFAULT NULL COMMENT '分类',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `redis_index` int(11) NULL DEFAULT NULL COMMENT 'key_index',
  `create_time` timestamp(3) NULL DEFAULT NULL COMMENT '创建时间',
  `ops_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作命令',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
