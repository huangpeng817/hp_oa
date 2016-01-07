package cn.hp.oa.base;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {
	
	void save(T entity) throws SQLException;
	
	void delete(Long id) throws SQLException;
	
	void update(T entity) throws SQLException;
	
	T getById(Long id) throws SQLException;
	
	List<T> getByIds(Long[] ids) throws SQLException;
	
	List<T> findAll() throws SQLException;
}
