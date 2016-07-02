ALTER TABLE `api`.`module` 
ADD COLUMN `url` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '模块地址' AFTER `sequence`;


ALTER TABLE `api`.`module` 
CHANGE COLUMN `moduleName` `name` VARCHAR(100) NOT NULL COMMENT '所属模块名称' ,
CHANGE COLUMN `parentId` `parentId` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '父级节点ID，0：表示顶级类' ,
ADD COLUMN `canDelete` TINYINT NOT NULL DEFAULT 1 COMMENT '1：可删除，0：不可删除' AFTER `url`, RENAME TO  `api`.`datacenter` ;


ALTER TABLE `api`.`datacenter` 
ADD COLUMN `type` VARCHAR(50) NOT NULL DEFAULT 'MODULE' COMMENT '数据类型，默认为模块' AFTER `canDelete`,


ALTER TABLE `api`.`datacenter` 
ADD COLUMN `remark` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '备注' AFTER `type`;

ALTER TABLE `api`.`datacenter` 
ADD INDEX `TYPE` (`type` ASC);


CREATE TABLE `api`.`source` (
  `id` VARCHAR(50) NOT NULL,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` INT NOT NULL DEFAULT 0,
  `status` TINYINT NOT NULL DEFAULT 1,
  `name` VARCHAR(100) NOT NULL COMMENT '资源名称',
  PRIMARY KEY (`id`));


ALTER TABLE `api`.`source` 
ADD COLUMN `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' AFTER `name`;

ALTER TABLE `api`.`source` 
ADD COLUMN `directoryId` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '文件夹目录' AFTER `updateTime`;

ALTER TABLE `api`.`source` 
ADD COLUMN `remark` VARCHAR(5000) NOT NULL DEFAULT '' COMMENT '备注' AFTER `directoryId`;

ALTER TABLE `api`.`source` 
ADD COLUMN `filePath` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '文件目录' AFTER `remark`;


