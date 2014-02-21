package cn.jiuling.distributedmanagement.dao;

import java.util.List;

import cn.jiuling.distributedmanagement.model.Server;

public interface ServerDao extends BaseDao<Server> {
	public List<Server> getServersByDeptId(Integer deptId);
}
