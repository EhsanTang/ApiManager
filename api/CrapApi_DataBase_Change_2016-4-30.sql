/*接口添加版本号*/
ALTER TABLE `api`.`interface` 
ADD COLUMN `version` VARCHAR(20) NOT NULL DEFAULT '1.0' COMMENT '版本号' AFTER `createTime`;