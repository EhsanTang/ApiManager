package cn.crap.framework;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import cn.crap.framework.base.BaseModel;
import cn.crap.utils.MyString;

public class IdGenerator implements IdentifierGenerator{
	private volatile int idNum = 1; // 自增，防止同一毫秒内id重复
	String ip = ""; // 分布式部署时防止ID重复
	public IdGenerator(){
		try{
			ip =InetAddress.getLocalHost().getHostAddress().replace(".", "");
		}catch(Exception e){
		    e.printStackTrace();
		    Random r = new Random();
		    ip = r.nextInt(10000000) + ""; 
		}
	}
	
	// id生成策略
	public synchronized Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		// 如果有id，则使用原有id，不重新生成
		BaseModel entity = (BaseModel) arg1;
		if( !MyString.isEmpty(entity.getId()) ){
			return entity.getId();
		}
		idNum ++;
		if(idNum > 9999){
			idNum = 1;
		}
		// 以ffff开头，兼容就系统，保证新生成的id大于旧方法生成的id
		return "ffff-" + System.currentTimeMillis() + "-" + ip + "-" + String.format("%04d", idNum);
	}

}
