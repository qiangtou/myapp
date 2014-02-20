package com.mycompany.app.service;

import java.util.List;

import com.mycompany.app.model.Server;

public interface ServerService {
	public List<Server> getServerList(Integer deptId);

	public void saveOrUpdate(Server server);

	public void active(Server server);
}
