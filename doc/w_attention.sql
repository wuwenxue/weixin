/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : wei_server

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-11-25 11:06:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `w_attention`
-- ----------------------------
DROP TABLE IF EXISTS `w_attention`;
CREATE TABLE `w_attention` (
  `id` bigint(10) NOT NULL auto_increment,
  `wid` varchar(200) default NULL COMMENT '关注者微信Id',
  `createDate` varchar(200) default NULL,
  `delFlag` int(10) default '0',
  `nickname` varchar(200) default NULL COMMENT '用户昵称',
  `sex` int(200) default '1' COMMENT '值为1时是男性，值为2时是女性，值为0时是未知 ',
  `address` varchar(200) default NULL COMMENT '地址',
  `headimgurl` varchar(200) default NULL COMMENT '头像',
  `subscribeTime` varchar(200) default NULL COMMENT '关注时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of w_attention
-- ----------------------------
