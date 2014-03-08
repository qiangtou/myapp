package cn.jiuling.distributedmanagement.service;

import java.util.List;

import cn.jiuling.distributedmanagement.model.Dept;

public interface DeptService {
	public List<Dept> getDeptList();

	public void saveOrUpdate(Dept dept);

	public boolean delete(Integer deptId);

	public void update(Dept dept);
}
