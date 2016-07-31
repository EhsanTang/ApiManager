-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: rdser3mvttgp20ftyozwc.mysql.rds.aliyuncs.com    Database: api
-- ------------------------------------------------------
-- Server version	5.6.16-log

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='9746bafa-1a4d-11e5-b90a-d89d672a9388:1-1523274,
9ce0b44b-1a4d-11e5-b90a-1051721c3b2a:1-1524332';

--
-- Table structure for table `error`
--

DROP TABLE IF EXISTS `error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error` (
  `errorId` varchar(50) NOT NULL COMMENT '主键',
  `errorCode` varchar(50) NOT NULL COMMENT '错误码编码',
  `errorMsg` varchar(128) NOT NULL COMMENT '错误码描述',
  `moduleId` varchar(50) NOT NULL,
  `moduleName` varchar(100) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`errorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error`
--

LOCK TABLES `error` WRITE;
/*!40000 ALTER TABLE `error` DISABLE KEYS */;
INSERT INTO `error` VALUES ('0008a40d-b79c-4579-a9f9-f0a04dba1403','205013','获取收获地址出错','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('00743df0-a358-4dd1-8856-dbdeb1f78633','281007','红包不存在','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('00932ac3-1b4d-4b27-8a54-46ae2c907ab5','210001','确认收货已超过15天，不能发起售后','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('0173ac20-bb4d-4704-8a85-1077dc9b84d0','000007','活动尚未开始，申请失败！','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('0185f80b-a467-49c3-bd96-27c6797be0f4','220001','F码无效，请核对后重新输入','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('02107ca6-f855-490c-85f2-94e1cbdf17a3','208109','验证码错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('04497cf7-2f10-41a5-9519-eb058a376196','200004','Unsupported http request Method','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('0500dcf9-4548-4dac-b769-2d2c3585cdbd','000000','Correct!','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('0a5f7843-cbc1-4ca6-85ae-b47bf1d4bc4d','208121','不是有效的手机号','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('0a826872-7e77-4ed5-afc2-c1ddf6b44272','240007','体验码已作废','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('0b4e412f-e09f-4bd8-8738-be79aa089f74','205014','保存订单异常','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('17a69314-a708-45de-88d4-2ee6b345860a','212008','订单不属于指定的商家','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('18d18226-43eb-47d6-98e3-b773dd32ed8b','220002','F码已失效','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('193625e6-a881-4152-b659-fc08193b8ffa','212002','订单不存在。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('1a040af7-8474-4fe1-8703-8594ad69a44e','208123','输入的手机号为空','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('1de575fb-706b-4b9f-95c8-f6c77865e6dd','210002','所有商品都已退货，不能再发起退货','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('1e01a3dd-1711-44ab-8481-3f15c57cdcde','000012','抱歉，您所选择的试用商品已被抢完，去看看其他活动吧~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('1e85c7e3-9ee9-4884-8c4f-061a2ad0e8ad','205004','当前用户操作不合法','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('1f60abf1-698a-4612-acde-021358376175','205006','商家未设置包装信息','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('1f6745e4-3a11-4a7a-8080-4c8d5d457c10','208126','获取用户信息失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('2069622d-579e-460e-89c0-dcfaa924fc82','281014','趣分期兑换uid为空','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('249f00da-9465-41e0-97b9-18ac6e9075f6','208106','输入的密码和原密码相同','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('26622971-152d-4b31-9ba3-0710ce8e2850','205007','删除指定商品失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('275be2d3-c3a3-4a0e-8abc-9045033913cc','200005','Unsupported http request Method','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('2d12aef3-99f4-4d21-bf78-25eb0b2ced82','205003','商品添加至购物车失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('2ece77ba-acdd-4abe-a159-c7ed2038fe83','281012','趣分期兑换码已使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('303e8413-6cfd-4c42-999f-103cbc14d038','205015','未找到待支付的订单','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('3154701e-7edc-42b7-b8ce-79a96b2d84cf','211001','提现税额计算错误。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('3380cef1-432e-4d18-a556-c4b4abd7c279','281013','趣分期兑换码无效','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('37af2829-4d38-42ba-bb95-964d5b3aff47','210003','此商品有正在处理中未完成的退货申请','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('39bdddf8-f491-42e1-8c84-3f55ded2a43c','281005','红包使用中','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('3c692a35-19c3-48d4-b2c7-e854208963c2','000002','授权验证失败','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('416a5375-9dc7-46d6-b8cc-6af4d28ead39','281010','趣分期兑换码长度错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('4521bab8-65f0-4e74-9029-e606cc3b3097','210010','使用红包的商品，退款金额与最大可退金额不匹配','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('47f19229-a789-418e-b499-a32a3bf107da','200002','接口参数错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('4ab0a03f-ba31-4458-bf95-6e268ae99af0','240005','该体验码只能购买指定商品，请核查后再使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('4b5d2c59-b001-439f-9b39-152030637cfd','205009','更新购物车商品包装信息失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('4bd6ef55-262e-4e36-8fa7-995200ca8399','208103','该账户名不存在','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('4c56df46-8059-406c-8e07-5730ceca1a9e','210008','退款已完成','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('51857555-c405-4739-ad92-910696ac1d24','208104','您输入的密码和账户名不匹配，请重新输入','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('57b12e4d-4130-4a01-ab35-5ca2d356ecd8','212003','订单状态不是待付款。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('590db662-1035-4b9c-85c8-5c002e679747','212005','无效的链接。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('5d232003-0991-40f0-b955-62a8cee30313','205002','用户未登录','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('5d3b33e4-e61a-4cb3-932b-a90e8d929ca4','220004','F码不能重复使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('62fbdfb8-f7d2-42ca-881f-e137af8a248d','240004','不能使用体验码','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('69db4154-de80-4974-8c73-79f98be0e601','208101','用户名已经被注册','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('6bdcf310-e2cf-4d78-b171-1c4e6c831b82','200001','Invalid request headers','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('6d6c4b2a-c03f-44c1-a66d-9ae3cd9d9531','208124','不存在输入idcard的账户','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('6ed2fea8-9836-43d4-b2f8-e6f83e56ede0','270001','签名验证不通过','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('70165dce-92d2-489c-a5af-efbbab08c78e','281011','趣分期兑换码已过期','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('720934d6-59f8-46d8-8a4a-28a9653e1353','000003','API接口异常错误','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('74b681d9-b304-4f05-b3d3-75c87ea9959c','000001','Unknown errors','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('7d2a5131-af7d-48a8-a78b-b4a194c5602f','200008','网络异常,请稍后重试','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('7e6783db-f61b-4841-94d7-5f5733f392b5','270002','不是小米订单无需处理','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('7ee682f9-54da-4363-88e0-7ada8c445e4b','205010','购物车信息已经发生变化，请确认订单是否已经提交，或重新刷新','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('805bb8d3-5bf7-44a3-855d-5966d0ddf0fa','240002','很遗憾，该体验码已过期','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('82e675b4-0c11-414d-8640-91ddead4f3b1','000001','系统未知错误','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('8507a333-d404-4665-87ae-81553747c777','000010','已被抢光|商品太热门啦，瞬间就被抢光啦~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('8a875a4d-80de-4158-b27c-8d3734847a69','281008','红包同步至小米失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('8c4e3635-4434-45cd-ae45-85ae06634d7d','205011','消费积分密码输入错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('8d656621-610c-4f10-aba6-a34fd1c93fbf','208125','授权失败，请稍后重试','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('8e0f53f3-f830-43bc-9b21-429a21ca6c6f','210006','未找到对应的受理单','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('8f685f9d-de83-4bf3-b7d1-15b2eda60720','240001','体验码不存在，请重新输入','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('913a5960-37d2-48a3-a33b-35c8c74da786','208122','输入的验证码错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('91b8d2f4-3561-40b5-837c-47793a34a8c0','210005','订单不存在','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('92d5c8de-ce8a-4d68-8241-c63f1bd178ba','208100','请正确输入您的手机号码','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('95ec015a-1e7b-418f-9027-dbe556e22a75','205012','输入积分超出您所拥有积分','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('97a83455-a014-470d-a844-d7b638ade5e2','212006','订单金额不匹配。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('97b85c51-3e42-4825-aa05-d29e8f527ecb','000008','任务完成|小伙伴们太给力了，砍价任务已经完成啦~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('9a554aca-b136-4ac7-9413-bbf748c03646','212001','订单状态不一致。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('9be00150-2ebf-46a6-bc33-346bd71ccc85','205018','减库存失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a0e82d5a-1bc5-43e0-bfd9-08ee5358de7d','205016','订单支付金额异常','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a123a0db-d06b-4be3-a33b-e08777b64e7c','206004','输入的样例ID不是眼镜品类。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a1d7ba85-b365-428e-9d18-31be64aac13b','281009','红包和体验码不能同时使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a35204cb-f40f-4765-9d80-e777cea478e0','240003','体验码已被使用，不能重复使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a45a841d-3f1f-4704-b3f8-b8c4118cb9b7','208128','用户表绑定手机号或小米用户表绑定原有用户出错','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a53c9cfe-b7cb-4ffe-be0e-29cbe94b84b1','200009','not have right','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('a882eb92-e398-4d66-b872-5f7b87b19ec1','000016','抱歉|API网络异常~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('a8e0bac1-2877-46f4-af4c-4e8d2bf2a092','208105','密码格式错误','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('aa4ddfdf-45e3-40d6-8788-38a49ff3f898','200000','Invalid token','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('abfa084b-517c-4d90-a392-380e5457d2e9','000014','调取接口，创建订单失败！','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('b26cee4a-0855-4450-b44a-aba5ccb122bc','208116','更改密码失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('b9bcf9ac-3929-4d3e-9c40-8e5a8a61da65','281004','红包已过期','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('bb491747-3c7a-4ce5-bdbc-de66d5716b3b','205005','添加商品至购物车前更新购物车操作状态失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('bcdf9fa5-1bea-45df-bd65-b8424fda29a4','281006','红包已作废','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('bec06964-c15e-4c7e-b070-105de698ff23','210009','退款单已关闭','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('c0b52782-9fda-4859-93ad-f752c40f3853','000006','已被抢光|商品太热门啦，瞬间就被抢光啦~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('c17a360a-4d78-4302-8016-f4319fb5d2dd','100001','抱歉，微信请求失败~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('c53d5bf3-3a33-41fb-aafa-3e7b9804b923','281015','templateId为空','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('c66a966c-1561-413d-ae80-4d00cb6242b0','205019','增加库存失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('c72a7dd0-a3e1-4172-9898-74ea3cc65206','250012','插入数据失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('cce84c9f-1423-4fe3-b3fc-c0017f116dc5','205017','此商品部分材料库存不足','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('cf4e5872-35c5-4a15-a5cb-7e3f36fd8d21','208118','注册失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('d05d5b11-b794-42bf-a52a-42cb30cf33d7','260002','赔偿单必须是赔款失败状态','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('d2d545cb-276b-43b7-b32a-4b93026eb34a','000015','抱歉，当前试用活动已结束，去看看其他活动吧~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('d2d7e8b7-d649-4d82-b5c5-8e9562ad8f2c','200010','生成token失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('d4214a33-575a-447f-b346-4d508dfd1962','100000','Failed to parse Json String','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('dac0a59e-1136-4f15-9af5-4aa802e8b1f3','281001','红包活动已过期','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('dc2c1eef-1ba4-4059-85c9-8ed28f579f07','208102','系统异常错误。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('de4eee77-3b5e-4a99-bc96-b69cbf28cd22','260001','赔偿单必须是未申请状态','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('def6f8fd-569f-479d-8b6b-69bc0427963b','000009','已被抢光|当前活动已结束，感谢参与~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('e209640c-3de9-4e68-bb53-e144318e19fe','230001','商品已被抢光。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('e561cf42-9d87-441d-bcad-c975d2486742','212009','汽车限量购校验未通过','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('e6c3e35b-9cec-459e-90e9-5a01129b5d9f','210007','商家同意退款，等待退款','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('e8befe32-68e2-43e2-96a9-1b6494c26f1c','220003','F码已失效','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('eb71ac41-9cac-436b-9867-4d68e61a9109','240008','体验码已锁定','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('ec2a3cd4-375d-412d-b456-bd00b72ab12a','205001','当前用户不合法','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('ed5b7808-e5a2-4adc-b669-a800308e59e1','205008','更新购物车商品数量失败','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('f010f479-00f0-4991-b091-3071086a71e7','100002','解析微信请求失败~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('f1ce26cd-ea6b-4e77-9c65-1499fcb24e78','000004','您已砍过|您已经帮Ta砍过一次咯~','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('f21bcb0b-f5c6-4611-9ade-5728909385bd','000011','试用单状态有误，操作失败！','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('f238f75d-52a9-486b-baa2-7d70e31f5466','250011','该手机号已预约过该品类','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('f6b3c360-4143-4393-8db0-905f21b85474','212007','订单金额小于等于0。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('f8637659-051f-4663-b1a9-c4c7f7e69961','281002','该用户已申请过此活动。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('fa13cd7f-3dd9-4aba-af62-394814c943b0','210004','退款总金额必须大于0','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('fa8be73f-8110-41aa-a5f3-4f1243ab68be','240006','该体验码只能购买指定商品，请核查后再使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('fc547387-7858-4fcc-b0d1-6c9f6aeb3bb5','000005','很抱歉，您已参与试用活动，在此活动结束后您可再次参与其它活动！','62bde6e0-988b-475f-bfdf-76203455ec57','试用ptapi','2016-04-01 04:14:20',1),('fc56cbde-32fc-44d5-bd07-ca94f06e4c28','281003','红包已使用','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('fd1efbe0-5d30-4a3e-813e-8cabcf5bc2ed','200007','处理过程中出现异常','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1),('fd863656-3cd0-4dc7-b315-abb343bf850b','212004','订单已被支付。','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','2016-04-01 04:14:20',1);
/*!40000 ALTER TABLE `error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface`
--

DROP TABLE IF EXISTS `interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interface` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `url` varchar(100) NOT NULL COMMENT 'api链接',
  `method` varchar(30) NOT NULL COMMENT ' 请求方式',
  `param` text COMMENT '参数列表',
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
  `updateTime` timestamp NOT NULL DEFAULT '2016-01-01 09:40:00',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `menuId` varchar(50) NOT NULL DEFAULT '导航菜单编号',
  `menuName` varchar(50) NOT NULL COMMENT '菜单名称',
  `menuUrl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `roleIds` varchar(512) DEFAULT NULL COMMENT '角色可见集合  （ID之间以逗号分隔）',
  `parentId` varchar(50) DEFAULT '0',
  `iconRemark` varchar(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT '前端菜单、后台菜单',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('07d8d120-34ec-4293-ab93-995c513fc16a','前端底部菜单列表','#/menu/list/0/BOTTOM/一级菜单','','5358c830-7a24-46d8-924e-ff47f3e8fb01','','','2016-04-10 09:56:17',0),('097bde20-9ece-4033-8f81-118e962f791b','模块&接口列表','#/interface/list/0/无','','4a8eee1c-4863-41f3-9ba8-d11c255de46f','','','2016-04-01 04:19:34',1),('0a2e84c9-f097-409d-a568-3f4fcb8d7575','错误码','','0,','0','<i class=\"iconfont\">&#xe60c;</i>','FRONT','2016-04-01 04:19:34',1),('0a57028b-8443-476b-a9dc-6cea8e934a5f','文章列表','index.do#/webPage/list/ARTICLE','','5a459a29-0932-4ef3-b907-579931b432d5','','','2016-04-10 09:32:48',0),('0c560ac0-1124-4ad9-95ec-49f0bf7d94f7','API','#/webInterface/list/9e6596c1-9fda-4cd2-b20a-1cce7e572a65/API模块','4444','1b35d3a5-57fc-4fc7-b458-918523c33042','444','','2016-04-01 04:19:34',1),('17a0bc3b-7b61-44e6-810d-f86acf37f1d8','错误码列表','web.do#/webError/list/9e6596c1-9fda-4cd2-b20a-1cce7e572a65','','0a2e84c9-f097-409d-a568-3f4fcb8d7575','',NULL,'2016-04-01 04:19:34',1),('19ca5c8d-bc19-44b1-bee9-432cdda5b95e','GitHub','https://github.com/EhsanTang/CrapApi','','d1fb098c-d13a-494d-98f1-791bddaec1a7','','','2016-04-09 17:53:25',0),('1b35d3a5-57fc-4fc7-b458-918523c33042','接口列表','','0,','0','<i class=\"iconfont\">&#xe614;</i>','FRONT','2016-04-01 04:19:34',1),('1cd9e13b-f431-4c18-abf4-e44e43b99410','免费常用接口','web.do#/webInterface/list/62bde6e0-988b-475f-bfdf-76203455ec57/免费常用接口','1','1b35d3a5-57fc-4fc7-b458-918523c33042','','','2016-04-01 04:19:34',1),('241f31ab-3dcb-4290-a485-644540d38433','后台菜单列表','index.do#/menu/list/0/BACK/一级菜单','','5358c830-7a24-46d8-924e-ff47f3e8fb01','','','2016-04-01 04:19:34',1),('2539e1e5-bc0e-4dc4-a27c-57658ce3c892','开源中国','http://www.oschina.net/p/crapapi','','0','','BOTTOM','2016-04-10 09:57:26',0),('28e9c99c-c365-41df-8591-771c4301157e','CrapBlog','http://blog.crap.cn/wordpress/','','0','','FRIEND','2016-04-16 15:39:56',0),('2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0','用户管理','','b304b5c2-9186-4bd2-851f-2979e03b8f83,','0','<i class=\"iconfont\">&#xe603;</i>','BACK','2016-04-01 04:19:34',1),('2f107703-3e03-440c-8297-5ccccae8f8da','','','','','',NULL,'2016-04-01 04:19:34',1),('3384cd9c-ff39-41b4-b5fc-84044fad6d30','角色管理','','b304b5c2-9186-4bd2-851f-2979e03b8f83,','0','<i class=\"iconfont\">&#xe612;</i>','BACK','2016-04-01 04:19:34',1),('34439446-6014-45f3-9d30-f29180e0b4b6','常用接口指南','#/webWebPage/list/ARTICLE/常用接口','','0','','TOP','2016-04-10 14:43:13',0),('4a8eee1c-4863-41f3-9ba8-d11c255de46f','项目&模块&接口管理','','bc1bbac0-68a4-4063-a216-c57c6da47c0d,','0','<i class=\"iconfont\">&#xe614;</i>','BACK','2016-04-01 04:19:34',1),('5358c830-7a24-46d8-924e-ff47f3e8fb01','菜单管理','','','0','<i class=\"iconfont\">&#xe60f;</i>','BACK','2016-04-01 04:19:34',1),('5891d916-a05d-4864-8c05-f8a844e71dd9','系统设置','','','0','<i class=\"iconfont\">&#xe61a;</i>','BACK','2016-04-03 22:09:55',0),('5a459a29-0932-4ef3-b907-579931b432d5','网站页面&文章管理','','','0','<i class=\"iconfont\">&#xe61f;</i>','BACK','2016-04-10 09:29:41',0),('5aa11f29-7acc-4f93-9b51-6bf47d654007','22222222','22222222222','2','1',NULL,NULL,'2016-04-01 04:19:34',1),('6417012b-ed74-49b5-aa93-c219832acb30','前端主菜单列表','index.do#/menu/list/0/FRONT/一级菜单','','5358c830-7a24-46d8-924e-ff47f3e8fb01','','','2016-04-10 09:55:41',0),('87534e09-1636-45fd-92a2-b5b90f5da58b','数据字典管理','','','0','<i class=\"iconfont\">&#xe61c;</i>','BACK','2016-04-05 14:53:33',0),('885df3f1-3476-4c87-b656-885bea1802f8','菜单3','1111111111','2','1',NULL,NULL,'2016-04-01 04:19:34',1),('8c868bf7-e330-40bf-a377-5c3f8f5c1fd7','1111','1333','0','1',NULL,NULL,'2016-04-01 04:19:34',1),('8d500f70-ea2a-46b8-a7a7-7013cb59e423','数据字典列表','index.do#/webPage/list/DICTIONARY','','87534e09-1636-45fd-92a2-b5b90f5da58b','','','2016-04-05 14:53:53',0),('92d94fa5-b98b-4397-89e2-a12f6df66aa1','项目帮助文档','web.do#/webWebPage/list/ARTICLE/帮助文档','','0','','TOP','2016-04-10 09:26:36',0),('932c923a-aea3-44a2-ba03-0a97cffbc09a','角色列表','index.do#/role/list','','3384cd9c-ff39-41b4-b5fc-84044fad6d30','','','2016-04-01 04:19:34',1),('9bb50876-abf1-46ec-8018-2ffe2510c83b','系统设置列表','index.do#/setting/list','','5891d916-a05d-4864-8c05-f8a844e71dd9','','','2016-04-03 22:35:17',0),('a05ba495-e9b9-4433-92f2-d71f92bdac6a','数据字典列表','web.do#/webWebPage/list/DICTIONARY/null','','f8764c78-a281-4133-b19f-109123a49a5f','','','2016-04-05 14:54:43',0),('ad12d510-c62b-4b4e-8fd5-837aece1fe27','KL博客','http://www.kailing.pub/','','0','','FRIEND','2016-04-12 15:06:40',0),('ba319708-1469-40a9-b09d-084ebd31c009','联系我','web.do#/webWebPage/detail/PAGE/CONTACT','','d1fb098c-d13a-494d-98f1-791bddaec1a7','','','2016-04-10 15:39:32',0),('c58fe99e-4050-477c-b60b-3513375e1996','用户列表','index.do#/user/list','','2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0','','','2016-04-01 04:19:34',1),('c61bec50-4cf9-41dd-9bd1-0fa8cf46ce38','ketayao博客','http://ketayao.com/','','0','','FRIEND','2016-04-09 09:08:33',0),('d082ec96-1ac5-4429-99e3-e02183d25628','前端顶部菜单列表','index.do#/menu/list/0/TOP/一级菜单','','5358c830-7a24-46d8-924e-ff47f3e8fb01','','','2016-04-10 09:56:00',0),('d1fb098c-d13a-494d-98f1-791bddaec1a7','关于我','','','0','','TOP','2016-04-09 17:53:12',0),('de6f4d42-bf2e-4e1b-a0b8-a706cf24addb','错误码管理','','bc1bbac0-68a4-4063-a216-c57c6da47c0d,','0','<i class=\"iconfont\">&#xe608;</i>','BACK','2016-04-01 04:19:34',1),('ded3b50d-1591-41e7-b642-14bf08bb740b','错误码列表','index.do#/error/list','c62a80c5-6064-4815-ac59-e507a8e22a6f,','de6f4d42-bf2e-4e1b-a0b8-a706cf24addb','','','2016-04-01 04:19:34',1),('e7250482-4fef-40e5-8cdf-09ec401290ce','旧城博客','http://blog.jiucheng.org/?fr=apicrapcn','','0','','FRIEND','2016-04-15 05:13:53',0),('f8233aa7-ccf9-48fe-9e31-60a1c6b34c4b','网站页面列表','index.do#/webPage/list/PAGE','','5a459a29-0932-4ef3-b907-579931b432d5','','','2016-04-10 09:32:27',0),('f8764c78-a281-4133-b19f-109123a49a5f','数据字典','','','0','<i class=\"iconfont\">&#xe61c;</i>','FRONT','2016-04-05 14:54:12',0),('fc0e431a-c964-4a5f-b49e-10f13ae56b10','AppApi项目','web.do#/webInterface/list/e634d522-aad8-4cdb-9c13-105ee7b17eab/AppApi模块','11','1b35d3a5-57fc-4fc7-b458-918523c33042','','','2016-04-01 04:19:34',1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `moduleId` varchar(50) NOT NULL COMMENT '所属模块ID',
  `moduleName` varchar(100) NOT NULL COMMENT '所属模块名称',
  `parentId` varchar(50) DEFAULT NULL COMMENT '父级节点ID',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `password` varchar(20) DEFAULT NULL COMMENT '访问密码',
  PRIMARY KEY (`moduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES ('063578aa-785f-42b8-bd1b-e3061ef45a2f','订单模块','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','2016-04-01 04:21:03',1,'123'),('189d073c-b4ea-4e50-a1c1-f66ec1da1dd4','购物车模块','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','2016-04-01 04:21:03',1,'123'),('563d4de0-5afd-4448-bf9e-a1a30b197167','用户模块','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','2016-04-01 04:21:03',1,NULL),('62bde6e0-988b-475f-bfdf-76203455ec57','免费常用接口','0','2016-04-01 04:21:03',1,''),('643b1586-6861-434b-b644-979bdd5f87ee','11','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','2016-04-12 13:56:36',0,''),('6462bd9d-db3f-48dd-abee-03bab9faa806','aaa','9e6596c1-9fda-4cd2-b20a-1cce7e572a65','2016-04-05 04:24:35',0,''),('9e6596c1-9fda-4cd2-b20a-1cce7e572a65','API模块','0','2016-04-01 04:21:03',1,'123'),('bfdc49dd-4999-4ab4-a41a-1bda782d71fa','短信模块','e634d522-aad8-4cdb-9c13-105ee7b17eab','2016-04-01 04:21:03',1,NULL),('cf9f682a-fd76-4ab5-9300-9210164ae2ea','订单模块','e634d522-aad8-4cdb-9c13-105ee7b17eab','2016-04-01 04:21:03',1,NULL),('e634d522-aad8-4cdb-9c13-105ee7b17eab','AppApi模块','0','2016-04-01 04:21:03',1,NULL),('f86d375e-d1db-4eeb-84fa-9e9def04fc6d','购物车模块','e634d522-aad8-4cdb-9c13-105ee7b17eab','2016-04-01 04:21:03',1,NULL);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `roleId` varchar(50) NOT NULL COMMENT '角色ID',
  `roleName` varchar(50) NOT NULL COMMENT '角色名称',
  `auth` text NOT NULL,
  `authName` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ee479a4b-f445-4130-a295-572e6126ec96','test','MODULE_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,MODULE_e634d522-aad8-4cdb-9c13-105ee7b17eab,INTERFACE_62bde6e0-988b-475f-bfdf-76203455ec57,INTERFACE_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,INTERFACE_e634d522-aad8-4cdb-9c13-105ee7b17eab,ERROR_62bde6e0-988b-475f-bfdf-76203455ec57,ERROR_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,ERROR_e634d522-aad8-4cdb-9c13-105ee7b17eab,SETTING,DICTIONARY_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,DICTIONARY_e634d522-aad8-4cdb-9c13-105ee7b17eab,5a459a29-0932-4ef3-b907-579931b432d5,87534e09-1636-45fd-92a2-b5b90f5da58b,5891d916-a05d-4864-8c05-f8a844e71dd9,2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0,3384cd9c-ff39-41b4-b5fc-84044fad6d30,4a8eee1c-4863-41f3-9ba8-d11c255de46f,5358c830-7a24-46d8-924e-ff47f3e8fb01,de6f4d42-bf2e-4e1b-a0b8-a706cf24addb,','API模块【模块】,AppApi模块【模块】,免费常用接口【接口】,API模块【接口】,AppApi模块【接口】,免费常用接口【错误码】,API模块【错误码】,AppApi模块【错误码】,系统设置管理,API模块,AppApi模块,网站页面&文章管理【菜单】,数据字典管理【菜单】,系统设置【菜单】,用户管理【菜单】,角色管理【菜单】,项目&模块&接口管理【菜单】,菜单管理【菜单】,错误码管理【菜单】,','2016-04-01 04:22:07',1);
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
  `value` varchar(100) NOT NULL,
  `remark` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `type` varchar(10) NOT NULL DEFAULT 'TEXT' COMMENT '设置类型（IMAGE,LINK,TEXT）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`mkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('062f01ae-e50b-4dd3-808b-b4a6d65eeadc','LOGO','http://api.crap.cn/resources/upload/images/2016-04-04/064741q2A4JA.png','网站主logo，可以直接在value中填写绝对链接地址，也可以自行上传图片','2016-04-01 04:23:18',1,'IMAGE'),('8a95bc2f-ea61-4dd6-8163-d9c520b28181','VISITCODE','true','游客访问私密模块输入密码的同时是否需要输入图像验证码？true为需要，其他为不需要','2016-04-03 10:07:14',0,'TEXT'),('98ecca1b-f762-4cd3-831a-4042b36419d8','VERIFICATIONCODE','fasle','是否开启安全登录？ture为开启，其他为不开启，开启后登录将需要输入图片验证码','2016-04-03 10:07:58',0,'TEXT'),('b97a3a75-c1c3-42cc-b944-8fb5ac5c5f49','SECRETKEY','crapApiKey','秘钥，用于cookie加密等','2016-04-02 17:04:40',0,'TEXT'),('de94c622-02fc-4b39-9cc5-0c24870ac21f','TITLE','CrapApi|Api接口管理系统','站点标题','2016-04-02 11:09:13',1,'TEXT'),('e0dec957-5043-4c6e-9225-960fbc401116','ICON','http://api.crap.cn/resources/upload/images/2016-04-04/063633hG35aC.ico','站点ICON图标（浏览器标题栏图标）','2016-04-02 11:49:41',1,'IMAGE'),('e0dec957-5043-4c6e-9225-960fbc401117','DOMAIN','http://api.crap.cn','站点域名','2016-04-02 11:09:13',1,'TEXT');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `trueName` varchar(50) DEFAULT NULL,
  `roleId` varchar(1024) DEFAULT NULL,
  `roleName` varchar(1024) DEFAULT NULL,
  `auth` varchar(1024) DEFAULT NULL,
  `authName` varchar(1024) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('206a1218-5c81-48af-b8e4-25864ad5e929','super','e10adc3949ba59abbe56e057f20f883e','测试用户','ee479a4b-f445-4130-a295-572e6126ec96,','test,','','','2016-04-01 04:24:00',1),('6e8af9da-ed79-4342-9eca-591ccb4ea658','admin','e10adc3949ba59abbe56e057f20f883e','超级管理员','super,','超级管理员,','','','2016-04-01 04:24:00',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webpage`
--

DROP TABLE IF EXISTS `webpage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webpage` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `brief` varchar(200) DEFAULT NULL,
  `content` text NOT NULL,
  `click` int(11) NOT NULL DEFAULT '0',
  `type` varchar(20) NOT NULL DEFAULT 'PAGE',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `moduleId` varchar(50) DEFAULT NULL,
  `mkey` varchar(20) DEFAULT NULL COMMENT 'key，唯一键，页面唯一标识',
  `canDelete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可删除，可修key，默认可以',
  `category` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mkey_UNIQUE` (`mkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webpage`
--

LOCK TABLES `webpage` WRITE;
/*!40000 ALTER TABLE `webpage` DISABLE KEYS */;
INSERT INTO `webpage` VALUES ('07bdc59d-e407-4ef0-8ff7-bf86449e7efd','用户表，存储用户信息','样例表：用户表，存储用户信息用户表，存储用户信息用户表，存储用户信息','[{\"name\":\"id\",\"type\":\"int\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"主键，自增\"},{\"name\":\"userName\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户名\"},{\"name\":\"password\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户密码\"},{\"name\":\"email\",\"type\":\"varchar(20)\",\"notNull\":\"true\",\"def\":\"\",\"remark\":\"邮箱\"},{\"name\":\"gender\",\"type\":\"varchar(2)\",\"notNull\":\"false\",\"def\":\"男\",\"remark\":\"性别\"}]',0,'DICTIONARY',0,'2016-04-05 14:52:41','62bde6e0-988b-475f-bfdf-76203455ec57',NULL,1,NULL),('07bdc59d-e4073-4ef0-8ff7-bf86449e7efd','用户表，存储用户信息','样例表：用户表，存储用户信息用户表，存储用户信息用户表，存储用户信息','[{\"name\":\"id\",\"type\":\"int\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"主键，自增\"},{\"name\":\"userName\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户名\"},{\"name\":\"password\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户密码\"},{\"name\":\"email\",\"type\":\"varchar(20)\",\"notNull\":\"true\",\"def\":\"\",\"remark\":\"邮箱\"},{\"name\":\"gender\",\"type\":\"varchar(2)\",\"notNull\":\"false\",\"def\":\"男\",\"remark\":\"性别\"}]',0,'DICTIONARY',0,'2016-04-05 06:52:41','62bde6e0-988b-475f-bfdf-762034535ec57',NULL,1,NULL),('19dc3d5c7-ff3d-4dff-ad72-2212869cd92a','项目部署','','<p><br/></p><p>CrapApi接口管理项目中的pdf导出功能目前只支持windows服务器，在linux服务器上部署导出的pdf无法显示中文</p><p>如你更好的解决方案（html转pdf），可点击“联系我”或加群讨论</p><p><br/></p>',0,'ARTICLE',0,'2016-04-10 01:51:53','',NULL,1,'帮助文档'),('2595b098-4e27-4e28-8a50-7241741f7619','管理员帮助中心','支持接口pdf文件下载\n支持发布文章，文章分类\n支持自定义网站页面','<blockquote style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><p>   感谢使用CrapApi应用接口管理系统！</p><p>   如您发现好的免费常用API接口，可在后台管理，文章管理一栏添加相关文章，不胜感激！<br/></p></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><blockquote style=\"color: rgb(51, 51, 51);background-color: rgb(255, 255, 255);\"><p>  一个由anjularjs+bootstrap+springMVC搭建的免费开源的API接口管理系统。</p><p>  系统特点：</p><p></p><ul><li> 系统支持管理员管理、权限控制、角色管理</li><li>支持多项目、多模块划分，同时支持私密项目、模块密码访问</li><li>支持数据字典管理、支持数据字典密码访问<br/></li><li>支持项目错误码管理、支持接口拷贝等功能<br/></li><li>支持前端菜单自定义，支持管理员菜单自定义</li><li>支持接口pdf文件下载<br/></li><li>支持发布文章，文章分类<br/></li><li>支持自定义网站页面<br/></li></ul></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\">常见问题：</p><ol style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">什么是【游客图形验证码】？如果开启了【游客图形验证码】，则游客在访问私密模块时不仅需要输入访问密码，而且需要输入【游客图形验证码】，开启【游客图形验证码】能有效防止暴力破解模块访问密码。<br/></li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">什么是【模块密码】？【模块密码】对后台管理没有任何影响，若为模块添加了密码，当游客访问该模块时需要输入访问密码才能浏览，【模块密码】只在当前模块有效，即子模块不能继承父模块的密码。故建议将【模块密码】设置在需要控制访问的接口所属的直接模块上。若多个模块密码一致，游客输入密码后无需再次输入，直接就可以访问。如没有开启游客图形验证码，则关闭浏览器后密码将自动失效。</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">为什么游客输入过访问密码后，再次访问依然需要再次输入访问密码？（1）系统只保留用户最后一次输入的访问密码，如果多个模块需要访问密码，则当用户交叉访问时，后一个密码将覆盖前一个密码，因此需要再次输入访问密码。</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">显示样式有问题？所有测试均使用Chrome浏览器，请优先使用Chrome、Firefox、Safari浏览器访问</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">GitHub地址：https://github.com/EhsanTang/CrapApi</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">QQ技术交流群：254450938</li></ol>',0,'PAGE',0,'2016-04-09 16:22:32','','ADMINHELP',0,''),('377d6580-657f-420b-88fd-d7d239f05cb5','帮助中心','','<blockquote style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><p>   感谢使用CrapApi应用接口管理系统！</p><p>   如您发现好的免费常用API接口，可在后台管理，文章管理一栏添加相关文章，不胜感激！</p></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><blockquote style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><p><span>  一个由anjularjs+bootstrap+springMVC搭建的免费开源的API接口管理系统</span><span>。</span></p><p><span>  系统特点：</span></p><p></p><ul><li><span>系统</span><span>支持管理员管理、权限控制、角色管理</span></li><li><span>支持多项目、多模块划分，同时支持私密项目、模块密码访问</span></li><li><span>支持数据字典管理、支持数据字典密码访问<br/></span></li><li><span>支持项目错误码管理、支持接口拷贝等功能<br/></span></li><li><span>支持前端菜单自定义，支持管理员菜单自定义</span></li><li><span>支持接口pdf文件下载</span></li><li>支持发布文章，文章分类</li><li>支持自定义网站页面</li></ul></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\">常见问题：</p><ol style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><li>管理员账号：super 123456</li><li>私有接口访问密码：123</li><li>显示样式有问题：所有测试均使用Chrome浏览器，请优先使用Chrome、Firefox、Safari浏览器访问</li><li>页面加载异常：项目正在不断完善，可能是文件缓存导致，请刷新浏览器重试。</li><li>GitHub地址：https://github.com/EhsanTang/CrapApi</li><li>QQ技术交流群：254450938</li></ol>',0,'PAGE',0,'2016-04-09 16:13:09','','WELCOME',0,''),('395b13aa-920c-4697-b95e-8d8c00342001','天气预报接口','气象数据开放接口是中国气象局面向网络媒体、手机厂商、第三方气象服务机构等用户，\n通过web方式提供气象数据服务的官方载体。随着气象数据开放平台的逐渐完善，\n会面向广大用户提供更为丰富全面的气象数据，从而满足不同用户的不同需求。','<p>气象数据开放平台：<a href=\"http://openweather.weather.com.cn/Home/Package/index.html\" target=\"\">http://openweather.weather.com.cn/Home/Package/index.html</a></p><p>1.常规气象数据接口</p><ul><li>包含国内全站2566个县级以上城市未来3天常规预报以及三个气象指数（穿衣指数，舒适度指数，晨练指数）等天气服务数据。 <br/></li><li>费用：每年300元</li></ul><p>2.基础气象数据接口</p><ul><li>包含国内369个地级市未来3天常规预报以及三个气象指数（穿衣指数，舒适度指数，晨练指数）等天气服务数据。<br/></li><li>费用：每年30元</li></ul><p><br/></p><p>直接通过气象局调用以上两个接口现阶段免费，未来可能需要收费，需要注册账号并完善资料后才能调用</p><p>目前气象局对外公开的直接可调用的接口如下：</p><ul><li><span class=\"s1\">http:</span>//www.weather.com.cn/data/sk/101010100.html</li><li><span class=\"s1\">http:</span>//www.weather.com.cn/data/cityinfo/101010100.html</li><li>101010100代表城市代码</li></ul><p class=\"p2\" style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><p class=\"p2\" style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">国外天气预报接口：<a href=\"https://www.wunderground.com/weather/api\" target=\"\">https://www.wunderground.com/weather/api</a></p><p class=\"p2\" style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">城市代码可直接通过气象局官网查询，地址：<a href=\"http://openweather.weather.com.cn/Home/Help/area.html\" target=\"\">http://openweather.weather.com.cn/Home/Help/area.html</a></p>',0,'ARTICLE',0,'2016-04-10 15:01:18','',NULL,1,'常用接口'),('63864a72-ab64-4350-a3d7-d25d5bfc88bf','手机号码归属地查询接口大全（七种）','淘宝、拍拍、财付通、百付宝等手机号码查询接口','<p>淘宝网<br/>API地址： http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15850781443<br/>参数：<br/>tel：手机号码<br/>返回：JSON</p><p><br/>拍拍<br/>API地址： http://virtual.paipai.com/extinfo/GetMobileProductInfo?mobile=15850781443&amp;amount=10000&amp;callname=getPhoneNumInfoExtCallback<br/>参数：<br/>mobile：手机号码<br/>callname：回调函数<br/>amount：未知（必须）<br/>返回：JSON</p><p><br/>财付通<br/>API地址： http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=15850781443<br/>参数：<br/>chgmobile：手机号码<br/>返回：xml</p><p><br/>百付宝<br/>API地址： https://www.baifubao.com/callback?cmd=1059&amp;callback=phone&amp;phone=15850781443<br/>参数：<br/>phone：手机号码<br/>callback：回调函数<br/>cmd：未知（必须）<br/>返回：JSON</p><p><br/>115<br/>API地址： http://cz.115.com/?ct=index&amp;ac=get_mobile_local&amp;callback=jsonp1333962541001&amp;mobile=15850781443<br/>参数：<br/>mobile：手机号码<br/>callback：回调函数<br/>返回：JSON<br/><br/>有道api接口<br/>接口地址：http://www.youdao.com/smartresult-xml/search.s?type=mobile&amp;q=13892101112<br/>参数说明：<br/>type ： 参数手机归属地固定为mobile<br/>q ： 手机号码<br/>返回XML格式：<br/>或者<br/>http://www.youdao.com/smartresult-xml/search.s?jsFlag=true&amp;type=mobile&amp;q=手机号码<br/>返回JSON格式：<br/>fYodaoCallBack(1, {‘product’:\'mobile’,\'phonenum’:’13892101112′,’location’:\'陕西 延安’} , ”);</p><p><br/>096.me api接口<br/>查询手机号码归属地：<br/>http://www.096.me/api.php?phone=手机号&amp;mode={txt,xml}<br/>举例：http://www.096.me/api.php?phone=13892101111&amp;mode=txt<br/>返回：<br/>13892101111||陕西延安移动全球通卡 ||吉凶参半，惟赖勇气，贯彻力行，始可成功 吉带凶||<br/>举例：http://www.096.me/api.php?phone=13892101111&amp;mode=xml<br/>返回：<br/>13892101111 陕西延安移动全球通卡 吉凶参半，惟赖勇气，贯彻力行，始可成功 吉带凶<br/><br/></p>',0,'ARTICLE',0,'2016-04-12 15:34:31','',NULL,1,'常用接口'),('74987b7c-54bc-4043-aa6b-de949303b2a0','联系我','','<blockquote><p>   热爱开源，梦想成为一名自由程序猿</p><p><i></i>   喜欢Java、bootstrap、angular的朋友一起交流、学习，如果有兴趣可以交换链接哦</p></blockquote><h2><br/></h2><h5><p><br/></p><p>联系方式：</p><ul><li><span>QQ技术交流群：254450938</span></li><li><span>Email：ehsantang@163.com</span></li><li><span>QQ：516452267</span></li></ul></h5>',0,'PAGE',0,'2016-04-09 17:46:49','','CONTACT',1,NULL),('8b4f67de-034ba-4a1a-b7c3-e3d5cd74e45b','测试','用于介绍test的一个demo','[]',0,'DICTIONARY',0,'2016-04-05 06:56:51','9e6596c1-9fda-4cd2-b20a-1cce7e572a65',NULL,1,''),('8b4f67de-04ba-4a1a-b7c3-e3d5cd74e45b','测试','测试简介，测试简介，测试简介，测试简介测试简介测试简介测试简介测试简介测试简介测试简介','[{\"name\":\"field1\",\"type\":\"int\",\"notNull\":\"true\",\"def\":\"无\",\"remark\":\"字段1\"},{\"name\":\"field2\",\"type\":\"varchar(20)\",\"notNull\":\"false\",\"def\":\"默认值\",\"remark\":\"字段2\"},{\"name\":\"field3\",\"type\":\"int\",\"notNull\":\"true\",\"def\":\"默认值22\",\"remark\":\"字段3\"}]',0,'DICTIONARY',0,'2016-04-05 14:56:51','9e6596c1-9fda-4cd2-b20a-1cce7e572a65',NULL,1,NULL),('9385500e-5dc2-4433-8a4b-47158620ef36','CrapApi接口管理系统菜单设置','CrapApi系统菜单目前分为后台菜单、前端菜单、底部菜单、顶部菜单、友情链接五大类，\n其中后台菜单、前端菜单、顶部菜单支持二级菜单','<p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">1.菜单分类</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>CrapApi系统菜单目前分为后台菜单、前端菜单、底部菜单、顶部菜单、友情链接五大类，其中后台菜单、前端菜单、顶部菜单支持二级菜单</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span>后台菜单</span>：后台菜单是管理员菜单，不同权限、角色可以个性化定制不同菜单，后台管理菜单只支持二级菜单，即使一级菜单设置了超链接，也不可点击，拥有【超级管理员】权限的账号等候后即使没有配置任何菜单，任然可以看到如图一的默认菜单，非【超级管理员】的普通管理员需要设置后台菜单</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>前端菜单：前端菜单是游客访问网站看到的主菜单（左侧菜单），该菜单也只支持二级分类，即使一级菜单设置了超链接，也不可点击</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>底部菜单：底部菜单在游客访问网站时的底部显示（倒数第二排），底部菜单只持此一级菜单，二级菜单不显示</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>顶部菜单：顶部菜单在游客访问网站是的顶部，支持一级和二级（没有二级一级可点击，如果有二级菜单，一级菜超链接无效）</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>友情链接：友情链接在游客访问网站的底部，至支持一次菜单，二级菜单不显示<span><br/></span><span></span></p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><img src=\"http://img.blog.csdn.net/20160413230916372?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center\" alt=\"\"/><br/></p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><img src=\"http://img.blog.csdn.net/20160413232535864?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center\" alt=\"\"/><br/></p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">2. 菜单修改</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>进入后台，点击菜单管理，超级管理员和拥有权限的普通管理员可以看到菜单对应的【修改】【删除】【查看子菜单】，点击修改即可修改对应的菜单，默认进入显示的是【前端菜单】，可以在菜单列表的右上根据类型或菜单名搜索菜单，如下图：在【类型】栏点击输入框即可选着类型，链接可以自己输入，也可点击【选着地址】按钮进行选择。</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>在链接下拉选着中，显示了站内通用的地址，主要包括【后台链接】（后台管理员相关的链接）和【前端链接】（游客相关的链接）两种类型，其中包括：前后台模块列表页面，前后台接口列表页面，前端文章分类页面，后台菜单分类列表页面，文章分类列表页面，网站页面等，一级菜单必须选择类型，二级菜单不需要选择类型</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><img src=\"http://img.blog.csdn.net/20160413233104437?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center\" alt=\"\"/><br/></p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">3.菜单图标设置</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><span></span>后台一级菜单，前端一级菜单支持显示图标，类容为:&lt;i class=&#34;iconfont&#34;&gt;<span style=\"color: rgb(204, 0, 0);\">&amp;#xxxxx;</span>&lt;i&gt;，红色部分请根据需要显示的图标填写，目前支持的图标及对应的代码如下：</p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\"><img src=\"http://img.blog.csdn.net/20160413234255339?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center\" alt=\"\"/><br/></p><p style=\"color: rgb(51, 51, 51);text-align: left;background-color: rgb(255, 255, 255);\">CSDN文章地址：<a href=\"http://blog.csdn.net/torrytang/article/details/51147823\" target=\"\">http://blog.csdn.net/torrytang/article/details/51147823</a><br/></p>',0,'ARTICLE',0,'2016-04-13 15:47:35','',NULL,1,'帮助文档'),('a6716dfc-0a0b-4c6f-932c-faf7b50a61b8','131','12312','<p>123213<br/></p>',0,'ARTICLE',0,'2016-04-12 09:21:34','',NULL,1,'13213'),('bd3fbc06-ef88-43d3-bf5b-1631c854efcd','项目二次开发环境搭建','CrapApi接口管理系统，是一个基于maven构建的javaweb项目\n文章将以eclipse作为开发工具进行项目搭建介绍\n本开源项目源代码托管于GitHub','<p>构建准备：</p><ol><li>下载eclipse开发工具</li><li><a href=\"http://blog.csdn.net/torrytang/article/details/51114134\" target=\"\">安装git插件（用于从github获取源码）</a></li><li><a href=\"http://blog.csdn.net/torrytang/article/details/51114155\" target=\"\">安装maven插件（本项目是基于maven构建的）</a></li><li>GitHub源码仓库地址：<a href=\"https://github.com/EhsanTang/CrapApi.git\" target=\"\">https://github.com/EhsanTang/CrapApi.git</a></li><li>安装mysql数据库，本项目使用springMVC+hibernate开发，你也可以使用其他数据库（使用其它数据库请修改连接池等信息）</li></ol><p>开始导入项目：</p><ol><li>安装好插件后关闭eclipse并重新打开</li><li>部署数据库（mysql），数据库脚本地址：<a href=\"https://github.com/EhsanTang/CrapApi/tree/master/api\" target=\"\"></a><a href=\"https://github.com/EhsanTang/CrapApi/blob/master/api/%E6%95%B0%E6%8D%AE%E5%BA%93%E8%84%9A%E6%9C%AC.sql\" target=\"\">https://github.com/EhsanTang/CrapApi/blob/master/api/%E6%95%B0%E6%8D%AE%E5%BA%93%E8%84%9A%E6%9C%AC.sql</a></li><li>导入项目后修改数据库配置，配置文件见项目目录：CrapApi/api/src/main/resources/jdbc.properties</li><li>导入项目</li></ol><p><br/></p><div class=\"tc\"><img src=\"resources/upload/images/2016-04-10/181857892iau.png\" style=\"width: 50%;\"/></div><p><br/></p><div class=\"tc\"><img src=\"resources/upload/images/2016-04-10/181932yGjG8w.png\" style=\"width: 50%;\"/></div><p><br/></p><div class=\"tc\"><img src=\"resources/upload/images/2016-04-10/182014Hf5iui.png\" style=\"width: 50%;\"/></div><p><br/></p><div class=\"tc\"><img src=\"resources/upload/images/2016-04-10/1820286j185H.png\" style=\"width: 50%;\"/></div><p><br/></p><p>            5.修改log4j日志文件输入地址：根据开发系统或部署的系统，修改日志文件输出地址（下图为mac日志输出地址）</p><div class=\"tc\"><img src=\"resources/upload/images/2016-04-17/0257599QKMcy.png\" style=\"height: 330px;width: 601px;\"/></div><p><br/></p><p>点击next即可导入项目，导入项目后如果项目报错，请查看文章：<a href=\"http://blog.csdn.net/torrytang/article/details/51072468\" target=\"\" style=\"color: rgb(51, 122, 183);text-align: left;background-color: rgb(255, 255, 255);\">http://blog.csdn.net/torrytang/article/details/51072468</a><br/></p>',0,'ARTICLE',0,'2016-04-10 01:26:13','',NULL,1,'帮助文档');
/*!40000 ALTER TABLE `webpage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'api'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-17  3:03:33
