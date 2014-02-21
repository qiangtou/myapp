package com.mycompany.app.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mycompany.app.dao.DeptDao;
import com.mycompany.app.model.Dept;

@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao{
	public void deleteById(Integer Id){
		getHibernateTemplate().bulkUpdate("delete from Dept d where d.deptId=?",Id);
	}
	public List<Dept> getUnDeletedList(){
		return getHibernateTemplate().find("from Dept d where d.isDelete=?", Dept.UN_DELETE);
	}
	public boolean hasServer(Integer deptId) {
		List list= getHibernateTemplate().find("select count(*) from Server s where  s.deptId=?", deptId);
		Long count=(Long)list.get(0);
		return count>0;
	}
	
}
