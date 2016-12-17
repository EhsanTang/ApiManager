ALTER TABLE `apidev`.`module` 
ADD COLUMN `templateId` VARCHAR(50) NULL COMMENT '接口模板ID' AFTER `projectId`,
ADD INDEX `index_templateId` (`templateId` ASC);

ALTER TABLE `apidev`.`interface` 
ADD COLUMN `isTemplate` BIT NOT NULL DEFAULT 0 COMMENT '是否是模板' AFTER `monitorEmails`;
