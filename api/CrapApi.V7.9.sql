use api;

ALTER TABLE `project` 
ADD COLUMN `luceneSearch` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '是否允许建立Lucene搜索' AFTER `cover`;


ALTER TABLE `project` 
CHANGE COLUMN `status` `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '2：推荐项目，3，管理员管理项目，4，管理管理&推荐项目，-1：默认debug项目' ;


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
  PRIMARY KEY (`id`),
  KEY `index_status_seq_createTime` (`status`,`sequence`,`createTime`),
  KEY `index_userId_seq_createTime` (`interfaceId`,`sequence`,`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `debug` 
ADD COLUMN `version` INT NOT NULL DEFAULT 0 AFTER `paramType`;

ALTER TABLE `debug` 
ADD COLUMN `uid` VARCHAR(50) NOT NULL DEFAULT '-1' AFTER `version`;

ALTER TABLE `module` 
ADD COLUMN `version` INT NOT NULL DEFAULT 0 AFTER `templateId`;

INSERT INTO `setting` (`id`, `mkey`, `value`, `remark`, `type`, `canDelete`, `sequence`) VALUES ('fpmbdc8b-9cd8-4839-b38a-898343435462', 'INDEX_PAGE', 'index.do#/web/article/list/web/PAGE/%E6%8A%80%E6%9C%AF%E6%96%87%E6%A1%A3/NULL', '前端首页：只能以index.do、font/ 开头的url', 'INDEXPAGE', '0', '66');

ALTER TABLE `user` 
CHANGE COLUMN `loginType` `loginType` INT(11) NOT NULL DEFAULT '0' COMMENT '0：账号登陆，1：github登陆，2：码云' ;

update `user` set loginType=1 where thirdlyId like 'gitHub%';
INSERT INTO `setting` (`id`, `mkey`, `value`, `remark`, `type`, `canDelete`, `sequence`) VALUES ('fff-1111-d4839-b38a-898343435462', 'ANONYMOUS_COMMENT', 'true', '是否允许匿名评论, true:允许', 'TEXT', '0', '100');

ALTER TABLE `comment` 
ADD COLUMN `avatarUrl` VARCHAR(500) NOT NULL DEFAULT 'resources/avatar/avatar0.jpg' COMMENT '用户头像 ' AFTER `userName`;


ALTER TABLE `comment` 
ADD COLUMN `userName` VARCHAR(50) NOT NULL COMMENT '匿名' AFTER `avatarUrl`;

update comment set userName='匿名' where userName='' or userName is null;

ALTER TABLE user`
ADD COLUMN `passwordSalt` VARCHAR(20) NULL COMMENT '密码MD5盐' AFTER `thirdlyId`;


ALTER TABLE `apidev`.`setting`
CHANGE COLUMN `remark` `remark` VARCHAR(500) NULL DEFAULT NULL ;

--- 未执行，线上
ALTER TABLE `apidev`.`error`
CHANGE COLUMN `moduleId` `projectId` VARCHAR(50) NOT NULL ;

ALTER TABLE `apidev`.`setting`
ADD COLUMN `show` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '是否在前端显示，1：是，0：否' AFTER `sequence`;

ALTER TABLE `apidev`.`article`
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `markdown`;

ALTER TABLE `apidev`.`interface`
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `isTemplate`;

ALTER TABLE `apidev`.`source`
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `filePath`;

CREATE TABLE `hot_search` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '搜索次数',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT '2015-12-31 08:00:00',
  `keyword` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '搜索关键字',
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyword_UNIQUE` (`keyword`),
  KEY `index_times` (`times`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `apidev`.`module`
ADD COLUMN `category` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '文章分类，多个分类以逗号分割，每个分类最多10个字';


