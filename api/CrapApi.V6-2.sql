--执行unitTest中的升级方法后才能执行该代码（删除无效接口）
delete from interface where moduleId not in(select id from datacenter where type='MODULE');
ALTER TABLE `apitest`.`interface` 
ADD UNIQUE INDEX `fullUrl_UNIQUE` (`fullUrl` ASC);