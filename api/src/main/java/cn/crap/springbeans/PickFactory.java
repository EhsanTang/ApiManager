package cn.crap.springbeans;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.UserType;
import cn.crap.framework.MyException;
import cn.crap.inter.service.tool.IPickService;
import cn.crap.utils.Tools;

@Component
public class PickFactory {
	
	@Resource(name="pickService")
	private IPickService pickService;
	
	@Resource(name="adminPickService")
	private IPickService adminPickService;
	
	@Resource(name="userPickService")
	private IPickService userPickService;

	public void getPickList(List<PickDto> picks, String code, String key) throws MyException {
		
		// 1.查询管理员中是否有对应的pick
		LoginInfoDto user = Tools.getUser();
		if( user != null && user.getType() == UserType.ADMIN.getType()){
			adminPickService.getPickList(picks, code, key, user);
		}
				
		// 2.如果管理员service中没有找到对应的pick则查询用户service
		if( user != null && picks.size() == 0 ){
			userPickService.getPickList(picks, code, key, user);
		}
		
		// 3.如果用户service中没有则查找普通的pickService
		if( picks.size() == 0 ){
			pickService.getPickList(picks, code, key, null);
		}
		
	}
}