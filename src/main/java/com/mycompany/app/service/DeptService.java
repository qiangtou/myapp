package com.mycompany.app.service;

import java.util.List;

import com.mycompany.app.model.Dept;

public interface DeptService {
	public List<Dept> getDeptList(Integer deptId);
}
