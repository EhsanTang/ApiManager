ALTER TABLE `project` 
ADD COLUMN `cover` VARCHAR(200) NOT NULL DEFAULT 'resources/images/cover.png' COMMENT '项目封面' AFTER `password`;
