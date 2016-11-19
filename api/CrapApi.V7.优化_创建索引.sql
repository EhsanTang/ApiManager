ALTER TABLE `interface` 
ADD INDEX `index_moduleId` (`moduleId` ASC);

ALTER TABLE `article` 
ADD INDEX `index_moduleId` (`moduleId` ASC);

ALTER TABLE `comment` 
ADD INDEX `index_articleId_parentId` (`articleId` ASC, `parentId` ASC);

ALTER TABLE `error` 
ADD INDEX `index_moduleId` (`moduleId` ASC);

ALTER TABLE `menu` 
ADD INDEX `index_parentId` (`parentId` ASC);

ALTER TABLE `module` 
ADD INDEX `index_userId` (`userId` ASC);

ALTER TABLE `module` 
ADD INDEX `index_projectId` (`projectId` ASC);

ALTER TABLE `project` 
ADD INDEX `index_userId` (`userId` ASC),
ADD INDEX `index_status` (`status` ASC);

ALTER TABLE `project` 
DROP INDEX `index_status` ,
ADD INDEX `index_status_seq_createTime` (`status` ASC, `sequence` ASC, `createTime` ASC);


ALTER TABLE `project` 
DROP INDEX `index_userId` ,
ADD INDEX `index_userId_seq_createTime` (`userId` ASC, `sequence` ASC, `createTime` ASC);


ALTER TABLE `article` 
DROP INDEX `index_moduleId` ,
ADD INDEX `index_mod_type_cat_seq_time` (`moduleId` ASC, `type` ASC, `category` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `comment` 
DROP INDEX `index_articleId_parentId` ,
ADD INDEX `index_articleId_seq_time` (`articleId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `error` 
DROP INDEX `index_moduleId` ,
ADD INDEX `index_mod_seq_time` (`moduleId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `interface` 
DROP INDEX `index_moduleId` ,
ADD INDEX `index_mod_seq_time` (`moduleId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `module` 
DROP INDEX `index_userId` ,
ADD INDEX `index_uid_seq_time` (`userId` ASC, `sequence` ASC, `createTime` ASC),
DROP INDEX `index_projectId` ,
ADD INDEX `index_pid_seq_time` (`projectId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `project_user` 
ADD INDEX `index_uid_seq_time` (`userId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `source` 
ADD INDEX `index_mod_seq_time` (`moduleId` ASC, `sequence` ASC, `createTime` ASC);

ALTER TABLE `user` 
ADD INDEX `index_thirdlyId` (`thirdlyId` ASC);







