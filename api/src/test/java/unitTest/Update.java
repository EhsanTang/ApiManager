package unitTest;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.crap.framework.MyException;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.DataCenter;
import cn.crap.model.Interface;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-servlet.xml"})
public class Update {
	@Autowired
	private IDataCenterService moduleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IInterfaceService interfaceService;
	
	/**
	 * V5版本升级成V6版本，下载源代码后，请执行该方法升级数据库
	 * 需要提供接口（有些用户没有下载源码，直接安装的无法执行该代码）
	 * @throws IOException 
	 * @throws MyException 
	 */
	@Test
	public void v5ToV6() throws MyException, IOException{
		// 模块中补全项目字段
		for(DataCenter dc: moduleService.findByMap(Tools.getMap("type","MODULE"), null, null)){
			
			if(dc.getParentId().equals(Const.TOP_MODULE) || dc.getParentId().equals(Const.PRIVATE_MODULE) || dc.getParentId().equals(Const.ADMIN_MODULE)){
				dc.setProjectId(dc.getId());
				moduleService.update(dc);
			}
			
			else{
				DataCenter parent = moduleService.get(dc.getParentId());
				// 父模块不存在，删除模块
				if(parent == null || MyString.isEmpty(parent.getId())){
					moduleService.delete(dc);
					break;
				}
				int i=0;
				while( !parent.getParentId().equals(Const.PRIVATE_MODULE) && !parent.getParentId().equals(Const.ADMIN_MODULE) ){
					DataCenter temp =  moduleService.get(parent.getParentId());
					if(temp == null || MyString.isEmpty(temp.getId())){
						moduleService.delete(dc);
						break;
					}else{
						i++;
						if(i>900){
							System.out.print("'"+temp.getId()+"',");
						}
						if(i>1000){
							System.out.println("模块表存在循环依赖，更新出现异常!!");
							return;
						}
						parent = temp;
					}
				}
				// 项目的根项目是私有项目或管理员项目则跟新
				if(parent.getParentId().equals(Const.PRIVATE_MODULE) || parent.getParentId().equals(Const.ADMIN_MODULE)){
					dc.setProjectId(parent.getId());
					moduleService.update(dc);
				}else{
					moduleService.delete(dc);
				}
			}
		}
		
		// 补全接口中的fullUrl
		for(Interface i : interfaceService.findByMap(null, null, null)){
			i.setFullUrl(i.getModuleUrl() + i.getUrl());
			interfaceService.update(i);
		}
		interfaceService.update("delete from Interface where moduleId not in(select id from DataCenter where type='MODULE')", null);
		
	}
}
