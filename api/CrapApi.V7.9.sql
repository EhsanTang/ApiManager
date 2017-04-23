use api;

ALTER TABLE `api`.`project` 
ADD COLUMN `luceneSearch` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '是否允许建立Lucene搜索' AFTER `cover`;