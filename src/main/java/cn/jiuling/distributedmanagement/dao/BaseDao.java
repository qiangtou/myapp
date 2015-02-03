package cn.jiuling.distributedmanagement.dao;

import java.io.Serializable;
import java.util.List;

import cn.jiuling.distributedmanagement.Vo.Pager;

public interface BaseDao<T> {
	public Serializable save(T t);

	public void delete(T t);

	public T find(Serializable id);

	public void update(T t);

	public void saveOrUpdate(Object t);

	public List<T> getAll();

	public T findByProperty(String name, Object value);

	public List findByPropertyList(String name, Object value);

	public List find(String queryString, Object[] values, Integer page, Integer rows);

	/**
	 * 分页模糊查询
	 * 
	 * @param exampleEntity
	 *            一个实体对象，当其中属性存在值时，like查询
	 * @param page
	 *            当前页
	 * @param rows
	 *            页面大小数
	 * @return
	 */
	public Pager list(Object exampleEntity, Integer page, Integer rows);

	public Pager list(Integer page, Integer rows);
}
