-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: apidev
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `brief` varchar(200) DEFAULT NULL,
  `content` longtext NOT NULL,
  `click` int(11) NOT NULL DEFAULT '0',
  `type` varchar(20) NOT NULL DEFAULT 'PAGE',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `moduleId` varchar(50) NOT NULL DEFAULT 'top',
  `mkey` varchar(20) DEFAULT NULL COMMENT 'key，唯一键，页面唯一标识',
  `canDelete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可删除，可修key，默认可以',
  `category` varchar(50) DEFAULT NULL,
  `canComment` tinyint(4) NOT NULL DEFAULT '1',
  `commentCount` int(11) NOT NULL DEFAULT '0',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `markdown` text NOT NULL,
  `projectId` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mkey_UNIQUE` (`mkey`),
  KEY `index_mod_type_cat_seq_time` (`moduleId`,`type`,`category`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES ('152611998276001000007','V8.0.0使用视频-用户版','V8.0.0使用视频-用户版，觉得有用的可以去https://gitee.com/CrapApi/CrapApi 上支持下，有钱的捐赠的咖啡钱，没钱的捧个人场，fork下、star一下','<p data-source-line=\"3\">V8.0.0使用视频-用户版，觉得有用的可以去 <a href=\"https://gitee.com/CrapApi/CrapApi\">https://gitee.com/CrapApi/CrapApi</a> 上支持下，有钱的捐赠的咖啡钱，没钱的捧个人场，fork下、star一下</p>\n<iframe height=\"498\" width=\"100%\" src=\"http://player.youku.com/embed/XMzU4NjQwODIzNg==\" frameborder=\"0\" \'allowfullscreen\'=\"\"></iframe>',1,'ARTICLE',2,'2018-05-12 10:13:03','152611971673309000002',NULL,1,'帮助文档',1,0,1,'V8.0.0使用视频-用户版，觉得有用的可以去 https://gitee.com/CrapApi/CrapApi 上支持下，有钱的捐赠的咖啡钱，没钱的捧个人场，fork下、star一下\n\n&lt;iframe height=498 width=100% src=\'http://player.youku.com/embed/XMzU4NjQwODIzNg==\' frameborder=0 \'allowfullscreen\'&gt;&lt;/iframe&gt;','152611968062607000001');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` varchar(50) NOT NULL,
  `articleId` varchar(50) NOT NULL,
  `content` varchar(200) NOT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `reply` varchar(200) NOT NULL DEFAULT '',
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-31 00:00:00',
  `userName` varchar(50) NOT NULL COMMENT '匿名',
  `avatarUrl` varchar(500) NOT NULL DEFAULT 'resources/avatar/avatar0.jpg' COMMENT '用户头像 ',
  PRIMARY KEY (`id`),
  KEY `index_articleId_seq_time` (`articleId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `debug`
--

DROP TABLE IF EXISTS `debug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `debug` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '文件夹名称',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `interfaceId` varchar(50) DEFAULT '',
  `moduleId` varchar(50) NOT NULL,
  `method` varchar(10) NOT NULL,
  `url` varchar(500) NOT NULL,
  `params` varchar(1000) NOT NULL,
  `headers` varchar(1000) NOT NULL,
  `paramType` varchar(100) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `uid` varchar(50) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `index_status_seq_createTime` (`status`,`sequence`,`createTime`),
  KEY `index_userId_seq_createTime` (`interfaceId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debug`
--

LOCK TABLES `debug` WRITE;
/*!40000 ALTER TABLE `debug` DISABLE KEYS */;
/*!40000 ALTER TABLE `debug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `error`
--

DROP TABLE IF EXISTS `error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `errorCode` varchar(50) NOT NULL COMMENT '错误码编码',
  `errorMsg` varchar(128) NOT NULL COMMENT '错误码描述',
  `projectId` varchar(50) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`),
  KEY `index_mod_seq_time` (`projectId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error`
--

LOCK TABLES `error` WRITE;
/*!40000 ALTER TABLE `error` DISABLE KEYS */;
INSERT INTO `error` VALUES ('152611980854003000004','000001','错误码1','152611968062607000001','2018-05-12 10:10:09',1,0);
/*!40000 ALTER TABLE `error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot_search`
--

DROP TABLE IF EXISTS `hot_search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hot_search` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '搜索次数',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-31 00:00:00',
  `keyword` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '搜索关键字',
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyword_UNIQUE` (`keyword`),
  KEY `index_times` (`times`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot_search`
--

LOCK TABLES `hot_search` WRITE;
/*!40000 ALTER TABLE `hot_search` DISABLE KEYS */;
/*!40000 ALTER TABLE `hot_search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface`
--

DROP TABLE IF EXISTS `interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interface` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `url` varchar(200) NOT NULL COMMENT 'api链接',
  `method` varchar(50) NOT NULL COMMENT ' 请求方式',
  `param` text COMMENT '参数列表',
  `paramRemark` text COMMENT '请求参数备注',
  `requestExam` text COMMENT '请求示例',
  `responseParam` text COMMENT '返回参数说明',
  `errorList` text COMMENT '接口错误码列表',
  `trueExam` text COMMENT '正确返回示例',
  `falseExam` text COMMENT '错误返回示例',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用;0不可用；1可用;-1 删除',
  `moduleId` varchar(50) NOT NULL COMMENT '所属模块ID',
  `interfaceName` varchar(100) NOT NULL COMMENT '接口名',
  `remark` text,
  `errors` text COMMENT '错误码、错误码信息',
  `updateBy` varchar(100) DEFAULT NULL,
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-31 00:00:00',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` varchar(20) NOT NULL DEFAULT '1.0' COMMENT '版本号',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `header` text,
  `fullUrl` varchar(255) NOT NULL DEFAULT '',
  `monitorType` int(11) NOT NULL DEFAULT '0' COMMENT '监控类型，多选：\nNetwork("网络异常",1),Include("包含指定字符串",2),NotInclude("不包含指定字符串",3),NotEqual("不等于指定字符串",4);	\n',
  `monitorText` varchar(500) NOT NULL DEFAULT '' COMMENT '监控比较内容',
  `monitorEmails` varchar(200) DEFAULT NULL,
  `isTemplate` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是模板',
  `projectId` varchar(50) NOT NULL DEFAULT '',
  `contentType` varchar(45) NOT NULL DEFAULT 'application/json' COMMENT '接口返回contentType',
  PRIMARY KEY (`id`),
  KEY `Index_fullUrl` (`fullUrl`),
  KEY `index_mod_seq_time` (`moduleId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interface`
--

LOCK TABLES `interface` WRITE;
/*!40000 ALTER TABLE `interface` DISABLE KEYS */;
INSERT INTO `interface` VALUES ('152611979419112000003','/test.json','GET,','form=[{\"name\":\"test\",\"def\":\"test\",\"remark\":\"test\",\"necessary\":\"true\",\"inUrl\":\"false\",\"type\":\"string\"}]','[]','请求地址:http://test.com/test.json\r\n请求头:\r\n	header=1\r\n请求参数:\r\n	test=test','[{\"deep\":\"0\",\"name\":\"test\",\"remark\":\"tet\",\"type\":\"string\",\"necessary\":\"true\"}]','000001,','{\n}','{}',0,'152611971673309000002','测试接口1','<h3>我是测试项目</h3>','[{\"createTime\":{\"date\":12,\"day\":6,\"hours\":18,\"minutes\":10,\"month\":4,\"seconds\":9,\"time\":1526119809000,\"timezoneOffset\":-480,\"year\":118},\"errorCode\":\"000001\",\"errorMsg\":\"错误码1\",\"id\":\"152611980854003000004\",\"projectId\":\"152611968062607000001\",\"sequence\":0,\"status\":1}]','userName：admin | trueName：超级管理员','2018-05-12 10:09:54','2018-05-12 10:09:54','1.0.1',1,'[{\"name\":\"header\",\"def\":\"1\",\"remark\":\"备注\",\"necessary\":\"true\",\"type\":\"string\"}]','http://test.com/test.json',0,'',NULL,'\0','152611968062607000001','application/json');
/*!40000 ALTER TABLE `interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `modelClass` varchar(50) NOT NULL,
  `modelName` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `updateBy` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人',
  `remark` varchar(100) NOT NULL,
  `content` longtext NOT NULL,
  `identy` varchar(50) NOT NULL COMMENT '数据唯一主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES ('152611982039908000005',1,'2018-05-12 10:10:20',0,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','测试接口1','{\"contentType\":\"application/json\",\"createTime\":{\"date\":12,\"day\":6,\"hours\":18,\"minutes\":9,\"month\":4,\"seconds\":54,\"time\":1526119794000,\"timezoneOffset\":-480,\"year\":118},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"{}\",\"fullUrl\":\"http://test.com/test.json\",\"header\":\"[{\\\"name\\\":\\\"header\\\",\\\"def\\\":\\\"1\\\",\\\"remark\\\":\\\"备注\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"152611979419112000003\",\"interfaceName\":\"测试接口1\",\"isTemplate\":false,\"method\":\"GET,\",\"moduleId\":\"152611971673309000002\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"form=[{\\\"name\\\":\\\"test\\\",\\\"def\\\":\\\"test\\\",\\\"remark\\\":\\\"test\\\",\\\"necessary\\\":\\\"true\\\",\\\"inUrl\\\":\\\"false\\\",\\\"type\\\":\\\"string\\\"}]\",\"paramRemark\":\"[]\",\"projectId\":\"152611968062607000001\",\"remark\":\"<h3>我是测试项目<\\/h3>\",\"requestExam\":\"请求地址:http://test.com/test.json\\r\\n请求头:\\r\\n\\theader=1\\r\\n请求参数:\\r\\n\\ttest=test\",\"responseParam\":\"[{\\\"deep\\\":\\\"0\\\",\\\"name\\\":\\\"test\\\",\\\"remark\\\":\\\"tet\\\",\\\"type\\\":\\\"string\\\",\\\"necessary\\\":\\\"true\\\"}]\",\"sequence\":1,\"status\":0,\"trueExam\":\"{\\n}\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":12,\"day\":6,\"hours\":18,\"minutes\":9,\"month\":4,\"seconds\":54,\"time\":1526119794000,\"timezoneOffset\":-480,\"year\":118},\"url\":\"/test.json\",\"version\":\"1.0.1\"}','152611979419112000003'),('152611988182408000006',1,'2018-05-12 10:11:22',1,'Module','模块','UPDATE','超级管理员','我是测试模块1','{\"canDelete\":1,\"category\":\"分类1,分类2\",\"createTime\":{\"date\":12,\"day\":6,\"hours\":18,\"minutes\":8,\"month\":4,\"seconds\":37,\"time\":1526119717000,\"timezoneOffset\":-480,\"year\":118},\"id\":\"152611971673309000002\",\"name\":\"我是测试模块1\",\"projectId\":\"152611968062607000001\",\"remark\":\"我是测试模块1\",\"sequence\":1,\"status\":1,\"templateId\":\"\",\"url\":\"http://test.com\",\"userId\":\"admin\",\"version\":0}','152611971673309000002');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` varchar(50) NOT NULL DEFAULT '导航菜单编号',
  `menuName` varchar(50) NOT NULL COMMENT '菜单名称',
  `menuUrl` varchar(200) DEFAULT NULL COMMENT '菜单链接',
  `roleIds` varchar(512) DEFAULT NULL COMMENT '角色可见集合  （ID之间以逗号分隔）',
  `parentId` varchar(50) DEFAULT '0',
  `iconRemark` varchar(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT '前端菜单、后台菜单',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`),
  KEY `index_parentId` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('152612004179705000008','快捷菜单',NULL,NULL,'0','<i class=\"iconfont\"></i>','FRONT','2018-05-12 10:14:02',1,1),('152612008276705000009','v8.0.0用户使用视频','index.do#/152611968062607000001/article/detail/152611971673309000002/ARTICLE/152611998276001000007',NULL,'152612004179705000008',NULL,'FRONT','2018-05-12 10:14:43',1,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `id` varchar(50) NOT NULL COMMENT '所属模块ID',
  `name` varchar(100) NOT NULL COMMENT '所属模块名称',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '模块地址',
  `canDelete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1：可删除，0：不可删除',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `userId` varchar(50) NOT NULL DEFAULT '',
  `projectId` varchar(50) NOT NULL DEFAULT '',
  `templateId` varchar(50) DEFAULT NULL COMMENT '接口模板ID',
  `version` int(11) NOT NULL DEFAULT '0',
  `category` varchar(200) NOT NULL DEFAULT '' COMMENT '文章分类，多个分类以逗号分割，每个分类最多10个字',
  PRIMARY KEY (`id`),
  KEY `index_uid_seq_time` (`userId`,`sequence`,`createTime`),
  KEY `index_pid_seq_time` (`projectId`,`sequence`,`createTime`),
  KEY `index_templateId` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES ('152611971673309000002','我是测试模块1','2018-05-12 10:08:37',1,1,'http://test.com',1,'我是测试模块1','admin','152611968062607000001',NULL,0,'分类1,分类2,帮助文档');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` varchar(50) NOT NULL COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '2：推荐项目，3，管理员管理项目，4，管理管理&推荐项目，-1：默认debug项目',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `userId` varchar(50) NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:私有项目，2公开项目，3公开推荐',
  `password` varchar(45) DEFAULT NULL,
  `cover` varchar(200) NOT NULL DEFAULT 'resources/images/cover.png' COMMENT '项目封面',
  `luceneSearch` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否允许建立Lucene搜索',
  PRIMARY KEY (`id`),
  KEY `index_status_seq_createTime` (`status`,`sequence`,`createTime`),
  KEY `index_userId_seq_createTime` (`userId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('152611968062607000001','测试项目','2018-05-12 10:08:01',2,1,'我是测试项目','admin',2,NULL,'resources/images/cover.png',1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_user`
--

DROP TABLE IF EXISTS `project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_user` (
  `id` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `projectId` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `addModule` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加模块',
  `delModule` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除模块',
  `modModule` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可是修改模块',
  `addInter` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加接口',
  `delInter` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除接口',
  `modInter` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以修改接口',
  `addArticle` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加文章',
  `delArticle` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除文章',
  `modArticle` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以修改文章',
  `addSource` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加文件',
  `delSource` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除文件',
  `modSource` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以修改文件',
  `addDict` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加数据库表',
  `delDict` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除数据库表',
  `modDict` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以修改数据库表',
  `userEmail` varchar(45) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `addError` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以添加错误码',
  `delError` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可以删除错误码',
  `modError` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以修改错误码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_user` (`userId`,`projectId`),
  KEY `index_uid_seq_time` (`userId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` varchar(50) NOT NULL COMMENT '角色ID',
  `roleName` varchar(50) NOT NULL COMMENT '角色名称',
  `auth` text NOT NULL,
  `authName` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `mkey` varchar(20) NOT NULL,
  `value` varchar(500) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `type` varchar(10) NOT NULL DEFAULT 'TEXT' COMMENT '设置类型（IMAGE,LINK,TEXT）',
  `canDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1：可删除，0：不可删除',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `show` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否在前端显示，1：是，0：否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`mkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('0','tes','test555','test555','2017-12-29 19:44:49',1,'TEXT',0,1,1),('062f01ae-e50b-4dd3-808b-b4a6d65eeadc','LOGO','http://api.crap.cn/resources/upload/images/2016-04-27/231357cAgbyp.png','网站主logo，可以直接在value中填写绝对链接地址，也可以自行上传图片','2016-03-29 04:23:18',1,'IMAGE',0,90,1),('8a95bc2f-ea61-4dd6-8163-d9c520b28181','VISITCODE','true','游客访问私密模块输入密码的同时是否需要输入图像验证码？true为需要，其他为不需要','2016-03-31 10:07:14',0,'TEXT',0,90,1),('98ecca1b-f762-4cd3-831a-4042b36419d8','VERIFICATIONCODE','false','是否开启安全登录？ture为开启，其他为不开启，开启后登录将需要输入图片验证码','2016-03-31 10:07:58',0,'TEXT',0,90,1),('b97a3a75-c1c3-42cc-b944-8fb5ac5c5f49','SECRETKEY','crapApiKey','秘钥，用于cookie加密等','2016-03-30 17:04:40',0,'TEXT',0,90,1),('de94c622-02fc-4b39-9cc5-0c24870ac21f','TITLE','CrapApi|Api接口管理系统','站点标题','2016-03-30 11:09:13',1,'TEXT',0,90,1),('e0dec957-5043-4c6e-9225-960fbc401116','ICON','http://api2.crap.cn/resources/upload/images/2016-04-04/063633hG35aC.ico','站点ICON图标（浏览器标题栏图标）','2016-03-30 11:49:41',1,'IMAGE',0,100,1),('e2a493a7-c4f0-4cbb-832f-4495a7074252','LOGINBG','https://dn-coding-net-production-static.qbox.me/d58141c9-9a0c-40b0-a408-5935fd70670f.jpg','登录背景图','2016-08-25 00:02:28',1,'IMAGE',0,100,1),('e2a493a7-c888-4cbb-832f-4495a7074252','TITLEBG','https://dn-coding-net-production-static.qbox.me/3c9bcbc0-15dc-4a6f-a81f-5112936b7773.jpg','头部标题背景图：resources/images/project.jpg,为空则显示主色调','2016-08-25 00:02:28',1,'IMAGE',0,99,1),('ecd676c2-0b04-4b4a-a049-4a825221a6d0','BG_COLOR','#f7f7f7','前端显示背景颜色','2016-04-28 05:07:37',1,'COLOR',0,98,1),('ef157b7f-cc53-4a41-9679-d243d478023d','COMMENTCODE','true','游客评论是否需要输入验证码','2016-04-14 14:47:29',1,'TEXT',0,90,1),('f1c8dc8b-9cd8-4839-b38a-1cea3ceb3942','MAX_WIDTH','1200','前端显示最大宽度（数字，建议：900-1200）','2016-04-28 05:07:37',1,'TEXT',0,90,1),('fff-1111-d4839-b38a-898343435462','ANONYMOUS_COMMENT','false','是否允许匿名评论, true:允许','2017-08-06 07:55:00',1,'TEXT',0,100,1),('fff-8888-d4839-b38a-898343435462','ICONFONT','//at.alicdn.com/t/font_860205_mty2sxmdeye','图标地址（cdn图标库或本地图标库）','2018-05-01 12:27:00',1,'ICONFONT',0,100,1),('fff-9191-d4839-b38a-898343435462','DESCRIPTION','免费开源的API接口管理系统、文档管理系统。系统特点：极致简单、开源开放、技术前沿。主要功能有：接口管理、接口调试、文档管理、数据库表管理、文章管理....。','搜索引描述','2018-05-04 14:53:11',1,'TEXT',0,100,1),('fff-9999-d4839-b38a-898343435462','KEYWORDS','CrapApi,api,crap,接口管理,应用接口管理,开源接口管理,开源api接口管理,api接口管理','搜索引擎关键字','2018-05-04 14:53:11',1,'TEXT',0,100,1),('foc8dc8b-9cd8-4839-b38a-1cea3ceb3942','FOOTER_BG_COLOR','#000000','前端顶部、底部颜色 #383942','2016-04-28 05:07:37',1,'COLOR',0,91,1),('fpc8dc8b-9cd8-4839-b38a-1cea3ceb3942','FOOTER_COLOR','#a9a9a9','前端顶部、底部字体颜色 #a9a9a9','2016-04-28 05:07:37',1,'COLOR',0,97,1),('fpmbdc00-9cd8-4839-b38a-1cea3ceb3945','LUCENE_DIR','/usr/local/crap/lucene','Lucene全文检索文件存储地址','2016-06-06 05:07:37',1,'TEXT',0,22,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3942','MAIN_COLOR','#407aaa','网站主色调，默认#CC6699 #007742','2016-04-28 05:07:37',1,'COLOR',0,100,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3945','FONT_FAMILY','\"Lantinghei SC\", \"Open Sans\", Arial, \"Hiragino Sans GB\", \"Microsoft YaHei\", \"STHeiti\", \"WenQuanYi Micro Hei\", SimSun, sans-serif;','网站字体','2016-06-06 05:07:37',1,'FONTFAMILY',0,77,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3999','ADORN_COLOR','#f82c1d','装饰背景颜色:左侧导航条背景颜色 #1c2034','2016-06-06 05:07:37',1,'COLOR',0,26,1),('fpmbdc8b-9cd8-4839-b38a-898343435462','INDEX_PAGE','dashboard.htm','前端首页：只能以index.do、font/ 开头的url','2017-06-11 09:57:39',1,'INDEXPAGE',0,66,1);
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `source` (
  `id` varchar(50) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `name` varchar(100) NOT NULL COMMENT '文件名称',
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-31 00:00:00',
  `moduleId` varchar(50) NOT NULL DEFAULT '0' COMMENT '模块ID',
  `remark` varchar(5000) NOT NULL DEFAULT '' COMMENT '备注',
  `filePath` varchar(200) NOT NULL DEFAULT '' COMMENT '文件目录',
  `projectId` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `index_mod_seq_time` (`moduleId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `source`
--

LOCK TABLES `source` WRITE;
/*!40000 ALTER TABLE `source` DISABLE KEYS */;
/*!40000 ALTER TABLE `source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL DEFAULT '',
  `trueName` varchar(50) NOT NULL DEFAULT '' COMMENT '用户真实姓名或昵称',
  `roleId` varchar(1024) NOT NULL DEFAULT '',
  `roleName` varchar(1024) NOT NULL DEFAULT '',
  `auth` varchar(1024) NOT NULL DEFAULT '',
  `authName` varchar(1024) NOT NULL DEFAULT '',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `type` tinyint(4) NOT NULL DEFAULT '100' COMMENT '用户类型：1普通用户，100：管理员',
  `email` varchar(45) DEFAULT NULL,
  `avatarUrl` varchar(500) NOT NULL DEFAULT '' COMMENT '用户头像',
  `loginType` int(11) NOT NULL DEFAULT '0' COMMENT '0：账号登录，1：github登录，2：码云',
  `thirdlyId` varchar(100) DEFAULT NULL COMMENT '第三方唯一ID',
  `passwordSalt` varchar(20) DEFAULT NULL COMMENT '密码MD5盐',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginType_userName` (`userName`,`loginType`),
  UNIQUE KEY `loginType_Email` (`email`,`loginType`),
  KEY `index_thirdlyId` (`thirdlyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','admin','96e79218965eb72c92a549dd5a330112','超级管理员','super,','超级管理员,','','','2016-03-29 04:24:00',1,0,100,'ihsantang@163.com','',0,'',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'apidev'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-12 18:17:02
