package com.mycompany.app.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mycompany.app.dao.ServerDao;
import com.mycompany.app.model.Server;

@Repository("serverDao")
public class ServerDaoImpl extends BaseDaoImpl<Server> implements ServerDao{
	public List<Server> getServersByDeptId(Integer deptId) {
		return getHibernateTemplate().find("from Server s where s.deptId=?",deptId);
	}
}
