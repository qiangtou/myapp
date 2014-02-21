package cn.jiuling.distributedmanagement.dao;

import java.util.List;

import cn.jiuling.distributedmanagement.model.Dept;

public interface DeptDao extends BaseDao<Dept> {
	public void deleteById(Integer Id); 
	public List<Dept> getUnDeletedList();
	/**
	 * 检测部门下是否有服务器
	 * @param deptId
	 * @return
	 */
	public boolean hasServer(Integer deptId); 
}
