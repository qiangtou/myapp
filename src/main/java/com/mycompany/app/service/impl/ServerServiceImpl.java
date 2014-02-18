package com.mycompany.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.app.dao.ServerDao;
import com.mycompany.app.model.Server;
import com.mycompany.app.service.ServerService;

@Service("serverService")
public class ServerServiceImpl implements ServerService {
	@Resource
	private ServerDao serverDao;

	@Override
	public List<Server> getServerList(Integer deptId) {
		return serverDao.getServersByDeptId(deptId);
	}

	public ServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}

	@Override
	public void saveOrUpdate(Server server) {
		serverDao.saveOrUpdate(server);
	}
}
