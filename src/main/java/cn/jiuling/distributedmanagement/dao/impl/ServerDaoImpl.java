package cn.jiuling.distributedmanagement.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.distributedmanagement.dao.ServerDao;
import cn.jiuling.distributedmanagement.model.Server;

@Repository("serverDao")
public class ServerDaoImpl extends BaseDaoImpl<Server> implements ServerDao {
	public List<Server> getServersByDeptId(Long deptId) {
		return getHibernateTemplate().find("from Server s where s.deptId=?", deptId);
	}

	@Override
	public List getOtherServer(Long id, String ipaddr) {
		return getHibernateTemplate().find("from Server s where s.ipAddr=? and s.id!=?", new Object[] { ipaddr, id });
	}

}
