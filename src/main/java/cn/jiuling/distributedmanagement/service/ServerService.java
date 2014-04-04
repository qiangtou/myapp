package cn.jiuling.distributedmanagement.service;

import java.util.List;

import cn.jiuling.distributedmanagement.model.Server;

public interface ServerService {
	public List<Server> getServerList(Integer deptId);

	public void saveOrUpdate(Server server);

	public void enable(Server server);

	public void del(Server server);

	/**
	 * 修改ip和部门
	 * 
	 * @param server
	 * @return
	 */
	public Server updateServer(Server server);

	public void disable(Server server);
}
