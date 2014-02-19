package com.mycompany.app.dao;

import java.util.List;

import com.mycompany.app.model.Dept;

public interface DeptDao extends BaseDao<Dept> {
	public void deleteById(Integer Id); 
	public List<Dept> getUnDeletedList(); 
}
