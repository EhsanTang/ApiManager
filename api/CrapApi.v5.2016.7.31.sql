update `api`.`datacenter` set status=1;

ALTER TABLE `api`.`datacenter` 
ADD COLUMN `userId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `remark`;

ALTER TABLE `api`.`user` 
ADD COLUMN `type` TINYINT NOT NULL DEFAULT 100 COMMENT '用户类型：1普通用户，100：管理员' AFTER `sequence`;

INSERT INTO `api`.`datacenter` (`id`, `name`, `parentId`, `createTime`, `status`, `sequence`, `canDelete`, `type`)
VALUES ('privateModule', '根目录（用户）', '0', '2016-04-01 04:21:03', '1', '0', '0', 'MODULE');
