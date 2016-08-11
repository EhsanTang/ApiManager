/*接口添加版本号*/
ALTER TABLE `api`.`interface` 
ADD COLUMN `version` VARCHAR(20) NOT NULL DEFAULT '1.0' COMMENT '版本号' AFTER `createTime`;

ALTER TABLE `api`.`setting` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前' AFTER `canDelete`;

ALTER TABLE `api`.`comment` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`error` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`interface` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`menu` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`module` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`role` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`user` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';

ALTER TABLE `api`.`webpage` 
ADD COLUMN `sequence` INT NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前';