/*
Navicat MySQL Data Transfer

Source Server         : api.crap.cn
Source Server Version : 50616
Source Host           : rdser3mvttgp20ftyozwc.mysql.rds.aliyuncs.com:3306
Source Database       : api

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-04-06 17:24:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `error`
-- ----------------------------
DROP TABLE IF EXISTS `error`;
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

-- ----------------------------
-- Records of error
-- ----------------------------
INSERT INTO `error` VALUES ('0008a40d-b79c-4579-a9f9-f0a04dba1403', '205013', '获取收获地址出错', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('00743df0-a358-4dd1-8856-dbdeb1f78633', '281007', '红包不存在', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('00932ac3-1b4d-4b27-8a54-46ae2c907ab5', '210001', '确认收货已超过15天，不能发起售后', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0173ac20-bb4d-4704-8a85-1077dc9b84d0', '000007', '活动尚未开始，申请失败！', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0185f80b-a467-49c3-bd96-27c6797be0f4', '220001', 'F码无效，请核对后重新输入', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('02107ca6-f855-490c-85f2-94e1cbdf17a3', '208109', '验证码错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('04497cf7-2f10-41a5-9519-eb058a376196', '200004', 'Unsupported http request Method', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0500dcf9-4548-4dac-b769-2d2c3585cdbd', '000000', 'Correct!', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0a5f7843-cbc1-4ca6-85ae-b47bf1d4bc4d', '208121', '不是有效的手机号', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0a826872-7e77-4ed5-afc2-c1ddf6b44272', '240007', '体验码已作废', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('0b4e412f-e09f-4bd8-8738-be79aa089f74', '205014', '保存订单异常', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('17a69314-a708-45de-88d4-2ee6b345860a', '212008', '订单不属于指定的商家', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('18d18226-43eb-47d6-98e3-b773dd32ed8b', '220002', 'F码已失效', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('193625e6-a881-4152-b659-fc08193b8ffa', '212002', '订单不存在。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1a040af7-8474-4fe1-8703-8594ad69a44e', '208123', '输入的手机号为空', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1de575fb-706b-4b9f-95c8-f6c77865e6dd', '210002', '所有商品都已退货，不能再发起退货', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1e01a3dd-1711-44ab-8481-3f15c57cdcde', '000012', '抱歉，您所选择的试用商品已被抢完，去看看其他活动吧~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1e85c7e3-9ee9-4884-8c4f-061a2ad0e8ad', '205004', '当前用户操作不合法', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1f60abf1-698a-4612-acde-021358376175', '205006', '商家未设置包装信息', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('1f6745e4-3a11-4a7a-8080-4c8d5d457c10', '208126', '获取用户信息失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('2069622d-579e-460e-89c0-dcfaa924fc82', '281014', '趣分期兑换uid为空', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('249f00da-9465-41e0-97b9-18ac6e9075f6', '208106', '输入的密码和原密码相同', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('26622971-152d-4b31-9ba3-0710ce8e2850', '205007', '删除指定商品失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('275be2d3-c3a3-4a0e-8abc-9045033913cc', '200005', 'Unsupported http request Method', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('2d12aef3-99f4-4d21-bf78-25eb0b2ced82', '205003', '商品添加至购物车失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('2ece77ba-acdd-4abe-a159-c7ed2038fe83', '281012', '趣分期兑换码已使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('303e8413-6cfd-4c42-999f-103cbc14d038', '205015', '未找到待支付的订单', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('3154701e-7edc-42b7-b8ce-79a96b2d84cf', '211001', '提现税额计算错误。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('3380cef1-432e-4d18-a556-c4b4abd7c279', '281013', '趣分期兑换码无效', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('37af2829-4d38-42ba-bb95-964d5b3aff47', '210003', '此商品有正在处理中未完成的退货申请', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('39bdddf8-f491-42e1-8c84-3f55ded2a43c', '281005', '红包使用中', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('3c692a35-19c3-48d4-b2c7-e854208963c2', '000002', '授权验证失败', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('416a5375-9dc7-46d6-b8cc-6af4d28ead39', '281010', '趣分期兑换码长度错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('4521bab8-65f0-4e74-9029-e606cc3b3097', '210010', '使用红包的商品，退款金额与最大可退金额不匹配', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('47f19229-a789-418e-b499-a32a3bf107da', '200002', '接口参数错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('4ab0a03f-ba31-4458-bf95-6e268ae99af0', '240005', '该体验码只能购买指定商品，请核查后再使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('4b5d2c59-b001-439f-9b39-152030637cfd', '205009', '更新购物车商品包装信息失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('4bd6ef55-262e-4e36-8fa7-995200ca8399', '208103', '该账户名不存在', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('4c56df46-8059-406c-8e07-5730ceca1a9e', '210008', '退款已完成', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('51857555-c405-4739-ad92-910696ac1d24', '208104', '您输入的密码和账户名不匹配，请重新输入', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('57b12e4d-4130-4a01-ab35-5ca2d356ecd8', '212003', '订单状态不是待付款。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('590db662-1035-4b9c-85c8-5c002e679747', '212005', '无效的链接。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('5d232003-0991-40f0-b955-62a8cee30313', '205002', '用户未登录', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('5d3b33e4-e61a-4cb3-932b-a90e8d929ca4', '220004', 'F码不能重复使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('62fbdfb8-f7d2-42ca-881f-e137af8a248d', '240004', '不能使用体验码', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('69db4154-de80-4974-8c73-79f98be0e601', '208101', '用户名已经被注册', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('6bdcf310-e2cf-4d78-b171-1c4e6c831b82', '200001', 'Invalid request headers', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('6d6c4b2a-c03f-44c1-a66d-9ae3cd9d9531', '208124', '不存在输入idcard的账户', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('6ed2fea8-9836-43d4-b2f8-e6f83e56ede0', '270001', '签名验证不通过', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('70165dce-92d2-489c-a5af-efbbab08c78e', '281011', '趣分期兑换码已过期', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('720934d6-59f8-46d8-8a4a-28a9653e1353', '000003', 'API接口异常错误', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('74b681d9-b304-4f05-b3d3-75c87ea9959c', '000001', 'Unknown errors', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('7d2a5131-af7d-48a8-a78b-b4a194c5602f', '200008', '网络异常,请稍后重试', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('7e6783db-f61b-4841-94d7-5f5733f392b5', '270002', '不是小米订单无需处理', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('7ee682f9-54da-4363-88e0-7ada8c445e4b', '205010', '购物车信息已经发生变化，请确认订单是否已经提交，或重新刷新', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('805bb8d3-5bf7-44a3-855d-5966d0ddf0fa', '240002', '很遗憾，该体验码已过期', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('82e675b4-0c11-414d-8640-91ddead4f3b1', '000001', '系统未知错误', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8507a333-d404-4665-87ae-81553747c777', '000010', '已被抢光|商品太热门啦，瞬间就被抢光啦~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8a875a4d-80de-4158-b27c-8d3734847a69', '281008', '红包同步至小米失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8c4e3635-4434-45cd-ae45-85ae06634d7d', '205011', '消费积分密码输入错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8d656621-610c-4f10-aba6-a34fd1c93fbf', '208125', '授权失败，请稍后重试', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8e0f53f3-f830-43bc-9b21-429a21ca6c6f', '210006', '未找到对应的受理单', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('8f685f9d-de83-4bf3-b7d1-15b2eda60720', '240001', '体验码不存在，请重新输入', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('913a5960-37d2-48a3-a33b-35c8c74da786', '208122', '输入的验证码错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('91b8d2f4-3561-40b5-837c-47793a34a8c0', '210005', '订单不存在', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('92d5c8de-ce8a-4d68-8241-c63f1bd178ba', '208100', '请正确输入您的手机号码', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('95ec015a-1e7b-418f-9027-dbe556e22a75', '205012', '输入积分超出您所拥有积分', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('97a83455-a014-470d-a844-d7b638ade5e2', '212006', '订单金额不匹配。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('97b85c51-3e42-4825-aa05-d29e8f527ecb', '000008', '任务完成|小伙伴们太给力了，砍价任务已经完成啦~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('9a554aca-b136-4ac7-9413-bbf748c03646', '212001', '订单状态不一致。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('9be00150-2ebf-46a6-bc33-346bd71ccc85', '205018', '减库存失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a0e82d5a-1bc5-43e0-bfd9-08ee5358de7d', '205016', '订单支付金额异常', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a123a0db-d06b-4be3-a33b-e08777b64e7c', '206004', '输入的样例ID不是眼镜品类。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a1d7ba85-b365-428e-9d18-31be64aac13b', '281009', '红包和体验码不能同时使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a35204cb-f40f-4765-9d80-e777cea478e0', '240003', '体验码已被使用，不能重复使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a45a841d-3f1f-4704-b3f8-b8c4118cb9b7', '208128', '用户表绑定手机号或小米用户表绑定原有用户出错', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a53c9cfe-b7cb-4ffe-be0e-29cbe94b84b1', '200009', 'not have right', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a6b17f34-def9-4b4d-b422-0fe786311c78', '000013', '很不幸，您没有通过必要试用的资格审核。您可以拨打400-001-2543申诉，谢谢您的关注！', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a882eb92-e398-4d66-b872-5f7b87b19ec1', '000016', '抱歉|API网络异常~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('a8e0bac1-2877-46f4-af4c-4e8d2bf2a092', '208105', '密码格式错误', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('aa4ddfdf-45e3-40d6-8788-38a49ff3f898', '200000', 'Invalid token', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('abfa084b-517c-4d90-a392-380e5457d2e9', '000014', '调取接口，创建订单失败！', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('b26cee4a-0855-4450-b44a-aba5ccb122bc', '208116', '更改密码失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('b9bcf9ac-3929-4d3e-9c40-8e5a8a61da65', '281004', '红包已过期', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('bb491747-3c7a-4ce5-bdbc-de66d5716b3b', '205005', '添加商品至购物车前更新购物车操作状态失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('bcdf9fa5-1bea-45df-bd65-b8424fda29a4', '281006', '红包已作废', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('bec06964-c15e-4c7e-b070-105de698ff23', '210009', '退款单已关闭', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('c0b52782-9fda-4859-93ad-f752c40f3853', '000006', '已被抢光|商品太热门啦，瞬间就被抢光啦~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('c17a360a-4d78-4302-8016-f4319fb5d2dd', '100001', '抱歉，微信请求失败~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('c53d5bf3-3a33-41fb-aafa-3e7b9804b923', '281015', 'templateId为空', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('c66a966c-1561-413d-ae80-4d00cb6242b0', '205019', '增加库存失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('c72a7dd0-a3e1-4172-9898-74ea3cc65206', '250012', '插入数据失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('cce84c9f-1423-4fe3-b3fc-c0017f116dc5', '205017', '此商品部分材料库存不足', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('cf4e5872-35c5-4a15-a5cb-7e3f36fd8d21', '208118', '注册失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('d05d5b11-b794-42bf-a52a-42cb30cf33d7', '260002', '赔偿单必须是赔款失败状态', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('d2d545cb-276b-43b7-b32a-4b93026eb34a', '000015', '抱歉，当前试用活动已结束，去看看其他活动吧~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('d2d7e8b7-d649-4d82-b5c5-8e9562ad8f2c', '200010', '生成token失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('d4214a33-575a-447f-b346-4d508dfd1962', '100000', 'Failed to parse Json String', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('dac0a59e-1136-4f15-9af5-4aa802e8b1f3', '281001', '红包活动已过期', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('dc2c1eef-1ba4-4059-85c9-8ed28f579f07', '208102', '系统异常错误。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('de4eee77-3b5e-4a99-bc96-b69cbf28cd22', '260001', '赔偿单必须是未申请状态', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('def6f8fd-569f-479d-8b6b-69bc0427963b', '000009', '已被抢光|当前活动已结束，感谢参与~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('e209640c-3de9-4e68-bb53-e144318e19fe', '230001', '商品已被抢光。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('e561cf42-9d87-441d-bcad-c975d2486742', '212009', '汽车限量购校验未通过', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('e6c3e35b-9cec-459e-90e9-5a01129b5d9f', '210007', '商家同意退款，等待退款', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('e8befe32-68e2-43e2-96a9-1b6494c26f1c', '220003', 'F码已失效', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('eb71ac41-9cac-436b-9867-4d68e61a9109', '240008', '体验码已锁定', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('ec2a3cd4-375d-412d-b456-bd00b72ab12a', '205001', '当前用户不合法', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('ed5b7808-e5a2-4adc-b669-a800308e59e1', '205008', '更新购物车商品数量失败', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f010f479-00f0-4991-b091-3071086a71e7', '100002', '解析微信请求失败~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f1ce26cd-ea6b-4e77-9c65-1499fcb24e78', '000004', '您已砍过|您已经帮Ta砍过一次咯~', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f21bcb0b-f5c6-4611-9ade-5728909385bd', '000011', '试用单状态有误，操作失败！', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f238f75d-52a9-486b-baa2-7d70e31f5466', '250011', '该手机号已预约过该品类', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f6b3c360-4143-4393-8db0-905f21b85474', '212007', '订单金额小于等于0。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('f8637659-051f-4663-b1a9-c4c7f7e69961', '281002', '该用户已申请过此活动。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fa13cd7f-3dd9-4aba-af62-394814c943b0', '210004', '退款总金额必须大于0', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fa8be73f-8110-41aa-a5f3-4f1243ab68be', '240006', '该体验码只能购买指定商品，请核查后再使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fc547387-7858-4fcc-b0d1-6c9f6aeb3bb5', '000005', '很抱歉，您已参与试用活动，在此活动结束后您可再次参与其它活动！', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用ptapi', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fc56cbde-32fc-44d5-bd07-ca94f06e4c28', '281003', '红包已使用', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fd1efbe0-5d30-4a3e-813e-8cabcf5bc2ed', '200007', '处理过程中出现异常', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');
INSERT INTO `error` VALUES ('fd863656-3cd0-4dc7-b315-abb343bf850b', '212004', '订单已被支付。', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '2016-04-01 12:14:20', '1');

-- ----------------------------
-- Table structure for `interface`
-- ----------------------------
DROP TABLE IF EXISTS `interface`;
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
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of interface
-- ----------------------------
INSERT INTO `interface` VALUES ('2e4c0946-61d7-4a63-bb3f-4bf5b3279f4d', 'http://xxx/activityOrder/8/list.do', 'post and get', '[{\"name\":\"activity_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\"试用活动id\"},{\"name\":\"page\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"HEADER\",\"remark\":\"页码 如果为空，则返回第一页的数据\"},{\"name\":\"pageSize\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"HEADER\",\"remark\":\"每页显示的数量，如果pageSize为空，则不分页，一次选择所有数据\"},{\"name\":\"555\",\"necessary\":\"true\",\"type\":\"5\",\"parameterType\":\"HEADER\",\"remark\":\"555\"}]', '请求地址:http://xxx/activityOrder/8/list.do\r\n请求头:\r\n	activity_id=xxxx\r\n	page=xxxx\r\n	pageSize=xxxx\r\n	555=xxxx\r\n请求参数:\r\n', '[{\"name\":\"page\",\"type\":\"对象\",\"remark\":\"分页信息\"},{\"name\":\"testList\",\"type\":\"数组\",\"remark\":\"试用单列表\"},{\"name\":\"55\",\"type\":\"55\",\"remark\":\"555\"}]', '000001,', '{\n    \"data\": {\n        \"testList\": [\n            {\n                \"submitTime\": 1456803171000, \n                \"userId\": 1253817, \n                \"userImg\": \"\", \n                \"userName\": \"Ehsan\"\n            }, \n            {\n                \"submitTime\": 1456891539000, \n                \"userId\": 1253817, \n                \"userImg\": \"\", \n                \"userName\": \"Ehsan\"\n            }, \n            {\n                \"submitTime\": 1457170074000, \n                \"userId\": 1927601, \n                \"userImg\": \"\", \n                \"userName\": \"2927602\"\n            }, \n            {\n                \"submitTime\": 1457171696000, \n                \"userId\": 1772231, \n                \"userImg\": \"\", \n                \"userName\": \"snail-阿蒲\"\n            }, \n            {\n                \"submitTime\": 1457184651000, \n                \"userId\": 389, \n                \"userImg\": \"\", \n                \"userName\": \"路上有你\"\n            }, \n            {\n                \"submitTime\": 1457185381000, \n                \"userId\": 4352, \n                \"userImg\": \"\", \n                \"userName\": \"夜月星空\"\n            }, \n            {\n                \"submitTime\": 1457417579000, \n                \"userId\": 1772231, \n                \"userImg\": \"\", \n                \"userName\": \"snail-阿蒲\"\n            }, \n            {\n                \"submitTime\": 1457422208000, \n                \"userId\": 1927601, \n                \"userImg\": \"\", \n                \"userName\": \"EhsanTang\"\n            }, \n            {\n                \"submitTime\": 1458013122000, \n                \"userId\": 1927601, \n                \"userImg\": \"\", \n                \"userName\": \"EhsanTang\"\n            }\n        ], \n        \"testNum\": 9\n    }, \n    \"error\": null, \n    \"page\": {\n        \"allRow\": 15, \n        \"currentPage\": 1, \n        \"size\": 1000, \n        \"start\": 0, \n        \"totalPage\": 1\n    }, \n    \"success\": 1\n}', '{\n    \"data\": null, \n    \"error\": {\n        \"code\": \"000001\", \n        \"data\": null, \n        \"message\": \"xxxxxxx\"\n    }, \n    \"page\": null, \n    \"success\": 0\n}', '1', '063578aa-785f-42b8-bd1b-e3061ef45a2f', '查询某试用活动已经获得试用资格的试用单', '查看当某活动的试用单列表（已经砍价成功，并且已经提交了订单）', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 14:04:46', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('2e65d664-db69-48c2-a467-26b713aa5fef', 'http://xxx/activity/detail.do', 'post and get', '[{\"name\":\"activity_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\"试用活动id\"}]', '请求地址:http://xxx/activity/detail.do\r\n请求头:\r\n	activity_id=xxxx\r\n请求参数:\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"data\",\"type\":\"对象\",\"remark\":\"活动详情\"},{\"name\":\"error\",\"type\":\"对象\",\"remark\":\"错误信息\"}]', '000001,', '{\n    \"data\": {\n        \"actEndTime\": 1458208910000,\n        \"actId\": 24,\n        \"actStartTime\": 1456308107000,\n        \"actStatus\": 3,\n        \"actStockNum\": 10,\n        \"applyNum\": 128,\n        \"prodDetailPics\": [],\n        \"products\": {\n            \"durations\": 0,\n            \"img\": \"http://img.biyao.com/files/temp/b5/b54684f1e52226fa.png\",\n            \"imgList\": [\n                \"http://img.biyao.com/files/temp/be/be5d30d9c468e357.jpg\",\n                \"http://img.biyao.com/files/temp/68/687616eb0674b806.jpg\"\n            ],\n            \"price\": 114,\n            \"proDl\": \"<p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561293653289_0.jpg\\\" style=\\\"\\\" title=\\\"8.jpg\\\"/></p><p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561299403618_0.jpg\\\" style=\\\"\\\" title=\\\"6.jpg\\\"/></p><p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561292153203_0.jpg\\\" style=\\\"\\\" title=\\\"7.jpg\\\"/></p>\",\n            \"proId\": 1300155001,\n            \"proName\": \"试用-开发测试-001\",\n            \"shelfStatus\": 1,\n            \"sizeList\": [\n                {\n                    \"des\": \"红色\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"颜色\",\n                    \"spec_id\": 265,\n                    \"type\": 1\n                },\n                {\n                    \"des\": \"白色\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"颜色\",\n                    \"spec_id\": 267,\n                    \"type\": 1\n                },\n                {\n                    \"des\": \"黑色\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"颜色\",\n                    \"spec_id\": 269,\n                    \"type\": 1\n                },\n                {\n                    \"des\": \"35\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"尺码\",\n                    \"spec_id\": 266,\n                    \"type\": 1\n                },\n                {\n                    \"des\": \"36\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"尺码\",\n                    \"spec_id\": 268,\n                    \"type\": 1\n                },\n                {\n                    \"des\": \"37\",\n                    \"img_l\": \"\",\n                    \"img_s\": \"\",\n                    \"name\": \"尺码\",\n                    \"spec_id\": 270,\n                    \"type\": 1\n                }\n            ]\n        },\n        \"reportList\": [\n            {\n                \"repAuthor\": \"路上有你\",\n                \"repContent\": \"<p>323<br/></p>\",\n                \"repId\": 114,\n                \"repImg\": \"http://img.biyao.com/files/data0/bytry/5e8187b706314eb98d2d1be27a10e221.png\",\n                \"repTitle\": \"123\",\n                \"userImg\": \"http://img.biyao.com/files/data0/2015/01/25/14/avatar/big/ebdb6c79-52ce-4c7b-bd68-f71ea4bd9563.jpg\"\n            }\n        ],\n        \"supplier\": {\n            \"supId\": 15,\n            \"supLogo\": \"/data0/2015/01/30/15/storelogo/ed8bf934f9344db8.jpg\",\n            \"supName\": \"北京市回力鞋业有限公司\",\n            \"supPhone\": \"15600899477\"\n        },\n        \"tryNum\": 50,\n        \"verifyTime\": 1459418513000\n    },\n    \"error\": null,\n    \"page\": null,\n    \"success\": 1\n}', '{\n    \"data\": null,\n    \"error\": {\n        \"code\": \"000001\",\n        \"data\": null,\n        \"message\": \"活动不存在\"\n    },\n    \"page\": null,\n    \"success\": 0\n}', '1', 'bfdc49dd-4999-4ab4-a41a-1bda782d71fa', '查询活动详情', '根据活动id查询活动详情', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 14:04:46', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('31ea8a71-547d-4938-a2ec-c877e338845f', 'http://xxx/activity/3/list.do', 'post and get', '[{\"name\":\"page\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"PARAMETER\",\"remark\":\"页码 如果为空，则返回第一页的数据\"},{\"name\":\"pageSize\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"HEADER\",\"remark\":\"每页显示的数量，如果pageSize为空，则不分页，一次选择所有数据\"}]', '请求地址:http://xxx/activity/3/list.do\r\n请求头:\r\n	pageSize=xxxx\r\n请求参数:\r\n	page=xxxx\r\n', '[{\"name\":\"actList\",\"type\":\"数组\",\"remark\":\"活动列表\"},{\"name\":\"page\",\"type\":\"对象\",\"remark\":\"分页信息\"},{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"error\",\"type\":\"对象\",\"remark\":\"错误信息\"}]', '000001,', '{\n    \"data\": {\n        \"actList\": [\n            {\n                \"actEndTime\": 1458208910000,\n                \"actId\": 24,\n                \"actStartTime\": 1456308107000,\n                \"actStatus\": 3,\n                \"actStockNum\": 10,\n                \"applyNum\": 0,\n                \"prodDetailPics\": [],\n                \"products\": {\n                    \"durations\": 0,\n                    \"img\": \"http://img.biyao.com/files/temp/b5/b54684f1e52226fa.png\",\n                    \"imgList\": [\n                        \"http://img.biyao.com/files/temp/be/be5d30d9c468e357.jpg\",\n                        \"http://img.biyao.com/files/temp/68/687616eb0674b806.jpg\"\n                    ],\n                    \"price\": 114,\n                    \"proDl\": \"<p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561293653289_0.jpg\\\" style=\\\"\\\" title=\\\"8.jpg\\\"/></p><p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561299403618_0.jpg\\\" style=\\\"\\\" title=\\\"6.jpg\\\"/></p><p><img src=\\\"http://img.biyao.com/files/data0/2016/02/22/16/productiondetail/data/19216810095_20496_635917561292153203_0.jpg\\\" style=\\\"\\\" title=\\\"7.jpg\\\"/></p>\",\n                    \"proId\": 1300155001,\n                    \"proName\": \"试用-开发测试-001\",\n                    \"shelfStatus\": 1,\n                    \"sizeList\": []\n                },\n                \"reportList\": [],\n                \"supplier\": {\n                    \"supId\": 15,\n                    \"supLogo\": \"/data0/2015/01/30/15/storelogo/ed8bf934f9344db8.jpg\",\n                    \"supName\": \"北京市回力鞋业有限公司\",\n                    \"supPhone\": \"15600899477\"\n                },\n                \"tryNum\": 50,\n                \"verifyTime\": 1459418513000\n            },\n            {\n                \"actEndTime\": 1458230399000,\n                \"actId\": 88,\n                \"actStartTime\": 1458144000000,\n                \"actStatus\": 3,\n                \"actStockNum\": 0,\n                \"applyNum\": 0,\n                \"prodDetailPics\": [],\n                \"products\": {\n                    \"durations\": 33,\n                    \"img\": \"http://img.biyao.com/files/temp/2f/2fb2386fb977cda7.png\",\n                    \"imgList\": [],\n                    \"price\": 333,\n                    \"proDl\": \"\",\n                    \"proId\": 1300175043,\n                    \"proName\": \"20160317李毅002\",\n                    \"shelfStatus\": 1,\n                    \"sizeList\": []\n                },\n                \"reportList\": [],\n                \"supplier\": {\n                    \"supId\": 17,\n                    \"supLogo\": \"\",\n                    \"supName\": \"强人鞋业\",\n                    \"supPhone\": \"13621289567\"\n                },\n                \"tryNum\": 1,\n                \"verifyTime\": 1458230399000\n            },\n            {\n                \"actEndTime\": 1458230399000,\n                \"actId\": 87,\n                \"actStartTime\": 1458144000000,\n                \"actStatus\": 3,\n                \"actStockNum\": 9,\n                \"applyNum\": 0,\n                \"prodDetailPics\": [],\n                \"products\": {\n                    \"durations\": 33,\n                    \"img\": \"http://img.biyao.com/files/temp/df/dfcc5bea8abefc71.jpg\",\n                    \"imgList\": [],\n                    \"price\": 333,\n                    \"proDl\": \"\",\n                    \"proId\": 1300175042,\n                    \"proName\": \"20160317李毅001\",\n                    \"shelfStatus\": 1,\n                    \"sizeList\": []\n                },\n                \"reportList\": [],\n                \"supplier\": {\n                    \"supId\": 17,\n                    \"supLogo\": \"\",\n                    \"supName\": \"强人鞋业\",\n                    \"supPhone\": \"13621289567\"\n                },\n                \"tryNum\": 1,\n                \"verifyTime\": 1458230399000\n            }\n        ]\n    },\n    \"error\": null,\n    \"page\": {\n        \"allRow\": 50,\n        \"currentPage\": 1,\n        \"size\": 3,\n        \"start\": 0,\n        \"totalPage\": 17\n    },\n    \"success\": 1\n}', '{\n    \"data\": null,\n    \"error\": {\n        \"code\": \"000001\",\n        \"data\": null,\n        \"message\": \"Failed to convert value of type \'java.lang.String\' to required type \'java.lang.Integer\'; nested exception is java.lang.NumberFormatException: For input string: \\\"a\\\"\"\n    },\n    \"page\": null,\n    \"success\": 0\n}', '1', '563d4de0-5afd-4448-bf9e-a1a30b197167', '获取试用活动类表', '查询所有状态为3（已发布）的活动，按创建时间降序排列', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 14:04:46', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('65eb4f2c-4992-4939-8415-1a14056c4e3d', 'http://xxxx/activityReport/detail.do', 'post and get', '[{\"name\":\"activity_report _id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\"试用报告id\"}]', '请求地址:http://xxxx/activityReport/detail.do\r\n请求头:\r\n	activity_report _id=xxxx\r\n请求参数:\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"data\",\"type\":\"对象\",\"remark\":\"试用报告详情\"}]', '000001,', '{\n    \"data\":{\n        \"repAuthor\":\"路上有你\",\n        \"repContent\":\"<p>323<br/></p>\",\n        \"repId\":114,\n        \"repImg\":\"http://img.biyao.com/files/data0/bytry/5e8187b706314eb98d2d1be27a10e221.png\",\n        \"repTitle\":\"123\",\n        \"userImg\":\"http://img.biyao.com/files/data0/2015/01/25/14/avatar/big/ebdb6c79-52ce-4c7b-bd68-f71ea4bd9563.jpg\"\n    },\n    \"error\":null,\n    \"page\":null,\n    \"success\":1\n}\n', '{\n    \"data\":null,\n    \"error\":{\n        \"code\":\"000001\",\n        \"data\":null,\n        \"message\":\"xxxxx\"\n    },\n    \"page\":null,\n    \"success\":0\n}', '1', '62bde6e0-988b-475f-bfdf-76203455ec57', '试用报告详情', '根据试用报告id查询试用报告详情', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 06:56:00', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('71597b65-bffb-462d-9f0d-6898ff03c8b3', 'http://xxx/activity/productSizedetail.do', 'post and get', '[{\"name\":\"activity_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"PARAMETER\",\"remark\":\"试用活动id\"}]', '请求地址:http://xxx/activity/productSizedetail.do\r\n请求头:\r\n请求参数:\r\n	activity_id=xxxx\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"sizeDetail\",\"type\":\"对象\",\"remark\":\"规格、su、库存信息\"}]', '000001,', '{\n    \"success\": 1, \n    \"data\": {\n        \"sizeDetail\": [\n            {\n                \"specs\": [\n                    {\n                        \"goods_size\": \"红色\", \n                        \"name\": \"颜色\"\n                    }, \n                    {\n                        \"goods_size\": \"35\", \n                        \"name\": \"尺码\"\n                    }\n                ], \n                \"storeNum\": 19, \n                \"suId\": \"1300155001000001011\"\n            }, \n            {\n                \"specs\": [\n                    {\n                        \"goods_size\": \"白色\", \n                        \"name\": \"颜色\"\n                    }, \n                    {\n                        \"goods_size\": \"36\", \n                        \"name\": \"尺码\"\n                    }\n                ], \n                \"storeNum\": 12, \n                \"suId\": \"1300155001000002021\"\n            }, \n            {\n                \"specs\": [\n                    {\n                        \"goods_size\": \"黑色\", \n                        \"name\": \"颜色\"\n                    }, \n                    {\n                        \"goods_size\": \"37\", \n                        \"name\": \"尺码\"\n                    }\n                ], \n                \"storeNum\": 0, \n                \"suId\": \"1300155001000003031\"\n            }\n        ]\n    }\n}', '{\n    \"data\": null, \n    \"error\": {\n        \"code\": \"000001\", \n        \"data\": null, \n        \"message\": \"活动不存在\"\n    }, \n    \"page\": null, \n    \"success\": 0\n}', '1', 'cf9f682a-fd76-4ab5-9300-9210164ae2ea', '试用活动详情页获取sizeDetail（规格、su、库存信息）', '根据活动id查询活动对应的商品的规格信息及su、库存信息', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 14:04:46', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('86d6b9fb-f131-42a8-a462-842cb25c12a1', 'http://xxx/product/info/render.do', 'post and get', '[{\"name\":\"renderParams\",\"necessary\":\"true\",\"type\":\"String\",\"remark\":\"渲染参数\"}]', null, '[{\"name\":\"CannotChangeMaterials\",\"type\":\"String\",\"remark\":\"被关联的面与材料（不可修改的面+材料）\"},{\"name\":\"sudata\",\"type\":\"String\",\"remark\":\"商品信息\"},{\"name\":\"sizeList\",\"type\":\"String\",\"remark\":\"尺码列表\"},{\"name\":\"NotShowComponents\",\"type\":\"String\",\"remark\":\"不可定制面的集合\"}]', '200008,200009,200010,205001,205002,', '{\n	\"data\": {\n		\"CannotChangeMaterials\": [{  		被关联的面与材料（不可修改的面+材料）\n			\"mid\": 2706,					材料id\n			\"cid\": 23840 					面id\n		}],\n		\"sudata\": {						商品信息\n			\"suCode\": \"130036002900002hVo435ehPwUgKTxEvHpLww==\", su编码\n			\"creator\": 0,\n			\"shelfStatus\": 0,\n			\"supplierId\": 36, 				商家id\n			\"productId\": \"\",\n			\"modelId\": 1300360029,			模型组id\n			\"skuStock\": 1, 					是否可购买>0 可买\n			\"specMd5\": \"hVo435ehPwUgKTxEvHpLww\n==\",        								规格md5\n			\"suId\": \"1300360029000020040\", 	suid\n			\"suImgUrl\": \"\",\n			\"type\": 1,						1有模型 0无模型\n			\"duration\": 21, 				生产周期\n			\"specs\": \"[{\\\"goods_size\\\":\\\"42\n\\\",\\\"name\\\":\\\"选择尺码\\\"}]\", 				规格信息\n			\"createTime\": {\n				\"date\": 21,\n				\"hours\": 19,\n				\"seconds\": 57,\n				\"month\": 2,\n				\"timezoneOffset\": -480,\n				\"year\": 116,\n				\"minutes\": 46,\n				\"time\": 1458560817600,\n				\"day\": 1\n			},\n			\"specIds\": \"\",\n			\"price\": 359, 					价格\n			\"thumbnailPath\": \"temp\n/render_zs/result/1300360029/c31150a7bb8913ef_130036002900002_0_800_637/\", 缩略图\n			\"showImgIndex\": 0, 			图片角度\n			\"desId\": 130036002900002, 		desId\n			\"skuCode\": \"\",					sku编码\n			\"status\": 0,\n			\"suName\": \"VIZ真皮男鞋 英伦款118-2\" 商品名\n		},\n		\"RenderServer\": \"\",\n		\"inifilepath\": \"\",\n		\"MBPath\": \"\",\n		\"sizeList\": {\n			\"sizeDetail\": [{\n				\"duration\": 21, 			生产周期\n				\"specs\": [{\n					\"goods_size\": \"38\", 	规格维度\n					\"name\": \"选择尺码\" 	规格度量\n				}],\n				\"shelfStatus\": 1,\n				\"price\": 359,				价格\n				\"suId\": \"1300360029000020000\", suid\n				\"storeNum\": 1, 			>0表示可买\n				\"suName\": \"VIZ真皮男鞋 英伦款118-2\" 商品名\n			}],\n			\"sizeList\": [{\n				\"create_time\": {\n					\"date\": 21,\n					\"hours\": 19,\n					\"seconds\": 57,\n					\"month\": 2,\n					\"timezoneOffset\": -480,\n					\"year\": 116,\n					\"minutes\": 46,\n					\"time\": 1458560817606,\n					\"day\": 1\n				},\n				\"model_id\": 1300360029,	模型组id\n				\"sizeDesp\": \"\",\n				\"product_code\": \"\",\n				\"create_by\": \"\",\n				\"goods_size\": \"38\",			规格度量\n				\"unit\": \"\",\n				\"update_time\": {\n					\"date\": 21,\n					\"hours\": 19,\n					\"seconds\": 57,\n					\"month\": 2,\n					\"timezoneOffset\": -480,\n					\"year\": 116,\n					\"minutes\": 46,\n					\"time\": 1458560817606,\n					\"day\": 1\n				},\n				\"enable\": true,\n				\"store_num\": 0,\n				\"name\": \"选择尺码\",		规格维度\n				\"id\": 8739,\n				\"update_by\": \"\"\n			}],\n			\"sku\": 0\n		},\n		\"modelPath\": \"\",\n		\"NotShowComponents\": [23838],		不可修改的面\n		\"Perspectives\": [{\n			\"ComponentMasks\": [{\n				\"MaskCode\": 1, 			蒙版编号\n				\"CustomeName\": \"大底\",\n				\"ComponentName\": \"dadi\",\n				\"ComponentId\": 23838, 		面id\n				\"ModelId\": 1300360029 	模型id\n			}],\n			\"imageUrl\": \"http://img.biyao.com/files//temp/render_zs/result\n/1300360029/c31150a7bb8913ef_130036002900002_0_800_637//img_0_800.jpg\", 角度图\n			\"index\": 0						角度 共八个角度0~7\n		}],\n		\"ErrorCode\": 0,\n		\"ErrInfo\": \"\",\n		\"ShadowPath\": \"\"\n	},\n	\"success\": 1\n}', '{\n    \"success\": 1,\n    \"data\": [\n        {\n            \"suId\": \"1253\", 	suid\n            \"suCode\": \"157416\",	su编码\n            \"skuCode\": null, 	sku编码\n            \"modelId\": 12587, 	模型组id\n            \"desId\": 157416,	desid\n            \"skuStock\": 0, 		sku库存\n            \"price\": 99, 		价格\n            \"duration\": 10, 	生产周期\n            \"specMd5\": \"11FxOYiYfpMxmANj4kGJzg==\", 规格md5\n            \"specs\": \"\",		规格信息\n            \"specIds\": null,	规格id\n            \"shelfStatus\": 0,\n            \"suImgUrl\": \"http://img.biyao.com/files/temp/render/result/12587/ab67cb22d0716c70_157416_0_800/img_0_800.jpg\",						su图\n            \"thumbnailPath\": \"http://img.biyao.com/files/temp/render/result/12587/ab67cb22d0716c70_157416_0_800/img_0_800_200.jpg\",					su缩略图\n            \"suName\": \"眼镜20151209-001\",商品名\n            \"status\": 0,\n            \"createTime\": 1449661784027,\n            \"creator\": 0,\n            \"supplierId\": 15,	商家id\n            \"showImgIndex\": 0\n        }\n    ],\n\"error\": null\n}', '1', '189d073c-b4ea-4e50-a1c1-f66ec1da1dd4', '获取购物车列表数据集', '不可定制面是不需要在用户的定制面列表里面出现', '[{\"errorCode\":\"205002\",\"errorId\":\"5d232003-0991-40f0-b955-62a8cee30313\",\"errorMsg\":\"用户未登录\",\"moduleId\":\"9e6596c1-9fda-4cd2-b20a-1cce7e572a65\",\"moduleName\":\"API模块\"},{\"errorCode\":\"200008\",\"errorId\":\"7d2a5131-af7d-48a8-a78b-b4a194c5602f\",\"errorMsg\":\"网络异常,请稍后重试\",\"moduleId\":\"9e6596c1-9fda-4cd2-b20a-1cce7e572a65\",\"moduleName\":\"API模块\"},{\"errorCode\":\"200009\",\"errorId\":\"a53c9cfe-b7cb-4ffe-be0e-29cbe94b84b1\",\"errorMsg\":\"not have right\",\"moduleId\":\"9e6596c1-9fda-4cd2-b20a-1cce7e572a65\",\"moduleName\":\"API模块\"},{\"errorCode\":\"200010\",\"errorId\":\"d2d7e8b7-d649-4d82-b5c5-8e9562ad8f2c\",\"errorMsg\":\"生成token失败\",\"moduleId\":\"9e6596c1-9fda-4cd2-b20a-1cce7e572a65\",\"moduleName\":\"API模块\"},{\"errorCode\":\"205001\",\"errorId\":\"ec2a3cd4-375d-412d-b456-bd00b72ab12a\",\"errorMsg\":\"当前用户不合法\",\"moduleId\":\"9e6596c1-9fda-4cd2-b20a-1cce7e572a65\",\"moduleName\":\"API模块\"}]', 'userName：super | trueName：超级管理员', '2016-04-03 18:34:13', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('955db6b9-c42c-4e84-8a16-891d0944e4da', 'http://xxx/product/getSuId.do', 'post and get', '[{\"name\":\"spec_ids\",\"necessary\":\"true\",\"type\":\"String\",\"parameterType\":\"HEADER\",\"remark\":\"规格id集合：{“1”,”2”}\"},{\"name\":\"product_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"undefined\",\"remark\":\"商品id\"}]', '请求地址:http://xxx/product/getSuId.do\r\n请求头:\r\n	spec_ids=xxxx\r\n请求参数:\r\n	product_id=xxxx\r\n', '[{\"name\":\"succeed\",\"type\":\"int\",\"remark\":\"是否成功:1成功，0失败\"}]', '000001,', '{\n	succeed:1,//是否成功\n	data{\n             suId,//售卖单元Id\n       }\n}', '{\n    \"data\": null,\n    \"error\": {\n        \"code\": \"000001\",\n        \"data\": null,\n        \"message\": \"xxxx\"\n    },\n    \"page\": null,\n    \"success\": 0\n}', '0', 'f86d375e-d1db-4eeb-84fa-9e9def04fc6d', '根据规格、商品id查询SU', '主键按照排序spec_order排序\n通过产品product_id和spec_md5从SU表查询唯一的SuId', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 14:04:46', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('a7c9ea74-e9cc-4ea4-b555-c0ff9cc9a816', 'http://xxx/activityReport/4/list.do', 'post and get', '[{\"name\":\"activity_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\" 活动id\"},{\"name\":\"page\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"PARAMETER\",\"remark\":\"页码 如果为空，则返回第一页的数据\"},{\"name\":\"pageSize\",\"necessary\":\"false\",\"type\":\"int\",\"parameterType\":\"HEADER\",\"remark\":\"每页显示的数量，如果pageSize为空，则不分页，一次选择所有数据\"}]', '请求地址:http://xxx/activityReport/4/list.do\r\n请求头:\r\n	activity_id=xxxx\r\n	pageSize=xxxx\r\n请求参数:\r\n	page=xxxx\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"reportList\",\"type\":\"数组\",\"remark\":\"试用报告列表\"}]', '000001,', '{\n    \"data\":{\n        \"reportList\":[\n            {\n                \"repAuthor\":\"路上有你\",\n                \"repContent\":\"<p>323<br/></p>\",\n                \"repId\":114,\n                \"repImg\":\"http://img.biyao.com/files/data0/bytry/5e8187b706314eb98d2d1be27a10e221.png\",\n                \"repTitle\":\"123\",\n                \"userImg\":\"http://img.biyao.com/files/data0/2015/01/25/14/avatar/big/ebdb6c79-52ce-4c7b-bd68-f71ea4bd9563.jpg\"\n            }\n        ]\n    },\n    \"error\":null,\n    \"page\":{\n        \"allRow\":1,\n        \"currentPage\":1,\n        \"size\":1000,\n        \"start\":0,\n        \"totalPage\":1\n    },\n    \"success\":1\n}\n', '{\n    \"data\":null,\n    \"error\":{\n        \"code\":\"000001\",\n        \"data\":null,\n        \"message\":\"xxx\"\n    },\n    \"page\":null,\n    \"success\":0\n}', '1', '62bde6e0-988b-475f-bfdf-76203455ec57', '查询某试用活动的试用报告', '根据活动id查询通过审核的试用报告列表', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 06:57:00', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('ab7e215e-abab-4b43-a7c3-9bc5a04224e4', 'http://xxx/activityOrder/add.do', 'post and get', '[{\"name\":\"activity _id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\"活动id\"},{\"name\":\"nickname\",\"necessary\":\"true\",\"type\":\"String\",\"parameterType\":\"HEADER\",\"remark\":\"用户昵称\"},{\"name\":\"user_img\",\"necessary\":\"true\",\"type\":\"String\",\"parameterType\":\"PARAMETER\",\"remark\":\"用户头像\"},{\"name\":\"su_id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"PARAMETER\",\"remark\":\"基本售卖单元ID\"},{\"name\":\"uid\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"PARAMETER\",\"remark\":\"请求头信息：用户id\"}]', '请求地址:http://xxx/activityOrder/add.do\r\n请求头:\r\n	activity _id=xxxx\r\n	nickname=xxxx\r\n请求参数:\r\n	user_img=xxxx\r\n	su_id=xxxx\r\n	uid=xxxx\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"}]', '000001,000005,000006,000007,000013,', '{\n    \"data\":null,\n    \"error\":null,\n    \"success\":1\n}\n', '{\n    \"data\":null,\n    \"error\":{\n        \"code\":\"000001\",\n        \"data\":null,\n        \"message\":\"xxxxxxx\"\n    },\n    \"success\":0\n}\n', '1', '62bde6e0-988b-475f-bfdf-76203455ec57', '添加试用单', '查询该用户是否在黑名单之中STATUS=0，查询活动是否未开始，是否已经结束，查询活动库存是否大于0，判断该用户是否已经参与过该活动', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000007\",\"errorId\":\"0173ac20-bb4d-4704-8a85-1077dc9b84d0\",\"errorMsg\":\"活动尚未开始，申请失败！\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1},{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1},{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000013\",\"errorId\":\"a6b17f34-def9-4b4d-b422-0fe786311c78\",\"errorMsg\":\"很不幸，您没有通过必要试用的资格审核。您可以拨打400-001-2543申诉，谢谢您的关注！\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1},{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000006\",\"errorId\":\"c0b52782-9fda-4859-93ad-f752c40f3853\",\"errorMsg\":\"已被抢光|商品太热门啦，瞬间就被抢光啦~\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1},{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000005\",\"errorId\":\"fc547387-7858-4fcc-b0d1-6c9f6aeb3bb5\",\"errorMsg\":\"很抱歉，您已参与试用活动，在此活动结束后您可再次参与其它活动！\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 06:57:00', '2016-04-01 12:12:51');
INSERT INTO `interface` VALUES ('c10427b4-0d88-4af2-a79c-551a798b8d4c', 'http://baidu.com', 'post and get', '[{\"name\":\"activity_report _id\",\"necessary\":\"true\",\"type\":\"long\",\"parameterType\":\"HEADER\",\"remark\":\"试用报告id\"}]', '请求地址:http://xxxx/activityReport/detail.do\r\n请求头:\r\n	activity_report _id=xxxx\r\n请求参数:\r\n', '[{\"name\":\"success\",\"type\":\"int\",\"remark\":\"是否成功：1成功，0失败\"},{\"name\":\"data\",\"type\":\"对象\",\"remark\":\"试用报告详情\"}]', '000001,', '{\n    \"data\":{\n        \"repAuthor\":\"路上有你\",\n        \"repContent\":\"<p>323<br/></p>\",\n        \"repId\":114,\n        \"repImg\":\"http://img.biyao.com/files/data0/bytry/5e8187b706314eb98d2d1be27a10e221.png\",\n        \"repTitle\":\"123\",\n        \"userImg\":\"http://img.biyao.com/files/data0/2015/01/25/14/avatar/big/ebdb6c79-52ce-4c7b-bd68-f71ea4bd9563.jpg\"\n    },\n    \"error\":null,\n    \"page\":null,\n    \"success\":1\n}\n', '{\n    \"data\":null,\n    \"error\":{\n        \"code\":\"000001\",\n        \"data\":null,\n        \"message\":\"xxxxx\"\n    },\n    \"page\":null,\n    \"success\":0\n}', '1', 'cf9f682a-fd76-4ab5-9300-9210164ae2ea', '试用报告详情-【拷贝】', '根据试用报告id查询试用报告详情', '[{\"createTime\":\"2016-04-01 12:14:20.0\",\"errorCode\":\"000001\",\"errorId\":\"82e675b4-0c11-414d-8640-91ddead4f3b1\",\"errorMsg\":\"系统未知错误\",\"moduleId\":\"62bde6e0-988b-475f-bfdf-76203455ec57\",\"moduleName\":\"试用ptapi\",\"status\":1}]', 'userName：super | trueName：测试用户', '2016-04-04 06:56:00', '2016-04-06 17:21:42');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
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

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('097bde20-9ece-4033-8f81-118e962f791b', '模块&接口列表', 'index.do#/interface/list/0/无', '', '4a8eee1c-4863-41f3-9ba8-d11c255de46f', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('0a2e84c9-f097-409d-a568-3f4fcb8d7575', '错误码', '', '0,', '0', '<i class=\"iconfont\">&#xe60c;</i>', 'FRONT', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('0c560ac0-1124-4ad9-95ec-49f0bf7d94f7', 'API', 'web.do#/webInterface/list/9e6596c1-9fda-4cd2-b20a-1cce7e572a65/API模块', '4444', '1b35d3a5-57fc-4fc7-b458-918523c33042', '444', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('17a0bc3b-7b61-44e6-810d-f86acf37f1d8', '错误码列表', 'web.do#/webError/list/9e6596c1-9fda-4cd2-b20a-1cce7e572a65', '', '0a2e84c9-f097-409d-a568-3f4fcb8d7575', '', null, '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('1b35d3a5-57fc-4fc7-b458-918523c33042', '接口列表', '', '0,', '0', '<i class=\"iconfont\">&#xe614;</i>', 'FRONT', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('1cd9e13b-f431-4c18-abf4-e44e43b99410', '样例项目', 'web.do#/webInterface/list/62bde6e0-988b-475f-bfdf-76203455ec57/样例项目-访问密码：123-请勿修改', '1', '1b35d3a5-57fc-4fc7-b458-918523c33042', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('241f31ab-3dcb-4290-a485-644540d38433', '菜单列表', 'index.do#/menu/list/0/无', '', '5358c830-7a24-46d8-924e-ff47f3e8fb01', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0', '用户管理', '', 'b304b5c2-9186-4bd2-851f-2979e03b8f83,', '0', '<i class=\"iconfont\">&#xe603;</i>', 'BACK', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('2f107703-3e03-440c-8297-5ccccae8f8da', '', '', '', '', '', null, '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('3384cd9c-ff39-41b4-b5fc-84044fad6d30', '角色管理', '', 'b304b5c2-9186-4bd2-851f-2979e03b8f83,', '0', '<i class=\"iconfont\">&#xe612;</i>', 'BACK', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('4a8eee1c-4863-41f3-9ba8-d11c255de46f', '项目&模块&接口管理', '', 'bc1bbac0-68a4-4063-a216-c57c6da47c0d,', '0', '<i class=\"iconfont\">&#xe614;</i>', 'BACK', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('5358c830-7a24-46d8-924e-ff47f3e8fb01', '菜单管理', '', '', '0', '<i class=\"iconfont\">&#xe60f;</i>', 'BACK', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('5891d916-a05d-4864-8c05-f8a844e71dd9', '系统设置', '', '', '0', '<i class=\"iconfont\">&#xe61a;</i>', 'BACK', '2016-04-04 06:09:55', '0');
INSERT INTO `menu` VALUES ('5aa11f29-7acc-4f93-9b51-6bf47d654007', '22222222', '22222222222', '2', '1', null, null, '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('87534e09-1636-45fd-92a2-b5b90f5da58b', '数据字典管理', '', '', '0', '<i class=\"iconfont\">&#xe61c;</i>', 'BACK', '2016-04-05 22:53:33', '0');
INSERT INTO `menu` VALUES ('885df3f1-3476-4c87-b656-885bea1802f8', '菜单3', '1111111111', '2', '1', null, null, '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('8c868bf7-e330-40bf-a377-5c3f8f5c1fd7', '1111', '1333', '0', '1', null, null, '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('8d500f70-ea2a-46b8-a7a7-7013cb59e423', '数据字典列表', 'index.do#/webPage/list/DICTIONARY', '', '87534e09-1636-45fd-92a2-b5b90f5da58b', '', '', '2016-04-05 22:53:53', '0');
INSERT INTO `menu` VALUES ('932c923a-aea3-44a2-ba03-0a97cffbc09a', '角色列表', 'index.do#/role/list', '', '3384cd9c-ff39-41b4-b5fc-84044fad6d30', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('9bb50876-abf1-46ec-8018-2ffe2510c83b', '系统设置列表', 'index.do#/setting/list', '', '5891d916-a05d-4864-8c05-f8a844e71dd9', '', '', '2016-04-04 06:35:17', '0');
INSERT INTO `menu` VALUES ('a05ba495-e9b9-4433-92f2-d71f92bdac6a', '数据字典列表', 'web.do#/webWebPage/list/DICTIONARY', '', 'f8764c78-a281-4133-b19f-109123a49a5f', '', '', '2016-04-05 22:54:43', '0');
INSERT INTO `menu` VALUES ('c58fe99e-4050-477c-b60b-3513375e1996', '用户列表', 'index.do#/user/list', '', '2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('de6f4d42-bf2e-4e1b-a0b8-a706cf24addb', '错误码管理', '', 'bc1bbac0-68a4-4063-a216-c57c6da47c0d,', '0', '<i class=\"iconfont\">&#xe608;</i>', 'BACK', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('ded3b50d-1591-41e7-b642-14bf08bb740b', '错误码列表', 'index.do#/error/list', 'c62a80c5-6064-4815-ac59-e507a8e22a6f,', 'de6f4d42-bf2e-4e1b-a0b8-a706cf24addb', '', '', '2016-04-01 12:19:34', '1');
INSERT INTO `menu` VALUES ('f8764c78-a281-4133-b19f-109123a49a5f', '数据字典', '', '', '0', '<i class=\"iconfont\">&#xe61c;</i>', 'FRONT', '2016-04-05 22:54:12', '0');
INSERT INTO `menu` VALUES ('fc0e431a-c964-4a5f-b49e-10f13ae56b10', 'AppApi项目', 'web.do#/webInterface/list/e634d522-aad8-4cdb-9c13-105ee7b17eab/AppApi模块', '11', '1b35d3a5-57fc-4fc7-b458-918523c33042', '', '', '2016-04-01 12:19:34', '1');

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `moduleId` varchar(50) NOT NULL COMMENT '所属模块ID',
  `moduleName` varchar(100) NOT NULL COMMENT '所属模块名称',
  `parentId` varchar(50) DEFAULT NULL COMMENT '父级节点ID',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `password` varchar(20) DEFAULT NULL COMMENT '访问密码',
  PRIMARY KEY (`moduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('063578aa-785f-42b8-bd1b-e3061ef45a2f', '订单模块', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('189d073c-b4ea-4e50-a1c1-f66ec1da1dd4', '购物车模块', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('563d4de0-5afd-4448-bf9e-a1a30b197167', '用户模块', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('62bde6e0-988b-475f-bfdf-76203455ec57', '样例项目-访问密码：123-请勿修改', '0', '2016-04-01 12:21:03', '1', '123');
INSERT INTO `module` VALUES ('6462bd9d-db3f-48dd-abee-03bab9faa806', 'aaa', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65', '2016-04-05 12:24:35', '0', 'aaa');
INSERT INTO `module` VALUES ('9e6596c1-9fda-4cd2-b20a-1cce7e572a65', 'API模块', '0', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('bfdc49dd-4999-4ab4-a41a-1bda782d71fa', '短信模块', 'e634d522-aad8-4cdb-9c13-105ee7b17eab', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('cf9f682a-fd76-4ab5-9300-9210164ae2ea', '订单模块', 'e634d522-aad8-4cdb-9c13-105ee7b17eab', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('e634d522-aad8-4cdb-9c13-105ee7b17eab', 'AppApi模块', '0', '2016-04-01 12:21:03', '1', null);
INSERT INTO `module` VALUES ('f86d375e-d1db-4eeb-84fa-9e9def04fc6d', '购物车模块', 'e634d522-aad8-4cdb-9c13-105ee7b17eab', '2016-04-01 12:21:03', '1', null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` varchar(50) NOT NULL COMMENT '角色ID',
  `roleName` varchar(50) NOT NULL COMMENT '角色名称',
  `auth` text NOT NULL,
  `authName` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('ee479a4b-f445-4130-a295-572e6126ec96', 'test', 'MODULE_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,MODULE_e634d522-aad8-4cdb-9c13-105ee7b17eab,INTERFACE_62bde6e0-988b-475f-bfdf-76203455ec57,INTERFACE_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,INTERFACE_e634d522-aad8-4cdb-9c13-105ee7b17eab,ERROR_62bde6e0-988b-475f-bfdf-76203455ec57,ERROR_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,ERROR_e634d522-aad8-4cdb-9c13-105ee7b17eab,MENU,SETTING,DICTIONARY_9e6596c1-9fda-4cd2-b20a-1cce7e572a65,DICTIONARY_e634d522-aad8-4cdb-9c13-105ee7b17eab,87534e09-1636-45fd-92a2-b5b90f5da58b,5891d916-a05d-4864-8c05-f8a844e71dd9,2b2292d5-0fb0-4d63-97cc-ce3a48d83fb0,3384cd9c-ff39-41b4-b5fc-84044fad6d30,4a8eee1c-4863-41f3-9ba8-d11c255de46f,5358c830-7a24-46d8-924e-ff47f3e8fb01,de6f4d42-bf2e-4e1b-a0b8-a706cf24addb,', 'API模块【模块】,AppApi模块【模块】,样例项目访问密码：123请勿修改【接口】,API模块【接口】,AppApi模块【接口】,样例项目访问密码：123请勿修改【错误码】,API模块【错误码】,AppApi模块【错误码】,菜单管理,系统设置管理,API模块,AppApi模块,数据字典管理【菜单】,系统设置【菜单】,用户管理【菜单】,角色管理【菜单】,项目&模块&接口管理【菜单】,菜单管理【菜单】,错误码管理【菜单】,', '2016-04-01 12:22:07', '1');

-- ----------------------------
-- Table structure for `setting`
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
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

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES ('062f01ae-e50b-4dd3-808b-b4a6d65eeadc', 'LOGO', 'http://api2.crap.cn/resources/upload/images/2016-04-04/064741q2A4JA.png', '网站主logo，可以直接在value中填写绝对链接地址，也可以自行上传图片', '2016-04-01 12:23:18', '1', 'IMAGE');
INSERT INTO `setting` VALUES ('56ed7e06-38dd-45d1-aa32-013e7cb77fd5', 'ADMINHELP', '帮助中心', '<blockquote style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><p>   感谢使用CrapApi应用接口管理系统！</p></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\">常见问题：</p><ol style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">什么是【游客图形验证码】？如果开启了【游客图形验证码】，则游客在访问私密模块时不仅需要输入访问密码，而且需要输入【游客图形验证码】，开启【游客图形验证码】能有效防止暴力破解模块访问密码。<br/></li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">什么是【模块密码】？【模块密码】对后台管理没有任何影响，若为模块添加了密码，当游客访问该模块时需要输入访问密码才能浏览，【模块密码】只在当前模块有效，即子模块不能继承父模块的密码。故建议将【模块密码】设置在需要控制访问的接口所属的直接模块上。若多个模块密码一致，游客输入密码后无需再次输入，直接就可以访问。如没有开启游客图形验证码，则关闭浏览器后密码将自动失效，如开启了图形验证码，则用户刷新站点或关闭浏览器后密码将自动失效。</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">为什么游客输入过访问密码后，再次访问依然需要再次输入访问密码？（1）系统只保留用户最后一次输入的访问密码，如果多个模块需要访问密码，则当用户交叉访问时，后一个密码将覆盖前一个密码，因此需要再次输入访问密码。（2）CrapApi为单页应用，即站点在第一次打开后，以后所有点击都是局部刷新，如果系统开启了【游客图形验证码】，则当用户手动刷新页面，【游客图形验证码】将失效，因此需要再次输入访问密码。</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">显示样式有问题？所有测试均使用Chrome浏览器，请优先使用Chrome、Firefox、Safari浏览器访问</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">GitHub地址：https://github.com/EhsanTang/CrapApi</li><li style=\"color: rgb(85, 85, 85);background-color: rgb(255, 255, 255);\">QQ技术交流群：254450938<br/></li></ol>', '2016-04-03 18:12:08', '0', 'RICHTEXT');
INSERT INTO `setting` VALUES ('58b20840-cee2-43ac-a6dd-94d7f1656318', 'HELP', '帮助中心', '<blockquote style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><p>   感谢使用CrapApi应用接口管理系统！</p></blockquote><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><br/></p><p style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\">常见问题：</p><ol style=\"color: rgb(85, 85, 85);text-align: left;background-color: rgb(255, 255, 255);\"><li>私有接口访问密码：123</li><li>显示样式有问题：所有测试均使用Chrome浏览器，请优先使用Chrome、Firefox、Safari浏览器访问</li><li>GitHub地址：https://github.com/EhsanTang/CrapApi</li><li>QQ技术交流群：254450938<br/></li></ol>', '2016-04-03 18:12:34', '0', 'RICHTEXT');
INSERT INTO `setting` VALUES ('8a95bc2f-ea61-4dd6-8163-d9c520b28181', 'VISITCODE', 'false', '游客访问私密模块输入密码的同时是否需要输入图像验证码？true为需要，其他为不需要', '2016-04-03 18:07:14', '0', 'TEXT');
INSERT INTO `setting` VALUES ('98ecca1b-f762-4cd3-831a-4042b36419d8', 'VERIFICATIONCODE', 'true', '是否开启安全登录？ture为开启，其他为不开启，开启后登录将需要输入图片验证码', '2016-04-03 18:07:58', '0', 'TEXT');
INSERT INTO `setting` VALUES ('b97a3a75-c1c3-42cc-b944-8fb5ac5c5f49', 'SECRETKEY', 'crapApiKey', '秘钥，用于cookie加密等', '2016-04-03 01:04:40', '0', 'TEXT');
INSERT INTO `setting` VALUES ('de94c622-02fc-4b39-9cc5-0c24870ac21f', 'TITLE', 'CrapApi|Api接口管理系统', '站点标题', '2016-04-02 19:09:13', '1', 'TEXT');
INSERT INTO `setting` VALUES ('e0dec957-5043-4c6e-9225-960fbc401116', 'ICON', 'http://api2.crap.cn/resources/upload/images/2016-04-04/063633hG35aC.ico', '站点ICON图标（浏览器标题栏图标）', '2016-04-02 19:49:41', '1', 'IMAGE');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
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

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('206a1218-5c81-48af-b8e4-25864ad5e929', 'super', 'e10adc3949ba59abbe56e057f20f883e', '测试用户', 'ee479a4b-f445-4130-a295-572e6126ec96,', 'test,', '', '', '2016-04-01 12:24:00', '1');
INSERT INTO `user` VALUES ('6e8af9da-ed79-4342-9eca-591ccb4ea658', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 'super,', '超级管理员,', '', '', '2016-04-01 12:24:00', '1');

-- ----------------------------
-- Table structure for `webpage`
-- ----------------------------
DROP TABLE IF EXISTS `webpage`;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of webpage
-- ----------------------------
INSERT INTO `webpage` VALUES ('07bdc59d-e407-4ef0-8ff7-bf86449e7efd', '用户表，存储用户信息', '样例表：用户表，存储用户信息用户表，存储用户信息用户表，存储用户信息', '[{\"name\":\"id\",\"type\":\"int\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"主键，自增\"},{\"name\":\"userName\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户名\"},{\"name\":\"password\",\"type\":\"varchar(50)\",\"notNull\":\"false\",\"def\":\"\",\"remark\":\"用户密码\"},{\"name\":\"email\",\"type\":\"varchar(20)\",\"notNull\":\"true\",\"def\":\"\",\"remark\":\"邮箱\"},{\"name\":\"gender\",\"type\":\"varchar(2)\",\"notNull\":\"false\",\"def\":\"男\",\"remark\":\"性别\"}]', '0', 'DICTIONARY', '0', '2016-04-05 22:52:41', '62bde6e0-988b-475f-bfdf-76203455ec57');
INSERT INTO `webpage` VALUES ('8b4f67de-04ba-4a1a-b7c3-e3d5cd74e45b', '测试', '测试简介，测试简介，测试简介，测试简介测试简介测试简介测试简介测试简介测试简介测试简介', '[{\"name\":\"field1\",\"type\":\"int\",\"notNull\":\"true\",\"def\":\"无\",\"remark\":\"字段1\"},{\"name\":\"field2\",\"type\":\"varchar(20)\",\"notNull\":\"false\",\"def\":\"默认值\",\"remark\":\"字段2\"},{\"name\":\"field3\",\"type\":\"int\",\"notNull\":\"true\",\"def\":\"默认值22\",\"remark\":\"字段3\"}]', '0', 'DICTIONARY', '0', '2016-04-05 22:56:51', '9e6596c1-9fda-4cd2-b20a-1cce7e572a65');
