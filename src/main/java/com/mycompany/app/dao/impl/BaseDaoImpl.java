package com.mycompany.app.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.mycompany.app.dao.BaseDao;

@Repository
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private Class clazz;

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Class getClazz() {
		if (null == this.clazz) {
			Class clazz = (Class<T>) getClass();
			ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
			this.clazz = (Class) pt.getActualTypeArguments()[0];
		}
		return this.clazz;
	}

	public void delete(Object t) {
		getHibernateTemplate().delete(t);
	}

	public T find(Integer id) {
		return (T) getHibernateTemplate().get(getClazz(), id);
	}

	public Integer save(Object t) {
		return (Integer) getHibernateTemplate().save(t);
	}

	public void update(Object t) {
		 getHibernateTemplate().update(t);
	}
	public void saveOrUpdate(Object t) {
		getHibernateTemplate().saveOrUpdate(t);
	}
	
	public List<T> getAll(){
		return getHibernateTemplate().find("from "+getClazz().getName());
	}
}
