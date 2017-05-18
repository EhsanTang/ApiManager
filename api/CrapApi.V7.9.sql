use api;

ALTER TABLE `api`.`project` 
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

