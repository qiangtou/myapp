package cn.jiuling.distributedmanagement.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.distributedmanagement.dao.ServerDao;
import cn.jiuling.distributedmanagement.model.Server;
import cn.jiuling.distributedmanagement.service.ServerService;

@Service("serverService")
public class ServerServiceImpl implements ServerService {
	@Resource
	private ServerDao serverDao;

	public List<Server> getServerList(Integer deptId) {
		return serverDao.getServersByDeptId(deptId);
	}

	public ServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}

	public void saveOrUpdate(Server server) {
		serverDao.saveOrUpdate(server);
	}

	public void active(Server server) {
		server =serverDao.find(server.getId());
		server.setIsValid(Server.VALID);
		saveOrUpdate(server);
	}

	public void del(Server server) {
		server =serverDao.find(server.getId());
		serverDao.delete(server);
	}

	public Server updateIpAndDept(Server server) {
		Server s =serverDao.find(server.getId());
		s.setIpAddr(server.getIpAddr());
		s.setDeptId(server.getDeptId());
		serverDao.save(s);
		return s;
	}
}
