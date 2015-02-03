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

	public List<Server> getServerList(Long deptId) {
		return serverDao.getServersByDeptId(deptId);
	}

	public Server add(Server server) {
		Server s = serverDao.findByProperty("ipAddr", server.getIpAddr());
		if (null == s) {
			serverDao.save(server);
			return server;
		}
		return null;
	}

	public void enable(Server server) {
		server = serverDao.find(server.getId().intValue());
		server.setIsValid(Server.VALID);
		serverDao.update(server);
	}

	public void del(Server server) {
		server = serverDao.find(server.getId());
		serverDao.delete(server);
	}

	public Server updateServer(Server server) {
		Server s = serverDao.find(server.getId());

		List list = serverDao.getOtherServer(server.getId(), server.getIpAddr());
		if (list.size() == 0) {

			s.setIpAddr(server.getIpAddr());
			s.setDeptId(server.getDeptId());
			s.setIsEnhance(server.getIsEnhance());
			serverDao.save(s);
			return s;
		} else {
			return null;
		}
	}

	@Override
	public void disable(Server server) {
		server = serverDao.find(server.getId());
		server.setIsValid(Server.INVALID);
		serverDao.update(server);
	}
}
