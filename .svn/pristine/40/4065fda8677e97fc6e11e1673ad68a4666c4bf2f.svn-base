/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : weixin_server

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-11-26 15:48:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `weixin_weixintosender`
-- ----------------------------
DROP TABLE IF EXISTS `weixin_weixintosender`;
CREATE TABLE `weixin_weixintosender` (
  `id` bigint(20) NOT NULL auto_increment,
  `senderId` varchar(255) default '',
  `toUserName` varchar(255) default '' COMMENT '接收者(微信服务号)',
  `fromUserName` varchar(255) default '' COMMENT '发送者(关注服务号的人)',
  `msgType` varchar(255) default '' COMMENT '发送类型(文本、图片、语音)',
  `content` varchar(255) default '' COMMENT '发送内容',
  `createDate` varchar(255) default '' COMMENT '创建时间',
  `delFlag` tinyint(1) default NULL,
  `useNickname` varchar(20) default '',
  `useSex` int(11) default NULL,
  `useAddress` varchar(20) default '',
  `useHeadimgurl` varchar(255) default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weixin_weixintosender
-- ----------------------------
INSERT INTO `weixin_weixintosender` VALUES ('1', 'oL1H0jqQbChbHQC7WDmSqpQnDWtI', 'oL1H0jqQbChbHQC7WDmSqpQnDWtI', '砝码', 'text', '无法识别您的输入，请回复\"0\"返回主菜单。', '2014-11-25 15:51:59', '0', '吴', '1', '中国江苏徐州', 'http://wx.qlogo.cn/mmopen/PlzPNiaE4ggiaHtfhuTSe0KU3ibia40ugucUEOFprDU1Na9claU9HbN4JpoyPQ8Mc29Bhx7nR8yrYNot1S8RRy25oBvtjpca3UDk/0');
