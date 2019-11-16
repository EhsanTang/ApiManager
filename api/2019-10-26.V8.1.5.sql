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
  `moduleId` varchar(50) DEFAULT 'top',
  `mkey` varchar(20) DEFAULT NULL COMMENT 'key，唯一键，页面唯一标识',
  `canDelete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可删除，可修key，默认可以',
  `category` varchar(50) DEFAULT NULL,
  `canComment` tinyint(4) NOT NULL DEFAULT '1',
  `commentCount` int(11) NOT NULL DEFAULT '0',
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `markdown` text NOT NULL,
  `projectId` varchar(50) NOT NULL DEFAULT '',
  `attributes` varchar(200) DEFAULT '',
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
INSERT INTO `article` VALUES ('155041717048101000025','ApiDebug http接口测试工具 -下载、安装、使用文档','支持本地/跨域测试、支持更多协议、支持本地测试记录、支持查看返回头、请求头、cookie等信息','<h4>原文地址：<a href=\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=155032424248009000006&amp;type=ARTICLE&amp;id=155037947655301000051\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255); font-size: 12px;\">ApiDebug接口调试插件</a></h4><h4>ApiDebug &amp; CrapApi-Plug区别：</h4><pre><code>ApiDebug为独立插件，与CrapApi接口管理系统接口数据没有互通，能不依赖服务器运行，服务器仅作数据备份<br><br>CrapApi-Plug为CrapApi接口管理软件的增强插件，不能单独使用，必须为联网状态才能使用，接口数据与管理系统接口一致\n安装插件后，接口调试页面可实现跨域调试，否则只能通过服务器端请求接口（无法调试本地接口、无法使用host配置，即：所有接口必须外网能访问才能调试）、安装接口后，只要本地浏览器登录，将自动携带网站cookie</code></pre><p><br></p><h4>一、下载</h4><p><br></p><ul><li>Chrome官网下载（需要访问google chrome market/webstore，请确保所在网络具备越过长城的能力）</li></ul><ol><li><a href=\"https://chrome.google.com/webstore/detail/apidebug-http-test/ieoejemkppmjcdfbnfphhpbfmallhfnc\" target=\"_blank\">Google官方网站英文版下载</a></li><li>中文版-审核中</li></ol><p><br></p><ul><li>离线下载（下载后需要自行拖到Chrome扩展程序）：</li></ul><ol><a href=\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\" target=\"_blank\"></a><li><a href=\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\" target=\"_blank\"></a><a href=\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/apiDebug.v1.0.4.crx\" target=\"_blank\">apiDebug.v1.0.4.crx</a>&nbsp;（英文版）<br></li><li><a href=\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/apiDebug.v.1.0.7.crx\" target=\"_blank\">apiDebug.v1.0.7.crx</a>（英文版）<br></li></ol><h4>二、插件安装（离线下载版）</h4><p><br></p><h5><ul><li>下载离线版后，打开chrome浏览器</li></ul></h5><h5><ul><li>找到下载后的文件，拖动至页面-&gt; 点击【添加扩展程序】即可</li></ul></h5><h4><br></h4><h4>三、插件使用</h4><p><br></p><h5><ul><li>打开浏览器，点击右上角按钮</li><li>如需要同步接口管理系统接口，请点击【登录】，如无需同步接口，请点击【接口调试】</li></ul></h5><h5><ul><li>登录后，点击右上角按钮选着需要测试的项目</li></ul></h5><p><br></p><h4>四、问题反馈</h4><p><br></p><ul><li>建议反馈请加QQ群：515305698<br></li><li>在线提交问题：<a href=\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143\" target=\"_blank\">http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143</a></li><li>直接在本文下留言</li></ul><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=\"http://api.crap.cn/resources/upload/images/2019-02/17161320efjm46.CAV.QxmnAq.1.jpeg\"></p><p><a href=\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\" target=\"_blank\"></a></p><p>*******&nbsp;<a href=\"http://api.crap.cn/\" target=\"_blank\">本文由CrapApi接口管理系统发布，转载请注明出处</a>&nbsp;***********</p>',3,'ARTICLE',2,'2019-02-16 17:26:10','155041592457909000006',NULL,1,NULL,1,0,1550417074953,'','155041466418307000002',''),('155041737565301000027','CrapApi-Plug 接口测试增强插件-下载、安装、使用文档','支持本地/跨域测试、支持更多协议、支持本地测试记录、支持查看返回头、请求头、cookie等信息','<h4>原文地址：<a href=\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=155032424248009000006&amp;type=ARTICLE&amp;id=153908629593401000133\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255); font-size: 12px;\">CrapApi-Plug 调试增强插件</a></h4><h4>ApiDebug &amp; CrapApi-Plug区别：</h4><h4><pre><code>ApiDebug为独立插件，与CrapApi接口管理系统接口数据没有互通，能不依赖服务器运行，服务器仅作数据备份\n\nCrapApi-Plug为CrapApi接口管理软件的增强插件，不能单独使用，必须为联网状态才能使用，接口数据与管理系统接口一致\n安装插件后，接口调试页面可实现跨域调试，否则只能通过服务器端请求接口（无法调试本地接口、无法使用host配置，即：所有接口必须外网能访问才能调试）、安装接口后，只要本地浏览器登录，将自动携带网站cookie<br></code></pre></h4><h4>一、下载</h4><p><br></p><ul><li>Chrome官网下载（需要访问google chrome market/webstore，请确保所在网络具备越过长城的能力）：<a href=\"https://chrome.google.com/webstore/detail/crapapi-%E6%8E%A5%E5%8F%A3%E8%B0%83%E8%AF%95%E5%B7%A5%E5%85%B7/dbdonnbbnhgojidhcogkapdldcheclnn\" target=\"_blank\">chrome.google.com下载</a></li></ul><ul><li>离线下载（下载后需要自行拖到Chrome扩展程序）：</li></ul><ol><li><a href=\"https://gitee.com/CrapApi/CrapApi-Plug/raw/master/release/chrome/crapapi-plug-v2.0.1.crx\" target=\"_blank\">离线下载v2.0.1</a><a href=\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/dbdonnbbnhgojidhcogkapdldcheclnn_main.crx\" target=\"_blank\"><br></a></li><li><a href=\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/dbdonnbbnhgojidhcogkapdldcheclnn_main.crx\" target=\"_blank\"></a><a href=\"https://gitee.com/CrapApi/CrapApi-Plug/raw/master/release/chrome/crapapi-plug.v2.0.4.crx\" target=\"_blank\">离线下载v2.0.4</a><br></li></ol><h4><br></h4><h4>二、插件安装（离线下载版）</h4><p><br></p><h5><ul><li>下载离线版后，打开chrome浏览器</li></ul></h5><h5><ul><li>找到下载后的文件，拖动至页面-&gt; 点击【添加扩展程序】即可</li></ul></h5><h4><br></h4><h4>三、插件使用</h4><p><br></p><h5><ul><li>打开浏览器，点击右上角按钮</li><li>如需要同步接口管理系统接口，请点击【登录】，如无需同步接口，请点击【接口调试】</li></ul></h5><h5><ul><li>登录后，点击右上角按钮选着需要测试的项目</li></ul></h5><p><br></p><h4>四、问题反馈</h4><p><br></p><ul><li>建议反馈请加QQ群：515305698<br></li><li>在线提交问题：<a href=\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143\" target=\"_blank\">http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143</a></li><li>直接在本文下留言</li></ul><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"http://api.crap.cn/resources/upload/images/2019-02/17161320efjm46.CAV.QxmnAq.1.jpeg\"><br></p><p>*******&nbsp;<a href=\"http://api.crap.cn/\" target=\"_blank\">本文由CrapApi接口管理系统发布，转载请注明出处</a>&nbsp;***********</p><p><br></p>',0,'ARTICLE',2,'2019-02-16 17:29:36','155041592457909000006',NULL,1,NULL,1,0,1550417287335,'','155041466418307000002',''),('155041763777601000031','通过建表语句导入-interface','点击标题进入详情，可以自动生成javaPO、mybatis xml配置代码哦~\n步骤：选着需要自动化生成的字段，点击右侧按钮，生成的代码在页面最下方','[{\"name\":\"id\",\"type\":\"varchar(50)\",\"def\":\"\",\"remark\":\"主键\",\"notNull\":\"false\",\"flag\":\"primary\"},{\"name\":\"monitorText\",\"type\":\"varchar(500)\",\"def\":\"\",\"remark\":\"监控比较内容\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"falseExam\",\"type\":\"text\",\"def\":\"\",\"remark\":\"错误返回示例\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"requestExam\",\"type\":\"text\",\"def\":\"\",\"remark\":\"请求示例\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"paramRemark\",\"type\":\"text\",\"def\":\"\",\"remark\":\"请求参数备注\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"remark\",\"type\":\"text\",\"def\":\"\",\"remark\":\"\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"monitorType\",\"type\":\"int(11)\",\"def\":\"0\",\"remark\":\"监控类型，多选：Network(\\\"网络异常\\\",1)\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"param\",\"type\":\"text\",\"def\":\"\",\"remark\":\"参数列表\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"updateBy\",\"type\":\"varchar(100)\",\"def\":\"NULL\",\"remark\":\"\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"responseParam\",\"type\":\"text\",\"def\":\"\",\"remark\":\"返回参数说明\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"monitorEmails\",\"type\":\"varchar(200)\",\"def\":\"NULL\",\"remark\":\"\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"interfaceName\",\"type\":\"varchar(100)\",\"def\":\"\",\"remark\":\"接口名\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"moduleId\",\"type\":\"varchar(50)\",\"def\":\"\",\"remark\":\"所属模块ID\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"contentType\",\"type\":\"varchar(45)\",\"def\":\"application/json\",\"remark\":\"接口返回contentType\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"method\",\"type\":\"varchar(50)\",\"def\":\"\",\"remark\":\"\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"errorList\",\"type\":\"text\",\"def\":\"\",\"remark\":\"接口错误码列表\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"fullUrl\",\"type\":\"varchar(255)\",\"def\":\"\",\"remark\":\"\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"updateTime\",\"type\":\"timestamp\",\"def\":\"2015-12-31\",\"remark\":\"\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"trueExam\",\"type\":\"text\",\"def\":\"\",\"remark\":\"正确返回示例\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"version\",\"type\":\"varchar(20)\",\"def\":\"1.0\",\"remark\":\"版本号\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"url\",\"type\":\"varchar(200)\",\"def\":\"\",\"remark\":\"api链接\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"sequence\",\"type\":\"int(11)\",\"def\":\"0\",\"remark\":\"排序，越大越靠前\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"createTime\",\"type\":\"timestamp\",\"def\":\"CURRENT_TIMESTAMP\",\"remark\":\"\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"isTemplate\",\"type\":\"bit(1)\",\"def\":\"b0\",\"remark\":\"是否是模板\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"header\",\"type\":\"text\",\"def\":\"\",\"remark\":\"\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"projectId\",\"type\":\"varchar(50)\",\"def\":\"\",\"remark\":\"\",\"notNull\":\"false\",\"flag\":\"common\"},{\"name\":\"errors\",\"type\":\"text\",\"def\":\"\",\"remark\":\"错误码、错误码信息\",\"notNull\":\"true\",\"flag\":\"common\"},{\"name\":\"status\",\"type\":\"tinyint(4)\",\"def\":\"1\",\"remark\":\"是否可用;0不可用；1可用;-1\",\"notNull\":\"false\",\"flag\":\"common\"}]',0,'DICTIONARY',1,'2019-02-16 17:33:58','155041592457909000006',NULL,1,NULL,1,0,1550417637776,'','155041466418307000002','');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bug`
--

DROP TABLE IF EXISTS `bug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `moduleId` varchar(50) DEFAULT 'top',
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `projectId` varchar(50) NOT NULL DEFAULT '',
  `attributes` varchar(200) DEFAULT '',
  `executor` varchar(50) DEFAULT NULL COMMENT '解决者',
  `executorStr` varchar(50) DEFAULT NULL COMMENT '解决者名称',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `creator` varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
  `creatorStr` varchar(50) DEFAULT NULL COMMENT '创建者名称',
  `tester` varchar(50) DEFAULT NULL COMMENT '验证者',
  `testerStr` varchar(50) DEFAULT NULL COMMENT '验证这名称',
  `priority` tinyint(4) NOT NULL COMMENT '优先级',
  `tracer` varchar(50) DEFAULT NULL COMMENT '抄送人',
  `tracerStr` varchar(50) DEFAULT NULL COMMENT '抄送人名称',
  `deadline` timestamp NULL DEFAULT NULL COMMENT '截止时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '问题类型',
  `severity` tinyint(4) DEFAULT NULL COMMENT '严重程度',
  `updateBy` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`id`),
  KEY `idx_project_sequence` (`projectId`,`sequence`),
  KEY `idx_project_creator` (`projectId`,`creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug`
--

LOCK TABLES `bug` WRITE;
/*!40000 ALTER TABLE `bug` DISABLE KEYS */;
INSERT INTO `bug` VALUES ('155041614940316000007','模块有多种用法，bug管理时可作为迭代哦~','<p>[缺陷描述]：我就想试试<br>[重现步骤]：你猜<br>[期望结果]：中个大奖<br>[原因定位]：运气不太好<br>[建议修改]：烧香<br></p>',10,'2019-02-17 07:09:09','155041590489909000005',1550415995490,'155041466418307000002',NULL,'155041470423906000003','测试用户','2019-02-17 07:12:53','admin','超级管理员','155041470423906000003','测试用户',4,'admin','超级管理员',NULL,4,1,NULL);
/*!40000 ALTER TABLE `bug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bug_log`
--

DROP TABLE IF EXISTS `bug_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug_log` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `operator` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operatorStr` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人名',
  `senior` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原用户',
  `type` tinyint(4) unsigned NOT NULL COMMENT '操作类型：1标题，2内容，3状态，4优先级，5严重程度，6问题类型，7模块，8执行人，9测试，10抄送人',
  `junior` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新用户',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `newValue` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新值',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `projectId` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `status` int(11) DEFAULT NULL,
  `bugId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '缺陷ID',
  `originalValue` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原始值',
  PRIMARY KEY (`id`),
  KEY `idx_bug_sequence` (`bugId`,`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_log`
--

LOCK TABLES `bug_log` WRITE;
/*!40000 ALTER TABLE `bug_log` DISABLE KEYS */;
INSERT INTO `bug_log` VALUES ('155041624636717000008','admin','超级管理员',NULL,6,NULL,NULL,'功能缺陷','2019-02-17 07:10:46','2019-02-17 07:10:46',1550416246367,'155041466418307000002',1,'155041614940316000007','线上问题'),('155041625973417000009','155041470423906000003','测试用户','155041470423906000003',9,'admin',NULL,'超级管理员','2019-02-17 07:10:59','2019-02-17 07:10:59',1550416259734,'155041466418307000002',1,'155041614940316000007','无'),('155041631127417000011','155041470423906000003','测试用户',NULL,4,NULL,NULL,'低','2019-02-17 07:11:51','2019-02-17 07:11:51',1550416311274,'155041466418307000002',1,'155041614940316000007','紧急'),('155041631338717000012','155041470423906000003','测试用户',NULL,4,NULL,NULL,'紧急','2019-02-17 07:11:53','2019-02-17 07:11:53',1550416313387,'155041466418307000002',1,'155041614940316000007','低'),('155041631683517000013','155041470423906000003','测试用户',NULL,6,NULL,NULL,'性能瓶颈','2019-02-17 07:11:56','2019-02-17 07:11:56',1550416316835,'155041466418307000002',1,'155041614940316000007','功能缺陷'),('155041633983817000014','admin','超级管理员','155041470423906000003',8,'admin',NULL,'超级管理员','2019-02-17 07:12:19','2019-02-17 07:12:19',1550416339838,'155041466418307000002',1,'155041614940316000007','无'),('155041634614117000015','admin','超级管理员','admin',8,'155041470423906000003',NULL,'测试用户','2019-02-17 07:12:26','2019-02-17 07:12:26',1550416346141,'155041466418307000002',1,'155041614940316000007','超级管理员'),('155041635262317000016','admin','超级管理员','admin',10,'155041470423906000003',NULL,'测试用户','2019-02-17 07:12:32','2019-02-17 07:12:32',1550416352623,'155041466418307000002',1,'155041614940316000007','无'),('155041635761217000017','admin','超级管理员','155041470423906000003',10,'admin',NULL,'超级管理员','2019-02-17 07:12:37','2019-02-17 07:12:37',1550416357612,'155041466418307000002',1,'155041614940316000007','测试用户'),('155041636479917000018','admin','超级管理员','admin',9,'155041470423906000003',NULL,'测试用户','2019-02-17 07:12:44','2019-02-17 07:12:44',1550416364799,'155041466418307000002',1,'155041614940316000007','超级管理员'),('155041637121417000019','admin','超级管理员','155041470423906000003',9,'admin',NULL,'超级管理员','2019-02-17 07:12:51','2019-02-17 07:12:51',1550416371214,'155041466418307000002',1,'155041614940316000007','测试用户'),('155041637338917000020','admin','超级管理员','admin',9,'155041470423906000003',NULL,'测试用户','2019-02-17 07:12:53','2019-02-17 07:12:53',1550416373389,'155041466418307000002',1,'155041614940316000007','超级管理员');
/*!40000 ALTER TABLE `bug_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` varchar(50) NOT NULL,
  `targetId` varchar(50) NOT NULL DEFAULT '' COMMENT '评论对象ID',
  `content` varchar(512) NOT NULL DEFAULT '',
  `userId` varchar(50) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `reply` varchar(200) NOT NULL DEFAULT '',
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-30 16:00:00',
  `userName` varchar(50) NOT NULL COMMENT '匿名',
  `avatarUrl` varchar(500) NOT NULL DEFAULT 'resources/avatar/avatar0.jpg' COMMENT '用户头像 ',
  `type` varchar(32) NOT NULL DEFAULT 'ARTICLE' COMMENT '评论类型：ARTICLE 文章，BUG 缺陷',
  PRIMARY KEY (`id`),
  KEY `index_articleId_seq_time` (`targetId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('155041629745404000010','155041614940316000007','怎么实现我不管，明天就要上线~~~~','155041470423906000003',NULL,10,'2019-02-17 07:11:37',1550416297454,'','2019-02-17 07:11:37','测试用户','resources/avatar/avatar4.jpg','BUG'),('155041724500604000026','155041717048101000025','赶紧试试~~~~~~~','admin',NULL,10,'2019-02-17 07:27:25',1550417245006,'','2019-02-17 07:27:25','超**理员','','ARTICLE');
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`),
  KEY `index_mod_seq_time` (`projectId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error`
--

LOCK TABLES `error` WRITE;
/*!40000 ALTER TABLE `error` DISABLE KEYS */;
INSERT INTO `error` VALUES ('155041796001903000037','order-00-001','创建订单失败','155041466418307000002','2019-02-16 17:39:20',1,1550417960019);
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
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-30 16:00:00',
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
  `moduleId` varchar(50) DEFAULT '' COMMENT '所属模块ID',
  `interfaceName` varchar(100) NOT NULL COMMENT '接口名',
  `remark` text,
  `errors` text COMMENT '错误码、错误码信息',
  `updateBy` varchar(100) DEFAULT NULL,
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-30 16:00:00',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` varchar(20) DEFAULT '1.0' COMMENT '版本号',
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
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
INSERT INTO `interface` VALUES ('155041672533112000021','http://api.crap.cn/visitor/example/xml.do','POST','<users>\n<name>用户名</name>\n<value>value值</value>\n</users>','[]',NULL,'[]','order-00-001,','{\n    \"page\":null,\n    \"success\":1,\n    \"data\":{\n        \"name\":\"用户名\",\n        \"value\":\"value值\"\n    },\n    \"error\":null,\n    \"others\":null\n}','{\n    \"isSuccess\":false\n}',1,'155041592457909000006','XML格式参数实现原理及录入','<blockquote><h2>XML参数</h2></blockquote><p><br></p><p>XML参数通JSON参数实现原理一样</p><p>1. 直接把xml数据当做一个参数，这种方法比较简单，xml数据就是一个字符串而已（xml包含特色符号，需要转义后在传），但是后端接收到该参数后需要单独处理，将xml数据转成对象<br></p><p>2. 直接在请求body中出入xml数据，后端直接通过springMVC自带的注解@RequestBody即可将xml数据转为对象（对象上需要添加注解@XmlRootElement），微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互</p><p>注意：必须添加http头信息【Content-Type=application/xml】，指定body类型为xml<br></p><p>服务端代码如下：</p><pre>@RequestMapping(<b>\"/front/example/xml.do\"</b>)<br>@ResponseBody<br><b>public </b>JsonResult xml(@RequestBody XmlParamsDto users) <b>throws </b>MyException{<br>   <b>return new </b>JsonResult(1, users);<br>}</pre><pre>@XmlRootElement(name=<b>\"users\"</b>)<br><b>public class </b>XmlParamsDto {<br>   <b>private </b>String <b>name</b>;<br>   <b>private </b>String <b>value</b>;<br>   <b>public </b>String getName() {<br>      <b>return </b><b>name</b>;<br>   }<br>   <b>public void </b>setName(String name) {<br>      <b>this</b>.<b>name </b>= name;<br>   }<br>   <b>public </b>String getValue() {<br>      <b>return </b><b>value</b>;<br>   }<br>   <b>public void </b>setValue(String value) {<br>      <b>this</b>.<b>value </b>= value;<br>   }<br>   <br>}</pre><p><br></p><p>可点击【<a href=\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/152575818145912000262\" target=\"\">调试</a>】或页面右上角【调试】按钮进行调试<br></p><p>借助本系统，可以将通过点击【自定义参数】，输入xml格式参数即可，由于xml格式的参数复杂，系统支持添加xml参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\" target=\"\">CrapApi视频介绍-用户版</a>】</p>','[{\"createTime\":1550417960000,\"errorCode\":\"order-00-001\",\"errorMsg\":\"创建订单失败\",\"id\":\"155041796001903000037\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417960019,\"status\":1}]','userName：admin | trueName：超级管理员','2019-02-16 17:18:45','2019-02-16 17:18:45','1.0',1550416661238,'[{\"name\":\"Content-Type\",\"def\":\"application/xml\",\"remark\":\"指定参数类型为xml格式\",\"necessary\":\"true\",\"type\":\"string\"}]','http://api.crap.cn/visitor/example/xml.do',0,'',NULL,'\0','155041466418307000002','application/json'),('155041751154012000029','http://api.crap.cn/visitor/example/json.do','POST','{\n\"id\":\"8989-dddvdg\",\n\"name\":\"文章标题-JSON格式参数演示\",\n\"brief\":\"快速入门json参数\",\n\"category\":\"分类\"\n}','[]',NULL,'[{\"name\":\"page\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"success\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"number\"},{\"name\":\"data\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"object\"},{\"name\":\"data->projectId\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->sequence\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->commentCount\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->canComment\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->category\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->canDelete\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->mkey\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->moduleId\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->createTime\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->status\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->type\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->click\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->brief\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->name\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"data->id\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"error\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"},{\"name\":\"others\",\"remark\":\"\",\"necessary\":\"true\",\"type\":\"string\"}]','order-00-001,','{\n    \"page\":null,\n    \"success\":1,\n    \"data\":{\n        \"id\":\"8989-dddvdg\",\n        \"name\":\"文章标题-JSON格式参数演示\",\n        \"brief\":\"快速入门json参数\",\n        \"click\":null,\n        \"type\":null,\n        \"status\":null,\n        \"createTime\":null,\n        \"moduleId\":null,\n        \"mkey\":null,\n        \"canDelete\":null,\n        \"category\":\"分类\",\n        \"canComment\":null,\n        \"commentCount\":null,\n        \"sequence\":null,\n        \"projectId\":null\n    },\n    \"error\":null,\n    \"others\":null\n}','{\n    \"isSuccess\":false\n}',1,'','JSON格式参数实现原理及录入','<blockquote><h2>JSON参数</h2></blockquote><p><br></p><p>在部分系统设计中，由于接口参数多或经常变动，因此会考虑使用json格式的参数。个人认为，有两种常用的方法可以实现：</p><p>1. 直接把json数据当做一个参数，这种方法比较简单，json数据就是一个字符串而已（json中会有双引号等，需要转义后在传），但是后端接收到该参数后需要单独处理，将json数据转成对象</p><p>2. 直接在请求body中出入json数据，后端直接通过springMVC自带的注解@RequestBody即可将json数据转为对象，微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互</p><p>注意：必须添加http头信息【Content-Type=application/json】，指定body类型为json<br></p><p>服务端代码如下：</p><pre>@RequestMapping(<b>\"/front/example/json.do\"</b>)<br>@ResponseBody<br><b>public </b>JsonResult json(@RequestBody Article article) <b>throws </b>MyException{<br>   <b>return new </b>JsonResult(1, article);<br>}</pre><p><br></p><p>可点击【<a href=\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/895aaabc-f3d7-461c-bad6-e9c44f6cdb4f\" target=\"\">调试</a>】或页面右上角【调试】按钮进行调试<br></p><p>借助本系统，可以将通过点击【自定义参数】，输入json格式参数即可，由于json格式的参数复杂，系统支持添加json参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\" target=\"\">CrapApi视频介绍-用户版</a>】</p>','[{\"createTime\":1550417960000,\"errorCode\":\"order-00-001\",\"errorMsg\":\"创建订单失败\",\"id\":\"155041796001903000037\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417960019,\"status\":1}]','userName：admin | trueName：超级管理员','2019-02-16 17:31:52','2019-02-16 17:31:52','1.0.0',1550417442881,'[{\"name\":\"Content-Type\",\"def\":\"application/json\",\"remark\":\"指定参数类型为json格式\",\"necessary\":\"true\",\"type\":\"string\"}]','http://api.crap.cn/visitor/example/json.do',0,'',NULL,'\0','155041466418307000002','application/json');
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `modelClass` varchar(50) NOT NULL,
  `modelName` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `updateBy` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人',
  `remark` varchar(100) NOT NULL,
  `content` longtext NOT NULL,
  `identy` varchar(50) NOT NULL COMMENT '数据唯一主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES ('155041682979608000022',1,'2019-02-16 17:20:30',1550416829796,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','404状态码接口测试','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"\",\"fullUrl\":\"http://api.crap.cn/visitor/example/error.do\",\"header\":\"[{\\\"name\\\":\\\"errorCode\\\",\\\"def\\\":\\\"404\\\",\\\"remark\\\":\\\"错误码\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"155041672533112000021\",\"interfaceName\":\"404状态码接口测试\",\"isTemplate\":false,\"method\":\"GET\",\"moduleId\":\"155041592457909000006\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"form=[]\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<p>404状态码接口测试<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[]\",\"sequence\":1550416661238,\"status\":1,\"trueExam\":\"\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/error.do\",\"version\":\"1.0\"}','155041672533112000021'),('155041703776808000023',1,'2019-02-16 17:23:58',1550417037768,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','XML格式参数实现原理及录入','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"\",\"fullUrl\":\"http://api.crap.cn/visitor/example/error.do\",\"header\":\"[]\",\"id\":\"155041672533112000021\",\"interfaceName\":\"404状态码接口测试\",\"isTemplate\":false,\"method\":\"GET\",\"moduleId\":\"155041592457909000006\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"form=[{\\\"name\\\":\\\"errorCode\\\",\\\"def\\\":\\\"404\\\",\\\"remark\\\":\\\"错误码\\\",\\\"necessary\\\":\\\"true\\\",\\\"inUrl\\\":\\\"false\\\",\\\"type\\\":\\\"string\\\"}]\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<p>404状态码接口测试<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[]\",\"sequence\":1550416661238,\"status\":1,\"trueExam\":\"\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/error.do\",\"version\":\"1.0\"}','155041672533112000021'),('155041705879208000024',1,'2019-02-16 17:24:19',1550417058792,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','XML格式参数实现原理及录入','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"{\\n    \\\"isSuccess\\\":false\\n}\",\"fullUrl\":\"http://api.crap.cn/visitor/example/xml.do\",\"header\":\"[{\\\"name\\\":\\\"Content-Type\\\",\\\"def\\\":\\\"application/xml\\\",\\\"remark\\\":\\\"指定参数类型为xml格式\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"155041672533112000021\",\"interfaceName\":\"XML格式参数实现原理及录入\",\"isTemplate\":false,\"method\":\"GET\",\"moduleId\":\"155041592457909000006\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"<users>\\n<name>用户名<\\/name>\\n<value>value值<\\/value>\\n<\\/users>\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<blockquote><h2>XML参数<\\/h2><\\/blockquote><p><br><\\/p><p>XML参数通JSON参数实现原理一样<\\/p><p>1. 直接把xml数据当做一个参数，这种方法比较简单，xml数据就是一个字符串而已（xml包含特色符号，需要转义后在传），但是后端接收到该参数后需要单独处理，将xml数据转成对象<br><\\/p><p>2. 直接在请求body中出入xml数据，后端直接通过springMVC自带的注解@RequestBody即可将xml数据转为对象（对象上需要添加注解@XmlRootElement），微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互<\\/p><p>注意：必须添加http头信息【Content-Type=application/xml】，指定body类型为xml<br><\\/p><p>服务端代码如下：<\\/p><pre>@RequestMapping(<b>\\\"/front/example/xml.do\\\"<\\/b>)<br>@ResponseBody<br><b>public <\\/b>JsonResult xml(@RequestBody XmlParamsDto users) <b>throws <\\/b>MyException{<br>   <b>return new <\\/b>JsonResult(1, users);<br>}<\\/pre><pre>@XmlRootElement(name=<b>\\\"users\\\"<\\/b>)<br><b>public class <\\/b>XmlParamsDto {<br>   <b>private <\\/b>String <b>name<\\/b>;<br>   <b>private <\\/b>String <b>value<\\/b>;<br>   <b>public <\\/b>String getName() {<br>      <b>return <\\/b><b>name<\\/b>;<br>   }<br>   <b>public void <\\/b>setName(String name) {<br>      <b>this<\\/b>.<b>name <\\/b>= name;<br>   }<br>   <b>public <\\/b>String getValue() {<br>      <b>return <\\/b><b>value<\\/b>;<br>   }<br>   <b>public void <\\/b>setValue(String value) {<br>      <b>this<\\/b>.<b>value <\\/b>= value;<br>   }<br>   <br>}<\\/pre><p><br><\\/p><p>可点击【<a href=\\\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/152575818145912000262\\\" target=\\\"\\\">调试<\\/a>】或页面右上角【调试】按钮进行调试<br><\\/p><p>借助本系统，可以将通过点击【自定义参数】，输入xml格式参数即可，由于xml格式的参数复杂，系统支持添加xml参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\\\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\\\" target=\\\"\\\">CrapApi视频介绍-用户版<\\/a>】<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[]\",\"sequence\":1550416661238,\"status\":1,\"trueExam\":\"{\\n    \\\"page\\\":null,\\n    \\\"success\\\":1,\\n    \\\"data\\\":{\\n        \\\"name\\\":\\\"用户名\\\",\\n        \\\"value\\\":\\\"value值\\\"\\n    },\\n    \\\"error\\\":null,\\n    \\\"others\\\":null\\n}\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/xml.do\",\"version\":\"1.0\"}','155041672533112000021'),('155041738205108000028',1,'2019-02-16 17:29:42',1550417382051,'ArticleWithBLOBs','文档','UPDATE','超级管理员','ApiDebug http接口测试工具 -下载、安装、使用文档','{\"attributes\":\"\",\"brief\":\"支持本地/跨域测试、支持更多协议、支持本地测试记录、支持查看返回头、请求头、cookie等信息\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":3,\"commentCount\":0,\"content\":\"<h4>原文地址：<a href=\\\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=155032424248009000006&amp;type=ARTICLE&amp;id=155037947655301000051\\\" target=\\\"_blank\\\" style=\\\"background-color: rgb(255, 255, 255); font-size: 12px;\\\">ApiDebug接口调试插件<\\/a><\\/h4><h4>ApiDebug &amp; CrapApi-Plug区别：<\\/h4><pre><code>ApiDebug为独立插件，与CrapApi接口管理系统接口数据没有互通，能不依赖服务器运行，服务器仅作数据备份<br><br>CrapApi-Plug为CrapApi接口管理软件的增强插件，不能单独使用，必须为联网状态才能使用，接口数据与管理系统接口一致\\n安装插件后，接口调试页面可实现跨域调试，否则只能通过服务器端请求接口（无法调试本地接口、无法使用host配置，即：所有接口必须外网能访问才能调试）、安装接口后，只要本地浏览器登录，将自动携带网站cookie<\\/code><\\/pre><p><br><\\/p><h4>一、下载<\\/h4><p><br><\\/p><ul><li>Chrome官网下载（需要访问google chrome market/webstore，请确保所在网络具备越过长城的能力）<\\/li><\\/ul><ol><li><a href=\\\"https://chrome.google.com/webstore/detail/apidebug-http-test/ieoejemkppmjcdfbnfphhpbfmallhfnc\\\" target=\\\"_blank\\\">Google官方网站英文版下载<\\/a><\\/li><li>中文版-审核中<\\/li><\\/ol><p><br><\\/p><ul><li>离线下载（下载后需要自行拖到Chrome扩展程序）：<\\/li><\\/ul><ol><a href=\\\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\\\" target=\\\"_blank\\\"><\\/a><li><a href=\\\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\\\" target=\\\"_blank\\\"><\\/a><a href=\\\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/apiDebug.v1.0.4.crx\\\" target=\\\"_blank\\\">apiDebug.v1.0.4.crx<\\/a>&nbsp;（英文版）<br><\\/li><li><a href=\\\"https://gitee.com/CrapApi/ApiDebug/raw/master/release/chrome/apiDebug.v.1.0.7.crx\\\" target=\\\"_blank\\\">apiDebug.v1.0.7.crx<\\/a>（英文版）<br><\\/li><\\/ol><h4>二、插件安装（离线下载版）<\\/h4><p><br><\\/p><h5><ul><li>下载离线版后，打开chrome浏览器<\\/li><\\/ul><\\/h5><h5><ul><li>找到下载后的文件，拖动至页面-&gt; 点击【添加扩展程序】即可<\\/li><\\/ul><\\/h5><h4><br><\\/h4><h4>三、插件使用<\\/h4><p><br><\\/p><h5><ul><li>打开浏览器，点击右上角按钮<\\/li><li>如需要同步接口管理系统接口，请点击【登录】，如无需同步接口，请点击【接口调试】<\\/li><\\/ul><\\/h5><h5><ul><li>登录后，点击右上角按钮选着需要测试的项目<\\/li><\\/ul><\\/h5><p><br><\\/p><h4>四、问题反馈<\\/h4><p><br><\\/p><ul><li>建议反馈请加QQ群：515305698<br><\\/li><li>在线提交问题：<a href=\\\"http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143\\\" target=\\\"_blank\\\">http://api.crap.cn/index.do#/article/detail?projectId=help&amp;moduleId=153908708739409000142&amp;type=ARTICLE&amp;id=153908718154501000143<\\/a><\\/li><li>直接在本文下留言<\\/li><\\/ul><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=\\\"http://api.crap.cn/resources/upload/images/2019-02/17161320efjm46.CAV.QxmnAq.1.jpeg\\\"><\\/p><p><a href=\\\"https://gitee.com/CrapApi/ApiDebug/blob/master/release/chrome/apiDebug.v1.0.4.crx\\\" target=\\\"_blank\\\"><\\/a><\\/p><p>*******&nbsp;<a href=\\\"http://api.crap.cn/\\\" target=\\\"_blank\\\">本文由CrapApi接口管理系统发布，转载请注明出处<\\/a>&nbsp;***********<\\/p>\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":26,\"month\":1,\"seconds\":10,\"time\":1550417170000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041717048101000025\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"ApiDebug http接口测试工具 -下载、安装、使用文档\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417074953,\"status\":1,\"type\":\"ARTICLE\",\"updateTime\":null}','155041717048101000025'),('155041752591008000030',1,'2019-02-16 17:32:06',1550417525910,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','JSON格式参数实现原理及录入','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":31,\"month\":1,\"seconds\":52,\"time\":1550417512000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"{\\n    \\\"isSuccess\\\":false\\n}\",\"fullUrl\":\"http://api.crap.cn/visitor/example/json.do\",\"header\":\"[{\\\"name\\\":\\\"Content-Type\\\",\\\"def\\\":\\\"application/json\\\",\\\"remark\\\":\\\"指定参数类型为json格式\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"155041751154012000029\",\"interfaceName\":\"JSON格式参数实现原理及录入\",\"isTemplate\":false,\"method\":\"POST\",\"moduleId\":\"\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"{\\n\\\"id\\\":\\\"8989-dddvdg\\\",\\n\\\"name\\\":\\\"文章标题-JSON格式参数演示\\\",\\n\\\"brief\\\":\\\"快速入门json参数\\\",\\n\\\"category\\\":\\\"分类\\\"\\n}\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<blockquote><h2>JSON参数<\\/h2><\\/blockquote><p><br><\\/p><p>在部分系统设计中，由于接口参数多或经常变动，因此会考虑使用json格式的参数。个人认为，有两种常用的方法可以实现：<\\/p><p>1. 直接把json数据当做一个参数，这种方法比较简单，json数据就是一个字符串而已（json中会有双引号等，需要转义后在传），但是后端接收到该参数后需要单独处理，将json数据转成对象<\\/p><p>2. 直接在请求body中出入json数据，后端直接通过springMVC自带的注解@RequestBody即可将json数据转为对象，微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互<\\/p><p>注意：必须添加http头信息【Content-Type=application/json】，指定body类型为json<br><\\/p><p>服务端代码如下：<\\/p><pre>@RequestMapping(<b>\\\"/front/example/json.do\\\"<\\/b>)<br>@ResponseBody<br><b>public <\\/b>JsonResult json(@RequestBody Article article) <b>throws <\\/b>MyException{<br>   <b>return new <\\/b>JsonResult(1, article);<br>}<\\/pre><p><br><\\/p><p>可点击【<a href=\\\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/895aaabc-f3d7-461c-bad6-e9c44f6cdb4f\\\" target=\\\"\\\">调试<\\/a>】或页面右上角【调试】按钮进行调试<br><\\/p><p>借助本系统，可以将通过点击【自定义参数】，输入json格式参数即可，由于json格式的参数复杂，系统支持添加json参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\\\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\\\" target=\\\"\\\">CrapApi视频介绍-用户版<\\/a>】<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[]\",\"sequence\":1550417442881,\"status\":1,\"trueExam\":\"{\\n    \\\"page\\\":null,\\n    \\\"success\\\":1,\\n    \\\"data\\\":{\\n        \\\"id\\\":\\\"8989-dddvdg\\\",\\n        \\\"name\\\":\\\"文章标题-JSON格式参数演示\\\",\\n        \\\"brief\\\":\\\"快速入门json参数\\\",\\n        \\\"click\\\":null,\\n        \\\"type\\\":null,\\n        \\\"status\\\":null,\\n        \\\"createTime\\\":null,\\n        \\\"moduleId\\\":null,\\n        \\\"mkey\\\":null,\\n        \\\"canDelete\\\":null,\\n        \\\"category\\\":\\\"分类\\\",\\n        \\\"canComment\\\":null,\\n        \\\"commentCount\\\":null,\\n        \\\"sequence\\\":null,\\n        \\\"projectId\\\":null\\n    },\\n    \\\"error\\\":null,\\n    \\\"others\\\":null\\n}\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":31,\"month\":1,\"seconds\":52,\"time\":1550417512000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/json.do\",\"version\":\"1.0.0\"}','155041751154012000029'),('155041765847608000032',1,'2019-02-16 17:34:18',1550417658476,'ArticleWithBLOBs','项目数据库表','UPDATE','超级管理员','通过interface','{\"attributes\":\"\",\"brief\":\"\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":0,\"commentCount\":0,\"content\":\"[{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"primary\\\",\\\"name\\\":\\\"id\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"主键\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorText\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控比较内容\\\",\\\"type\\\":\\\"varchar(500)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"falseExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"requestExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"paramRemark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求参数备注\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"remark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控类型，多选：\\\\\\\\nNetwork(\\\\\\\"网络异常\\\\\\\",1)\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"param\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"参数列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateBy\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"responseParam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"返回参数说明\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorEmails\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"interfaceName\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口名\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"moduleId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"所属模块ID\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"application/json\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"contentType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口返回contentType\\\",\\\"type\\\":\\\"varchar(45)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"method\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errorList\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"接口错误码列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"fullUrl\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(255)\\\"},{\\\"def\\\":\\\"2015-12-31\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"trueExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"正确返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1.0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"version\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"版本号\\\",\\\"type\\\":\\\"varchar(20)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"url\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"api链接\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"sequence\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"排序，越大越靠前\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"CURRENT_TIMESTAMP\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"createTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"b0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"isTemplate\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否是模板\\\",\\\"type\\\":\\\"bit(1)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"header\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"projectId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errors\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误码、错误码信息\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"status\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否可用;0不可用；1可用;-1\\\",\\\"type\\\":\\\"tinyint(4)\\\"}]\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":33,\"month\":1,\"seconds\":58,\"time\":1550417638000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041763777601000031\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"interface\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417637776,\"status\":1,\"type\":\"DICTIONARY\",\"updateTime\":null}','155041763777601000031'),('155041769052208000033',1,'2019-02-16 17:34:51',1550417690522,'ArticleWithBLOBs','项目数据库表','UPDATE','超级管理员','通过interface','{\"attributes\":\"\",\"brief\":\"\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":0,\"commentCount\":0,\"content\":\"[{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"primary\\\",\\\"name\\\":\\\"id\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"主键\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorText\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控比较内容\\\",\\\"type\\\":\\\"varchar(500)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"falseExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"requestExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"paramRemark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求参数备注\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"remark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控类型，多选：\\\\\\\\nNetwork(\\\\\\\"网络异常\\\\\\\",1)\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"param\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"参数列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateBy\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"responseParam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"返回参数说明\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorEmails\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"interfaceName\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口名\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"moduleId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"所属模块ID\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"application/json\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"contentType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口返回contentType\\\",\\\"type\\\":\\\"varchar(45)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"method\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errorList\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"接口错误码列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"fullUrl\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(255)\\\"},{\\\"def\\\":\\\"2015-12-31\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"trueExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"正确返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1.0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"version\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"版本号\\\",\\\"type\\\":\\\"varchar(20)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"url\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"api链接\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"sequence\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"排序，越大越靠前\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"CURRENT_TIMESTAMP\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"createTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"b0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"isTemplate\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否是模板\\\",\\\"type\\\":\\\"bit(1)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"header\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"projectId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errors\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误码、错误码信息\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"status\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否可用;0不可用；1可用;-1\\\",\\\"type\\\":\\\"tinyint(4)\\\"}]\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":33,\"month\":1,\"seconds\":58,\"time\":1550417638000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041763777601000031\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"interface\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417637776,\"status\":1,\"type\":\"DICTIONARY\",\"updateTime\":null}','155041763777601000031'),('155041770366308000034',1,'2019-02-16 17:35:04',1550417703663,'ArticleWithBLOBs','项目数据库表','UPDATE','超级管理员','通过interface','{\"attributes\":\"\",\"brief\":\"\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":0,\"commentCount\":0,\"content\":\"[{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"primary\\\",\\\"name\\\":\\\"id\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"主键\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorText\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控比较内容\\\",\\\"type\\\":\\\"varchar(500)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"falseExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"requestExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"paramRemark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求参数备注\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"remark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控类型，多选：\\\\\\\\nNetwork(\\\\\\\"网络异常\\\\\\\",1)\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"param\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"参数列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateBy\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"responseParam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"返回参数说明\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorEmails\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"interfaceName\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口名\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"moduleId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"所属模块ID\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"application/json\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"contentType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口返回contentType\\\",\\\"type\\\":\\\"varchar(45)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"method\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errorList\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"接口错误码列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"fullUrl\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(255)\\\"},{\\\"def\\\":\\\"2015-12-31\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"trueExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"正确返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1.0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"version\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"版本号\\\",\\\"type\\\":\\\"varchar(20)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"url\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"api链接\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"sequence\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"排序，越大越靠前\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"CURRENT_TIMESTAMP\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"createTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"b0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"isTemplate\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否是模板\\\",\\\"type\\\":\\\"bit(1)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"header\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"projectId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errors\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误码、错误码信息\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"status\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否可用;0不可用；1可用;-1\\\",\\\"type\\\":\\\"tinyint(4)\\\"}]\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":33,\"month\":1,\"seconds\":58,\"time\":1550417638000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041763777601000031\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"interface\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417637776,\"status\":1,\"type\":\"DICTIONARY\",\"updateTime\":null}','155041763777601000031'),('155041776295308000035',1,'2019-02-16 17:36:03',1550417762953,'ArticleWithBLOBs','项目数据库表','UPDATE','超级管理员','通过建表语句导入-interface','{\"attributes\":\"\",\"brief\":\"\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":0,\"commentCount\":0,\"content\":\"[{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"primary\\\",\\\"name\\\":\\\"id\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"主键\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorText\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控比较内容\\\",\\\"type\\\":\\\"varchar(500)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"falseExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"requestExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"paramRemark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"请求参数备注\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"remark\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"监控类型，多选：\\\\\\\\nNetwork(\\\\\\\"网络异常\\\\\\\",1)\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"param\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"参数列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateBy\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"responseParam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"返回参数说明\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"NULL\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"monitorEmails\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"interfaceName\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口名\\\",\\\"type\\\":\\\"varchar(100)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"moduleId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"所属模块ID\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"application/json\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"contentType\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"接口返回contentType\\\",\\\"type\\\":\\\"varchar(45)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"method\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errorList\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"接口错误码列表\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"fullUrl\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(255)\\\"},{\\\"def\\\":\\\"2015-12-31\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"updateTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"trueExam\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"正确返回示例\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1.0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"version\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"版本号\\\",\\\"type\\\":\\\"varchar(20)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"url\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"api链接\\\",\\\"type\\\":\\\"varchar(200)\\\"},{\\\"def\\\":\\\"0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"sequence\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"排序，越大越靠前\\\",\\\"type\\\":\\\"int(11)\\\"},{\\\"def\\\":\\\"CURRENT_TIMESTAMP\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"createTime\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"timestamp\\\"},{\\\"def\\\":\\\"b0\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"isTemplate\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否是模板\\\",\\\"type\\\":\\\"bit(1)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"header\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"projectId\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"varchar(50)\\\"},{\\\"def\\\":\\\"\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"errors\\\",\\\"notNull\\\":\\\"true\\\",\\\"remark\\\":\\\"错误码、错误码信息\\\",\\\"type\\\":\\\"text\\\"},{\\\"def\\\":\\\"1\\\",\\\"flag\\\":\\\"common\\\",\\\"name\\\":\\\"status\\\",\\\"notNull\\\":\\\"false\\\",\\\"remark\\\":\\\"是否可用;0不可用；1可用;-1\\\",\\\"type\\\":\\\"tinyint(4)\\\"}]\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":33,\"month\":1,\"seconds\":58,\"time\":1550417638000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041763777601000031\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"interface\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417637776,\"status\":1,\"type\":\"DICTIONARY\",\"updateTime\":null}','155041763777601000031'),('155041784599408000036',1,'2019-02-16 17:37:26',1550417845995,'ArticleWithBLOBs','项目数据库表','UPDATE','超级管理员','通过建表语句导入-interface','{\"attributes\":\"\",\"brief\":\"点击标题进入详情，可以自动生成javaPO、mybatis xml配置代码哦~\",\"canComment\":1,\"canDelete\":1,\"category\":\"\",\"click\":0,\"commentCount\":0,\"content\":\"[{\\\"name\\\":\\\"id\\\",\\\"type\\\":\\\"varchar(50)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"主键\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"primary\\\"},{\\\"name\\\":\\\"monitorText\\\",\\\"type\\\":\\\"varchar(500)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"监控比较内容\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"falseExam\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"错误返回示例\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"requestExam\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"请求示例\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"paramRemark\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"请求参数备注\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"remark\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"monitorType\\\",\\\"type\\\":\\\"int(11)\\\",\\\"def\\\":\\\"0\\\",\\\"remark\\\":\\\"监控类型，多选：\\\\nNetwork(\\\\\\\"网络异常\\\\\\\",1)\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"param\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"参数列表\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"updateBy\\\",\\\"type\\\":\\\"varchar(100)\\\",\\\"def\\\":\\\"NULL\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"responseParam\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"返回参数说明\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"monitorEmails\\\",\\\"type\\\":\\\"varchar(200)\\\",\\\"def\\\":\\\"NULL\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"interfaceName\\\",\\\"type\\\":\\\"varchar(100)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"接口名\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"moduleId\\\",\\\"type\\\":\\\"varchar(50)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"所属模块ID\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"contentType\\\",\\\"type\\\":\\\"varchar(45)\\\",\\\"def\\\":\\\"application/json\\\",\\\"remark\\\":\\\"接口返回contentType\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"method\\\",\\\"type\\\":\\\"varchar(50)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"errorList\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"接口错误码列表\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"fullUrl\\\",\\\"type\\\":\\\"varchar(255)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"updateTime\\\",\\\"type\\\":\\\"timestamp\\\",\\\"def\\\":\\\"2015-12-31\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"trueExam\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"正确返回示例\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"version\\\",\\\"type\\\":\\\"varchar(20)\\\",\\\"def\\\":\\\"1.0\\\",\\\"remark\\\":\\\"版本号\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"url\\\",\\\"type\\\":\\\"varchar(200)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"api链接\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"sequence\\\",\\\"type\\\":\\\"int(11)\\\",\\\"def\\\":\\\"0\\\",\\\"remark\\\":\\\"排序，越大越靠前\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"createTime\\\",\\\"type\\\":\\\"timestamp\\\",\\\"def\\\":\\\"CURRENT_TIMESTAMP\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"isTemplate\\\",\\\"type\\\":\\\"bit(1)\\\",\\\"def\\\":\\\"b0\\\",\\\"remark\\\":\\\"是否是模板\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"header\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"projectId\\\",\\\"type\\\":\\\"varchar(50)\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"errors\\\",\\\"type\\\":\\\"text\\\",\\\"def\\\":\\\"\\\",\\\"remark\\\":\\\"错误码、错误码信息\\\",\\\"notNull\\\":\\\"true\\\",\\\"flag\\\":\\\"common\\\"},{\\\"name\\\":\\\"status\\\",\\\"type\\\":\\\"tinyint(4)\\\",\\\"def\\\":\\\"1\\\",\\\"remark\\\":\\\"是否可用;0不可用；1可用;-1\\\",\\\"notNull\\\":\\\"false\\\",\\\"flag\\\":\\\"common\\\"}]\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":33,\"month\":1,\"seconds\":58,\"time\":1550417638000,\"timezoneOffset\":-480,\"year\":119},\"id\":\"155041763777601000031\",\"markdown\":\"\",\"mkey\":\"\",\"moduleId\":\"155041592457909000006\",\"name\":\"通过建表语句导入-interface\",\"projectId\":\"155041466418307000002\",\"sequence\":1550417637776,\"status\":1,\"type\":\"DICTIONARY\",\"updateTime\":null}','155041763777601000031'),('155041796937208000038',1,'2019-02-16 17:39:29',1550417969372,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','JSON格式参数实现原理及录入','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":31,\"month\":1,\"seconds\":52,\"time\":1550417512000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"{\\n    \\\"isSuccess\\\":false\\n}\",\"fullUrl\":\"http://api.crap.cn/visitor/example/json.do\",\"header\":\"[{\\\"name\\\":\\\"Content-Type\\\",\\\"def\\\":\\\"application/json\\\",\\\"remark\\\":\\\"指定参数类型为json格式\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"155041751154012000029\",\"interfaceName\":\"JSON格式参数实现原理及录入\",\"isTemplate\":false,\"method\":\"POST\",\"moduleId\":\"\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"{\\n\\\"id\\\":\\\"8989-dddvdg\\\",\\n\\\"name\\\":\\\"文章标题-JSON格式参数演示\\\",\\n\\\"brief\\\":\\\"快速入门json参数\\\",\\n\\\"category\\\":\\\"分类\\\"\\n}\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<blockquote><h2>JSON参数<\\/h2><\\/blockquote><p><br><\\/p><p>在部分系统设计中，由于接口参数多或经常变动，因此会考虑使用json格式的参数。个人认为，有两种常用的方法可以实现：<\\/p><p>1. 直接把json数据当做一个参数，这种方法比较简单，json数据就是一个字符串而已（json中会有双引号等，需要转义后在传），但是后端接收到该参数后需要单独处理，将json数据转成对象<\\/p><p>2. 直接在请求body中出入json数据，后端直接通过springMVC自带的注解@RequestBody即可将json数据转为对象，微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互<\\/p><p>注意：必须添加http头信息【Content-Type=application/json】，指定body类型为json<br><\\/p><p>服务端代码如下：<\\/p><pre>@RequestMapping(<b>\\\"/front/example/json.do\\\"<\\/b>)<br>@ResponseBody<br><b>public <\\/b>JsonResult json(@RequestBody Article article) <b>throws <\\/b>MyException{<br>   <b>return new <\\/b>JsonResult(1, article);<br>}<\\/pre><p><br><\\/p><p>可点击【<a href=\\\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/895aaabc-f3d7-461c-bad6-e9c44f6cdb4f\\\" target=\\\"\\\">调试<\\/a>】或页面右上角【调试】按钮进行调试<br><\\/p><p>借助本系统，可以将通过点击【自定义参数】，输入json格式参数即可，由于json格式的参数复杂，系统支持添加json参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\\\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\\\" target=\\\"\\\">CrapApi视频介绍-用户版<\\/a>】<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[{\\\"name\\\":\\\"page\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"success\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"number\\\"},{\\\"name\\\":\\\"data\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"object\\\"},{\\\"name\\\":\\\"data->id\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->name\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->brief\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->click\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->type\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->status\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->createTime\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->moduleId\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->mkey\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->canDelete\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->category\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->canComment\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->commentCount\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->sequence\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"data->projectId\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"error\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"others\\\",\\\"remark\\\":\\\"\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"sequence\":1550417442881,\"status\":1,\"trueExam\":\"{\\n    \\\"page\\\":null,\\n    \\\"success\\\":1,\\n    \\\"data\\\":{\\n        \\\"id\\\":\\\"8989-dddvdg\\\",\\n        \\\"name\\\":\\\"文章标题-JSON格式参数演示\\\",\\n        \\\"brief\\\":\\\"快速入门json参数\\\",\\n        \\\"click\\\":null,\\n        \\\"type\\\":null,\\n        \\\"status\\\":null,\\n        \\\"createTime\\\":null,\\n        \\\"moduleId\\\":null,\\n        \\\"mkey\\\":null,\\n        \\\"canDelete\\\":null,\\n        \\\"category\\\":\\\"分类\\\",\\n        \\\"canComment\\\":null,\\n        \\\"commentCount\\\":null,\\n        \\\"sequence\\\":null,\\n        \\\"projectId\\\":null\\n    },\\n    \\\"error\\\":null,\\n    \\\"others\\\":null\\n}\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":31,\"month\":1,\"seconds\":52,\"time\":1550417512000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/json.do\",\"version\":\"1.0.0\"}','155041751154012000029'),('155041797836208000039',1,'2019-02-16 17:39:38',1550417978362,'InterfaceWithBLOBs','接口','UPDATE','超级管理员','XML格式参数实现原理及录入','{\"attributes\":\"\",\"contentType\":\"application/json\",\"createTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"errorList\":\"\",\"errors\":\"[]\",\"falseExam\":\"{\\n    \\\"isSuccess\\\":false\\n}\",\"fullUrl\":\"http://api.crap.cn/visitor/example/xml.do\",\"header\":\"[{\\\"name\\\":\\\"Content-Type\\\",\\\"def\\\":\\\"application/xml\\\",\\\"remark\\\":\\\"指定参数类型为xml格式\\\",\\\"necessary\\\":\\\"true\\\",\\\"type\\\":\\\"string\\\"}]\",\"id\":\"155041672533112000021\",\"interfaceName\":\"XML格式参数实现原理及录入\",\"isTemplate\":false,\"method\":\"POST\",\"moduleId\":\"155041592457909000006\",\"monitorEmails\":\"\",\"monitorText\":\"\",\"monitorType\":0,\"param\":\"<users>\\n<name>用户名<\\/name>\\n<value>value值<\\/value>\\n<\\/users>\",\"paramRemark\":\"[]\",\"projectId\":\"155041466418307000002\",\"remark\":\"<blockquote><h2>XML参数<\\/h2><\\/blockquote><p><br><\\/p><p>XML参数通JSON参数实现原理一样<\\/p><p>1. 直接把xml数据当做一个参数，这种方法比较简单，xml数据就是一个字符串而已（xml包含特色符号，需要转义后在传），但是后端接收到该参数后需要单独处理，将xml数据转成对象<br><\\/p><p>2. 直接在请求body中出入xml数据，后端直接通过springMVC自带的注解@RequestBody即可将xml数据转为对象（对象上需要添加注解@XmlRootElement），微信公众号开发接口调用就是通过请求body，通过xml格式的参数与服务器交互<\\/p><p>注意：必须添加http头信息【Content-Type=application/xml】，指定body类型为xml<br><\\/p><p>服务端代码如下：<\\/p><pre>@RequestMapping(<b>\\\"/front/example/xml.do\\\"<\\/b>)<br>@ResponseBody<br><b>public <\\/b>JsonResult xml(@RequestBody XmlParamsDto users) <b>throws <\\/b>MyException{<br>   <b>return new <\\/b>JsonResult(1, users);<br>}<\\/pre><pre>@XmlRootElement(name=<b>\\\"users\\\"<\\/b>)<br><b>public class <\\/b>XmlParamsDto {<br>   <b>private <\\/b>String <b>name<\\/b>;<br>   <b>private <\\/b>String <b>value<\\/b>;<br>   <b>public <\\/b>String getName() {<br>      <b>return <\\/b><b>name<\\/b>;<br>   }<br>   <b>public void <\\/b>setName(String name) {<br>      <b>this<\\/b>.<b>name <\\/b>= name;<br>   }<br>   <b>public <\\/b>String getValue() {<br>      <b>return <\\/b><b>value<\\/b>;<br>   }<br>   <b>public void <\\/b>setValue(String value) {<br>      <b>this<\\/b>.<b>value <\\/b>= value;<br>   }<br>   <br>}<\\/pre><p><br><\\/p><p>可点击【<a href=\\\"http://api.crap.cn/index.do#/de2055f4-656a-495b-85dd-6591922bdf5d/front/interface/debug/152575818145912000262\\\" target=\\\"\\\">调试<\\/a>】或页面右上角【调试】按钮进行调试<br><\\/p><p>借助本系统，可以将通过点击【自定义参数】，输入xml格式参数即可，由于xml格式的参数复杂，系统支持添加xml参数备注，便于其他协同人员快速了解接口信息，详情可观看：【<a href=\\\"http://v.youku.com/v_show/id_XMzU4NjQwODIzNg==.html\\\" target=\\\"\\\">CrapApi视频介绍-用户版<\\/a>】<\\/p>\",\"requestExam\":\"\",\"responseParam\":\"[]\",\"sequence\":1550416661238,\"status\":1,\"trueExam\":\"{\\n    \\\"page\\\":null,\\n    \\\"success\\\":1,\\n    \\\"data\\\":{\\n        \\\"name\\\":\\\"用户名\\\",\\n        \\\"value\\\":\\\"value值\\\"\\n    },\\n    \\\"error\\\":null,\\n    \\\"others\\\":null\\n}\",\"updateBy\":\"userName：admin | trueName：超级管理员\",\"updateTime\":{\"date\":17,\"day\":0,\"hours\":23,\"minutes\":18,\"month\":1,\"seconds\":45,\"time\":1550416725000,\"timezoneOffset\":-480,\"year\":119},\"url\":\"http://api.crap.cn/visitor/example/xml.do\",\"version\":\"1.0\"}','155041672533112000021');
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`),
  KEY `index_parentId` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('153948782568805000003','帮助文档',NULL,NULL,'0','','TOP','2018-10-13 19:30:26',1,4),('153950905406605000001','系统完全免费、完全开源',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 01:24:14',1,2),('153951065165405000001','开源chrome插件，支持跨域、本地、在线接口调试',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 01:50:52',1,6),('153951121365505000002','私有项目、加密项目、MD5加盐，全方面保护数据安全',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 02:00:14',1,7),('153951128120905000003','数据库表、markdown、restful、mock、pdf、word',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 02:01:21',1,8),('153951143816405000005','团队协作、权限控制、修改日志',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 02:03:58',1,10),('153951206304005000006','阿里云安全的云端存储，定时备份数据，支持本地部署',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 02:14:23',1,11),('153951230601105000007','不断迭代、更多好用功能在路上...',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2018-10-14 02:18:26',1,1),('155041388332805000001','简单高效的BUG管理系统，记录每一次变动',NULL,NULL,'0','<i class=\"iconfont\"></i>','FUNCTION','2019-02-16 16:31:23',1,1550413883328);
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
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
INSERT INTO `module` VALUES ('155041590489909000005','迭代2019-02-18','2019-02-16 17:05:05',1,1550415839365,'',1,'','admin','155041466418307000002',NULL,0,'默认分类'),('155041592457909000006','订单接口','2019-02-16 17:05:25',1,1550415915345,'',1,'','admin','155041466418307000002',NULL,0,'默认分类');
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
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
INSERT INTO `project` VALUES ('155041466418307000002','欢迎使用 CRAP管理系统','2019-02-16 16:44:24',1,1550414007005,'','admin',1,NULL,'resources/avatar/avatar5.jpg',0);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_meta`
--

DROP TABLE IF EXISTS `project_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_meta` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `projectId` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '项目ID',
  `moduleId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模块ID',
  `attributes` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '属性，使用;:分割',
  `sequence` bigint(20) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态，1:正常',
  `type` tinyint(4) NOT NULL COMMENT '数据类型，0:环境',
  `name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `value` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`),
  KEY `idx_project_type` (`projectId`,`type`),
  KEY `idx_project_module_type` (`projectId`,`moduleId`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_meta`
--

LOCK TABLES `project_meta` WRITE;
/*!40000 ALTER TABLE `project_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_meta` ENABLE KEYS */;
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `projectId` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `userEmail` varchar(45) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `permission` varchar(500) DEFAULT ',' COMMENT '权限',
  PRIMARY KEY (`id`),
  KEY `index_uid_seq_time` (`userId`,`sequence`,`createTime`),
  KEY `idx_project_seq` (`projectId`,`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` VALUES ('155041480595210000004',1,1550414805952,'2019-02-17 06:46:45','155041466418307000002','155041470423906000003','testUser@crap.cn','testUser',',modInter,addInter,modModule,addModule,modError,addError,modArticle,addArticle,modDict,addDict,modBug,addBug,delBug,modSource,addSource,');
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
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
  `remark` varchar(500) DEFAULT '',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `type` varchar(10) NOT NULL DEFAULT 'TEXT' COMMENT '设置类型（IMAGE,LINK,TEXT）',
  `canDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1：可删除，0：不可删除',
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
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
INSERT INTO `setting` VALUES ('062f01ae-e50b-4dd3-808b-b4a6d65eeadc','LOGO','resources/images/logo_new.png','网站主logo，可以直接在value中填写绝对链接地址，也可以自行上传图片\nmr50 ml50','2016-03-28 20:23:18',1,'IMAGE',0,90,1),('152974757483102000001','MINI_LOGO','resources/images/logo.png','网站小logo','2018-06-23 01:52:55',1,'IMAGE',0,101,1),('153035340149802000001','MAX_MODULE','100','项目下允许创建的最大模块数','2018-06-30 02:10:01',100,'TEXT',0,101,1),('153035340151002000002','MAX_PROJECT','1000','最大允许创建的项目数','2018-06-30 02:10:02',100,'TEXT',0,101,1),('153037721900502000001','IMAGE_CODE','Tekton Pro','图形验证码风格（部分系统显示有问题，可切换风格）','2018-06-30 08:46:59',100,'SELECT',0,101,1),('153043918138702000001','MAX_ERROR','1000','项目下最大允许的错误码数量','2018-07-01 01:59:41',100,'TEXT',0,101,1),('153404928057302000001','NAV_BG_COLOR','#233050','网站顶部、底部导航背景颜色','2018-08-11 20:48:01',1,'COLOR',0,100,1),('153404928058302000002','NAV_COLOR','#a9a9a9','前端顶部、导航字体颜色 #a9a9a9','2018-08-11 20:48:01',1,'COLOR',0,100,1),('154005080763302000001','NO_NEED_LOGIN_USER','155041470423906000003','不需要登陆直接模拟的用户，快速试用，0表示不开放试用功能','2018-10-20 07:53:28',100,'TEXT',0,101,1),('154071214301202000001','OPEN_ALIYUN','false','是否开启阿里云图片存储，开通后图片、文件将通过云端读写','2018-10-27 23:35:43',100,'TEXT',0,101,1),('154244284899002000001','DATABASE_CHANGE_LOG','70','数据库自动更新记录：记录最后一条sql的序号，更新版本后系统会自动修改，请勿修改','2018-11-17 00:20:49',100,'TEXT',0,0,1),('157207911033402000001','DOMAIN','http://127.0.0.1:8080','系统访问地址/域名/IP','2019-10-26 08:38:30',1,'TEXT',0,1572079110334,1),('8a95bc2f-ea61-4dd6-8163-d9c520b28181','VISITCODE','false','游客访问私密模块输入密码的同时是否需要输入图像验证码？true为需要，其他为不需要','2016-03-31 02:07:14',0,'TEXT',0,90,1),('98ecca1b-f762-4cd3-831a-4042b36419d8','VERIFICATIONCODE','false','是否开启安全登录？ture为开启，其他为不开启，开启后登录将需要输入图片验证码','2016-03-31 02:07:58',0,'TEXT',0,90,1),('b97a3a75-c1c3-42cc-b944-8fb5ac5c5f49','SECRETKEY','crapApiKey','秘钥，用于cookie加密等','2016-03-30 09:04:40',100,'TEXT',0,90,1),('de94c622-02fc-4b39-9cc5-0c24870ac21f','TITLE','CrapApi|Api接口管理系统','站点标题','2016-03-30 03:09:13',1,'TEXT',0,90,1),('e0dec957-5043-4c6e-9225-960fbc401116','ICON','http://api2.crap.cn/resources/upload/images/2016-04-04/063633hG35aC.ico','站点ICON图标（浏览器标题栏图标）','2016-03-30 03:49:41',1,'IMAGE',0,100,1),('e2a493a7-c4f0-4cbb-832f-4495a7074252','LOGINBG','resources/images/transparent.png','登陆背景图\n默认图片：resources/images/bg_web.jpg\n透明：resources/images/transparent.png','2016-08-24 16:02:28',1,'IMAGE',0,100,1),('e2a493a7-c888-4cbb-832f-4495a7074252','TITLEBG','resources/images/transparent.png','头部标题搜索背景图\n默认图片：resources/images/bg_web.jpg\n透明：resources/images/transparent.png','2016-08-24 16:02:28',1,'IMAGE',0,99,1),('ecd676c2-0b04-4b4a-a049-4a825221a6d0','BG_COLOR','#f7f7f7','前端显示背景颜色','2016-04-27 21:07:37',1,'COLOR',0,98,1),('ef157b7f-cc53-4a41-9679-d243d478023d','COMMENTCODE','true','游客评论是否需要输入验证码','2016-04-14 06:47:29',1,'TEXT',0,90,1),('f1c8dc8b-9cd8-4839-b38a-1cea3ceb3942','MAX_WIDTH','1000','前端显示最大宽度（数字，建议：900-1200）','2016-04-27 21:07:37',1,'TEXT',0,90,1),('fff-1111-d4839-b38a-898343435462','ANONYMOUS_COMMENT','false','是否允许匿名评论, true:允许','2017-08-05 23:55:00',1,'TEXT',0,100,1),('fff-8888-d4839-b38a-898343435462','ICONFONT','iconfont','图标地址（cdn图标库或本地图标库）','2018-05-01 04:27:00',1,'SELECT',0,100,1),('fff-9191-d4839-b38a-898343435462','DESCRIPTION','免费开源的API接口管理系统、文档管理系统。系统特点：极致简单、开源开放、技术前沿。主要功能有：接口管理、接口调试、文档管理、数据字典管理、文章管理....。','搜索引描述','2018-05-04 06:53:11',1,'TEXT',0,100,1),('fff-9999-d4839-b38a-898343435462','KEYWORDS','CrapApi,api,crap,接口管理,应用接口管理,开源接口管理,开源api接口管理,api接口管理','搜索引擎关键字','2018-05-04 06:53:11',1,'TEXT',0,100,1),('foc8dc8b-9cd8-4839-b38a-1cea3ceb3942','FOOTER_BG_COLOR','#233050','前端顶部、底部颜色 #383942','2016-04-27 21:07:37',-1,'COLOR',0,91,1),('fpc8dc8b-9cd8-4839-b38a-1cea3ceb3942','FOOTER_COLOR','#a9a9a9','前端顶部、底部字体颜色 #a9a9a9','2016-04-27 21:07:37',-1,'COLOR',0,97,1),('fpmbdc00-9cd8-4839-b38a-1cea3ceb3945','LUCENE_DIR','/usr/local/crap/lucene','Lucene全文检索文件存储地址','2016-06-05 21:07:37',1,'TEXT',0,22,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3942','MAIN_COLOR','#4CAF56','网站主色调，默认#CC6699 #007742 6f5499 4285f4 946fca 4285f4 0F95E6','2016-04-27 21:07:37',1,'COLOR',0,100,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3945','FONT_FAMILY','\"Lantinghei SC\", \"Open Sans\", Arial, \"Hiragino Sans GB\", \"Microsoft YaHei\", \"STHeiti\", \"WenQuanYi Micro Hei\", SimSun, sans-serif;','网站字体','2016-06-05 21:07:37',1,'SEL_IN',0,77,1),('fpmbdc8b-9cd8-4839-b38a-1cea3ceb3999','ADORN_COLOR','#f82c1d','装饰背景颜色:左侧导航条背景颜色 #1c2034','2016-06-05 21:07:37',1,'COLOR',0,26,1),('fpmbdc8b-9cd8-4839-b38a-898343435462','INDEX_PAGE','dashboard.htm','前端首页：只能以index.do、font/ 开头的url','2017-06-11 01:57:39',1,'INDEXPAGE',0,66,1);
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `name` varchar(100) NOT NULL COMMENT '资源名称',
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-30 16:00:00',
  `moduleId` varchar(50) DEFAULT '0' COMMENT '模块ID',
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
  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `type` tinyint(4) NOT NULL DEFAULT '100' COMMENT '用户类型：1普通用户，100：管理员',
  `email` varchar(45) DEFAULT NULL,
  `avatarUrl` varchar(500) NOT NULL DEFAULT '' COMMENT '用户头像',
  `loginType` int(11) NOT NULL DEFAULT '0' COMMENT '0：账号登陆，1：github登陆，2：码云',
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
INSERT INTO `user` VALUES ('155041470423906000003','testUser','4be53ec042adef08cdeeac6b971e9cb0','测试用户','','','','','2019-02-16 16:45:04',1,1550414704239,1,'testUser@crap.cn','resources/avatar/avatar4.jpg',0,NULL,'37BCNeGggtIiaksB6w4k'),('admin','admin','5c5ab77d908c2a3be8674ac6f5c48d50','超级管理员','super,','超级管理员,',',SUPER,,,','','2016-03-28 20:24:00',1,0,100,'ihsantang@163.com','',0,'','stnci97PqJeu4P18pqyn');
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

-- Dump completed on 2019-10-26 16:52:12
