package cn.crap.framework.base;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class SuperDaoImpl extends HibernateDaoSupport implements SuperDao {
	
	public final HibernateTemplate getHibernateTemplateSuper(){
		return super.getHibernateTemplate();
	}
	
	@Resource
	public final void setSessionFactorySuper(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
