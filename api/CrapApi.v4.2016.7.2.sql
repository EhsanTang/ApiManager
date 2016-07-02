ALTER TABLE `api`.`module` 
ADD COLUMN `url` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '模块地址' AFTER `sequence`;