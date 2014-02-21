package com.mycompany.app.service;

import java.util.List;

import com.mycompany.app.model.Dept;

public interface DeptService {
	public List<Dept> getDeptList();

	public void saveOrUpdate(Dept dept);

	public boolean delete(Integer deptId);
}
