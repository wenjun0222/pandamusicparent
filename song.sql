/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.91.200
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.91.200:3306
 Source Schema         : song

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/11/2023 18:17:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(15) NOT NULL COMMENT '管理员id',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员密码',
  `admin_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员名字',
  `type` int(1) NULL DEFAULT 0 COMMENT '0表示超级管理员，1表示普通管理员',
  `pass_salt` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员密码盐值',
  `status` int(1) NULL DEFAULT 0 COMMENT '管理员状态,0为正常，1为禁用',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer`  (
  `id` int(15) NOT NULL COMMENT '歌手id',
  `singer_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌手名字',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌手职业',
  `nationality` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌手国籍',
  `masterpiece` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌手代表作',
  `singer_photo_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌手照片地址',
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建者',
  `editor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '歌手状态',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '歌手列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`  (
  `id` int(30) NOT NULL COMMENT '歌曲id',
  `song_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌曲名称',
  `song_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌曲文件地址',
  `song_duration` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌曲时长',
  `song_play_length` int(10) NULL DEFAULT NULL COMMENT '歌曲播放时的字节长度',
  `lrc_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌词路径',
  `singer_id` int(30) NULL DEFAULT NULL COMMENT '歌手id',
  `type_id` int(30) NULL DEFAULT NULL COMMENT '歌曲类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建者',
  `editor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新者',
  `status` int(10) NULL DEFAULT 0 COMMENT '0表示上架，1已下架',
  `is_deleted` int(10) NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '歌曲表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for song_type
-- ----------------------------
DROP TABLE IF EXISTS `song_type`;
CREATE TABLE `song_type`  (
  `id` int(15) NOT NULL COMMENT '歌曲类型id',
  `type_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌曲类型',
  `type_photo_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌曲类型照片url地址',
  `type_description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '歌曲类型介绍',
  `type_summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型简短概述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建者',
  `editor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新者',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '歌曲类型状态',
  `is_deleted` int(1) NOT NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '歌曲类型列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(20) NOT NULL COMMENT '用户唯一id',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名字',
  `pass_salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码盐值',
  `gender` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户性别',
  `avatar_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户头像地址',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建者',
  `editor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(5) NULL DEFAULT 0 COMMENT '用户状态,0为正常，1为禁用',
  `is_deleted` int(5) NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_favorite_song
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite_song`;
CREATE TABLE `user_favorite_song`  (
  `id` int(15) NOT NULL COMMENT '用户喜欢歌曲id',
  `user_id` int(15) NULL DEFAULT NULL COMMENT '用户id',
  `song_id` int(15) NULL DEFAULT NULL COMMENT '歌曲id',
  `singer_id` int(11) NULL DEFAULT NULL COMMENT '歌手id',
  `type_id` int(11) NOT NULL COMMENT '歌曲类型id',
  `is_deleted` int(1) NULL DEFAULT 0 COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`, `type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户喜欢的歌曲表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
