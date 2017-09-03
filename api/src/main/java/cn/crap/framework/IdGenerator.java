package cn.crap.framework;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import cn.crap.model.User;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import cn.crap.framework.base.BaseModel;
import cn.crap.utils.MyString;

public class IdGenerator implements IdentifierGenerator{
	private static volatile AtomicInteger idNum = new AtomicInteger(1); // 自增，防止同一毫秒内id重复

	private static String ip = ""; // 分布式部署时防止ID重复，采用ip，如果ip获取不到，则随机生成
	public IdGenerator(){
		try{
			ip =InetAddress.getLocalHost().getHostAddress().replace(".", "");
		}catch(Exception e){
		    e.printStackTrace();
		    Random r = new Random();
		    ip = String.format("%012d", r.nextInt(1000000000));
		}
	}
	
	// id生成策略
	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		// 如果有id，则使用原有id，不重新生成
		BaseModel entity = (BaseModel) arg1;
		if( !MyString.isEmpty(entity.getId()) ){
			return entity.getId();
		}

		int id = idNum.getAndIncrement();

		// id 大于 100000 则从新从1开始
		if(id >= 100000){
			idNum.compareAndSet(id + 1, 1);
		}
		return System.currentTimeMillis() + "-" + ip + "-" + String.format("%06d", id);
	}

	public static String getId(){
		int id = idNum.getAndIncrement();
		// id 大于 100000 则从新从1开始
		if(id >= 100000){
			idNum.compareAndSet(id + 1, 1);
		}
		return System.currentTimeMillis() + "-" + ip + "-" + String.format("%06d", id);
	}

}
