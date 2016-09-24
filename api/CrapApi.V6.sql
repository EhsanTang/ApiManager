ALTER TABLE `api`.`interface` 
CHANGE COLUMN `url` `url` VARCHAR(200) NOT NULL COMMENT 'api链接' ;

ALTER TABLE `api`.`datacenter` 
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `userId`;
