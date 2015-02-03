package cn.jiuling.distributedmanagement.dao;

import java.util.List;

import cn.jiuling.distributedmanagement.model.Server;

public interface ServerDao extends BaseDao<Server> {
	public List<Server> getServersByDeptId(Long deptId);

	/**
	 * 查找其他重复ip的服务器
	 * 
	 * @param id
	 *            当前服务器的id
	 * @param ipaddr
	 *            当前服务器的ip
	 * @return 其他服务器的拥有相同ip的list
	 */
	public List getOtherServer(Long id, String ipaddr);
}
