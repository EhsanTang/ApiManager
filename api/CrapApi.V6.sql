ALTER TABLE `api`.`interface` 
CHANGE COLUMN `url` `url` VARCHAR(200) NOT NULL COMMENT 'api链接' ;

ALTER TABLE `api`.`datacenter` 
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `userId`;

ALTER TABLE `api`.`interface` 
DROP INDEX `url` ;

ALTER TABLE `api`.`interface` 
ADD COLUMN `fullUrl` VARCHAR(255) NOT NULL DEFAULT '' AFTER `header`;

ALTER TABLE `api`.`comment` 
ADD COLUMN `reply` VARCHAR(200) NOT NULL DEFAULT '' AFTER `sequence`;

ALTER TABLE `api`.`comment` 
ADD COLUMN `updateTime` TIMESTAMP NOT NULL DEFAULT '2016-01-01 01:40:00' AFTER `reply`;

update `api`.`datacenter` set parentId='privateModule' where id=parentId and parentId !='top'

ALTER TABLE `api`.`interface` 
ADD INDEX `Index_fullUrl` (`fullUrl` ASC);

ALTER TABLE `api`.`interface` 
ADD COLUMN `monitorType` INT NOT NULL DEFAULT 0 COMMENT '监控类型，多选：\nNetwork(\"网络异常\",1),Include(\"包含指定字符串\",2),NotInclude(\"不包含指定字符串\",3),NotEqual(\"不等于指定字符串\",4);	\n' AFTER `fullUrl`,
ADD COLUMN `monitorText` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '监控比较内容' AFTER `monitorType`;

ALTER TABLE `api`.`user` 
CHANGE COLUMN `trueName` `trueName` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户真实姓名或昵称' ,
ADD COLUMN `avatarUrl` VARCHAR(500) NULL DEFAULT '' COMMENT '用户头像' AFTER `email`,
ADD COLUMN `loginType` INT NOT NULL DEFAULT 0 COMMENT '0：账号登陆，1：github登陆' AFTER `avatarUrl`,
ADD COLUMN `thirdlyId` VARCHAR(100) NULL COMMENT '第三方唯一ID' AFTER `loginType`,
DROP INDEX `email_UNIQUE` ,
DROP INDEX `userName` ;


ALTER TABLE `api`.`user` 
ADD UNIQUE INDEX `loginType_userName` (`userName` ASC, `loginType` ASC),
ADD UNIQUE INDEX `loginType_Email` (`email` ASC, `loginType` ASC);

ALTER TABLE `api`.`user` 
CHANGE COLUMN `trueName` `trueName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户真实姓名或昵称' ,
CHANGE COLUMN `avatarUrl` `avatarUrl` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '用户头像' ;

ALTER TABLE `api`.`user` 
CHANGE COLUMN `password` `password` VARCHAR(50) NOT NULL DEFAULT '' ;
delete from `api`.`setting` where mkey in('SOLR_URL','SEARCH_TYPE','SOLR_QUEUESIZE','SOLR_THREADCOUNT','DOMAIN')


