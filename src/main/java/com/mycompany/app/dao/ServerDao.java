package com.mycompany.app.dao;

import java.util.List;

import com.mycompany.app.model.Server;

public interface ServerDao extends BaseDao<Server> {
	public List<Server> getServersByDeptId(Integer deptId);
}
