package cn.hp.oa.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.jdbc.TxQueryRunner;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private QueryRunner qr = new TxQueryRunner();
	
	private Class<T> clazz;
	Field[] fields;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
		System.out.println("继承BaseDaoImpl的子类具体类型为： " + clazz);
		fields = clazz.getDeclaredFields();
	}
	
	@Override
	public void save(T entity) throws SQLException {

		String sql = "insert into itcast_"
				+ clazz.getSimpleName().toLowerCase() + "(";
		
		/**
		 * 循环拼凑插入列表
		 */
		for (int i = 1; i < fields.length; i++) { // id 字段跳过(自动增长)
			if (i < fields.length - 1) {
				sql += fields[i].getName() + ",";
			} else {
				sql += fields[i].getName();
			}
		}

		sql += ") values(";
		
		/**
		 * 循环拼凑插入的数据(获取entity的所有get方法的值)
		 */
		for (int i = 1; i < fields.length; i++) { // id 字段跳过(自动增长)
				if (i < fields.length - 1) {
					sql += "?,";
				} else {
					sql += "?";
				}
		}
		
		sql += ")";
		
		List<Object> params = new ArrayList<Object>();
		
		for (int i = 1; i < fields.length; i++) { // id 字段跳过(自动增长)
			try {
				PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), clazz);
				Method getMethod = pd.getReadMethod(); // 获取当前字段(属性)的get方法
				Object obj = getMethod.invoke(entity); // 调用get方法，得到返回值
				params.add(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		qr.update(sql, params.toArray());

	}

	@Override
	public void delete(Long id) throws SQLException {
		String sql = "delete from itcast_" + clazz.getSimpleName().toLowerCase() +" where id=?";
		qr.update(sql, id);
	}

	@Override
	public void update(T entity) throws SQLException {
		String sql = "update itcast_" + clazz.getSimpleName().toLowerCase()+" set ";
		
		for (int i = 1; i < fields.length; i++) { // id 字段跳过(自动增长)
			if (i < fields.length - 1) {
				sql += fields[i].getName() + "=?,";
			} else {
				sql += fields[i].getName() + "=?";
			}
		}
		sql += " where "+fields[0].getName()+ "=?";
		
		System.out.println(sql);
		
		List<Object> params = new ArrayList<Object>();
		
		for (int i = 1; i < fields.length; i++) { // id 字段跳过(自动增长)
			try {
				PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), clazz);
				Method getMethod = pd.getReadMethod(); // 获取当前字段(属性)的get方法
				Object obj = getMethod.invoke(entity); // 调用get方法，得到返回值
				params.add(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 判断条件的id条件是最后一个参数，最后添加进去
		 */
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fields[0].getName(), clazz);
			Method getMethod = pd.getReadMethod(); // 获取当前字段(属性)的get方法
			Object idValue = getMethod.invoke(entity); // 调用get方法，得到返回值
			params.add(idValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		qr.update(sql, params.toArray());
	}

	@Override
	public T getById(Long id) throws SQLException {
		String sql = "select * from itcast_" + clazz.getSimpleName().toLowerCase();
		sql += " where "+fields[0].getName()+ "=?";
		T entity = qr.query(sql, new BeanHandler<T>(clazz), id);
		return entity;
	}

	@Override
	public List<T> getByIds(Long[] ids) throws SQLException {
		List<T> list = new ArrayList<T>();
		for (Long id : ids) {
			T entity = getById(id);
			list.add(entity);
		}
		return list;
	}

	@Override
	public List<T> findAll() throws SQLException {
		String sql = "select * from itcast_" + clazz.getSimpleName().toLowerCase();
		return qr.query(sql, new BeanListHandler<T>(clazz));
	}

}
