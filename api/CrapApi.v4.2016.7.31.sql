update `api`.`datacenter` set status=1;

ALTER TABLE `apitest`.`datacenter` 
ADD COLUMN `userId` VARCHAR(50) NOT NULL DEFAULT '' AFTER `remark`;