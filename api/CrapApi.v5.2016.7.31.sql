update `api`.`datacenter` set status=1;

ALTER TABLE `api`.`datacenter` 
ADD COLUMN `userId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `remark`;

ALTER TABLE `api`.`user` 
ADD COLUMN `type` TINYINT NOT NULL DEFAULT 100 COMMENT '用户类型：1普通用户，100：管理员' AFTER `sequence`;

INSERT INTO `api`.`datacenter` (`id`, `name`, `parentId`, `createTime`, `status`, `sequence`, `canDelete`, `type`)
VALUES ('privateModule', '根目录（用户）', 'top', '2016-04-01 04:21:03', '2', '0', '0', 'MODULE');

INSERT INTO `api`.`datacenter` (`id`, `name`, `parentId`, `createTime`, `status`, `sequence`, `canDelete`, `type`) 
VALUES ('0', '根目录（管理员）', 'top', '2016-04-01 04:21:03', '1', '0', '0', 'MODULE');

INSERT INTO `api`.`datacenter` (`id`, `name`, `parentId`, `createTime`, `status`, `sequence`, `canDelete`, `type`) 
VALUES ('top', '顶级目录（系统数据，请勿删除）', 'top', '2016-04-01 04:21:03', '1', '0', '0', 'MODULE');


ALTER TABLE `api`.`user` 
CHANGE COLUMN `roleId` `roleId` VARCHAR(1024) NOT NULL DEFAULT '' ,
CHANGE COLUMN `roleName` `roleName` VARCHAR(1024) NOT NULL DEFAULT '' ,
CHANGE COLUMN `auth` `auth` VARCHAR(1024) NOT NULL DEFAULT '' ,
CHANGE COLUMN `authName` `authName` VARCHAR(1024) NOT NULL DEFAULT '' ;

ALTER TABLE `api`.`user` 
ADD COLUMN `email` VARCHAR(45) NULL AFTER `type`;

INSERT INTO `api`.`setting` (`id`, `mkey`, `value`, `remark`, `status`, `type`,`canDelete`, `sequence`) VALUES 
('e2a493a7-c4f0-4cbb-832f-4495a7074252', 'LOGINBG', 'https://dn-coding-net-production-static.qbox.me/d58141c9-9a0c-40b0-a408-5935fd70670f.jpg', 
'登陆背景图', '1', 'IMAGE','0','100');

INSERT INTO `api`.`setting` (`id`, `mkey`, `value`, `remark`, `status`, `type`,`canDelete`, `sequence`) VALUES 
('e2a493a7-c888-4cbb-832f-4495a7074252', 'TITLEBG', 'resources/images/project.jpg', 
'头部标题背景图：resources/images/project.jpg,为空则显示主色调', '1', 'IMAGE','0','100');




