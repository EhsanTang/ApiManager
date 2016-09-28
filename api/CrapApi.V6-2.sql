--执行unitTest.Update中的升级方法后才能执行该代码（删除无效接口）
delete from interface where moduleId not in(select id from datacenter where type='MODULE');
ALTER TABLE `api`.`interface` 
ADD INDEX `Index_fullUrl` (`fullUrl` ASC);

ALTER TABLE `api`.`interface` 
ADD COLUMN `monitorType` INT NOT NULL DEFAULT 0 COMMENT '监控类型，多选：\nNetwork(\"网络异常\",1),Include(\"包含指定字符串\",2),NotInclude(\"不包含指定字符串\",3),NotEqual(\"不等于指定字符串\",4);	\n' AFTER `fullUrl`,
ADD COLUMN `monitorText` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '监控比较内容' AFTER `monitorType`;
