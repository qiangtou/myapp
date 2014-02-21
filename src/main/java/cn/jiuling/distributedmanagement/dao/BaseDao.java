package cn.jiuling.distributedmanagement.dao;

import java.util.List;

public interface BaseDao<T> {
       public Integer save(T t);
       public void delete(T t);
       public T find(Integer id);
       public void update(T t);
       public void saveOrUpdate(Object t);
       public List<T> getAll();
}
