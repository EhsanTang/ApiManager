ALTER TABLE `api`.`interface` 
CHANGE COLUMN `url` `url` VARCHAR(200) NOT NULL COMMENT 'api链接' ;

ALTER TABLE `api`.`datacenter` 
ADD COLUMN `projectId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `userId`;

ALTER TABLE `api`.`interface` 
DROP INDEX `url` ;

ALTER TABLE `api`.`interface` 
ADD COLUMN `fullUrl` VARCHAR(255) NOT NULL DEFAULT '' AFTER `header`;


