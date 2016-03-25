/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : wei_server

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-11-25 11:07:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `w_menu_button`
-- ----------------------------
DROP TABLE IF EXISTS `w_menu_button`;
CREATE TABLE `w_menu_button` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(200) default NULL COMMENT '菜单名称',
  `type` varchar(200) default NULL COMMENT '菜单类型',
  `keyMenu` varchar(200) default NULL COMMENT '消息接口推送',
  `url` varchar(200) default NULL COMMENT '网页链接',
  `menuId` bigint(20) default '0' COMMENT '父类菜单Id',
  `createDate` varchar(200) default NULL,
  `delFlag` int(20) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of w_menu_button
-- ----------------------------
