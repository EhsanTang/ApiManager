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



