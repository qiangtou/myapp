package cn.jiuling.distributedmanagement.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.distributedmanagement.dao.ServerDao;
import cn.jiuling.distributedmanagement.model.Server;

@Repository("serverDao")
public class ServerDaoImpl extends BaseDaoImpl<Server> implements ServerDao{
	public List<Server> getServersByDeptId(Integer deptId) {
		return getHibernateTemplate().find("from Server s where s.deptId=?",deptId);
	}
}
