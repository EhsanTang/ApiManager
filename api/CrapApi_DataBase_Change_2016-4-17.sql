CREATE TABLE `api`.`comment` (
  `id` VARCHAR(50) NOT NULL,
  `webpage_id` VARCHAR(50) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `user_id` VARCHAR(50) NULL,
  `parent_id` VARCHAR(50) NULL,
  `status` TINYINT(4) NOT NULL DEFAULT 1,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));

ALTER TABLE `api`.`webpage` 
ADD COLUMN `canComment` TINYINT(4) NOT NULL DEFAULT 1 AFTER `category`,
ADD COLUMN `commentCount` INT NOT NULL DEFAULT 0 AFTER `canComment`;

ALTER TABLE `api`.`comment` 
CHANGE COLUMN `webpage_id` `webpageId` VARCHAR(50) NOT NULL ,
CHANGE COLUMN `user_id` `userId` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `parent_id` `parentId` VARCHAR(50) NULL DEFAULT NULL ;

INSERT INTO `api`.`setting` (`id`, `mkey`, `value`, `remark`, `status`, `type`) VALUES ('ef157b7f-cc53-4a41-9679-d243d478023d', 'COMMENTCODE', 'true', '游客评论是否需要输入验证码', '1', 'TEXT');
