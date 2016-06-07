
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `modelClass` varchar(50) NOT NULL,
  `modelName` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `updateBy` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人',
  `remark` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `api`.`error` 
CHANGE COLUMN `errorId` `id` VARCHAR(50) NOT NULL COMMENT '主键' ;

ALTER TABLE `api`.`menu` 
CHANGE COLUMN `menuId` `id` VARCHAR(50) NOT NULL DEFAULT '导航菜单编号' ;

ALTER TABLE `api`.`module` 
CHANGE COLUMN `moduleId` `id` VARCHAR(50) NOT NULL COMMENT '所属模块ID' ;

ALTER TABLE `api`.`role` 
CHANGE COLUMN `roleId` `id` VARCHAR(50) NOT NULL COMMENT '角色ID' ;

ALTER TABLE `api`.`user` 
CHANGE COLUMN `userId` `id` VARCHAR(50) NOT NULL ;

ALTER TABLE `api`.`log` 
ADD COLUMN `content` TEXT NOT NULL AFTER `remark`;


