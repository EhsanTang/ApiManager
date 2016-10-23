CREATE TABLE `apitest`.`project_user` (
  `id` VARCHAR(50) NOT NULL,
  `status` TINYINT(4) NOT NULL DEFAULT 1,
  `sequence` INT NOT NULL DEFAULT 0,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `projectId` VARCHAR(50) NOT NULL,
  `userId` VARCHAR(50) NOT NULL,
  `addModule` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加模块',
  `delModule` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除模块',
  `modModule` BIT NOT NULL DEFAULT 1 COMMENT '是否可是修改模块',
  `addInter` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加接口',
  `delInter` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除接口',
  `modInter` BIT NOT NULL DEFAULT 1 COMMENT '是否可以修改接口',
  `addArticle` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加文章',
  `delArticle` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除文章',
  `modArticle` BIT NOT NULL DEFAULT 1 COMMENT '是否可以修改文章',
  `addSource` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加资源',
  `delSource` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除资源',
  `modSource` BIT NOT NULL DEFAULT 1 COMMENT '是否可以修改资源',
  `addDict` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加数据字典',
  `delDict` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除数据字典',
  `modDict` BIT NOT NULL DEFAULT 1 COMMENT '是否可以修改数据字典',
  `addError` BIT NOT NULL DEFAULT 1 COMMENT '是否可以添加错误码',
  `delError` BIT NOT NULL DEFAULT 0 COMMENT '是否可以删除错误码',
  `modError` BIT NOT NULL DEFAULT 1 COMMENT '是否可以修改错误码',
  PRIMARY KEY (`id`));
  
  ALTER TABLE `apitest`.`project_user` 
ADD UNIQUE INDEX `project_user` (`userId` ASC, `projectId` ASC);

ALTER TABLE `apitest`.`project_user` 
ADD COLUMN `userEmail` VARCHAR(45) NULL AFTER `modDict`,
ADD COLUMN `userName` VARCHAR(50) NULL AFTER `userEmail`;