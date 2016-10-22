use apitest;

CREATE TABLE `project` (
  `id` varchar(50) NOT NULL COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `userId` varchar(50) NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:私有项目，2公开项目，3公开推荐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `datacenter` 
RENAME TO  `module` ;

ALTER TABLE `module` 
DROP COLUMN `type`,
DROP INDEX `TYPE` ;

ALTER TABLE `source` 
CHANGE COLUMN `directoryId` `moduleId` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '模块ID' ;

ALTER TABLE `webpage` 
RENAME TO  `article` ;

UPDATE `module` SET `id`='web',`projectId`='web',`name`='站点默认模块', `parentId`='web' WHERE `id`='0';

INSERT INTO `project` (`id`, `name`, `createTime`, `status`, `sequence`, `remark`, `userId`, `type`) VALUES ('web', '站点默认项目', '2016-10-04 23:02:10', '0', '1', '站点默认项目，请勿删除', 'admin', '1');

UPDATE `article` SET `moduleId`='web' WHERE `moduleId`='top';

ALTER TABLE `module` 
DROP COLUMN `parentId`;

ALTER TABLE `project` 
ADD COLUMN `password` VARCHAR(45) NULL AFTER `type`;

ALTER TABLE `article` 
DROP COLUMN `password`;

ALTER TABLE `comment` 
CHANGE COLUMN `webpageId` `articleId` VARCHAR(50) NOT NULL ;

delete from menu where  type='BACK';

ALTER TABLE `project` 
CHANGE COLUMN `status` `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '2：推荐项目，3，管理员管理项目，4，管理管理&推荐项目' ;

DELETE FROM `module` WHERE `id`='privateModule';
DELETE FROM `module` WHERE `id`='top';
