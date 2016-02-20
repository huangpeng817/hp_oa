package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.ForumManageDao;
import cn.hp.oa.domain.Forum;

public class ForumManageDaoImpl extends BaseDaoImpl<Forum> implements
		ForumManageDao {

	/**
	 * 重写findAll，根据position字段升序排列
	 */
	@Override
	public List<Forum> findAll() throws SQLException {
		String sql = "select * from itcast_forum order by position";
		return qr.query(sql, new BeanListHandler<Forum>(Forum.class));
	}
	
	/**
	 * 重写save方法，设置position字段的默认值与主键id相等(因为是自增)
	 */
	@Override
	public void save(Forum entity) throws SQLException {
		String sql = "insert into itcast_forum(name,description,position,topicCount,articleCount) values(?,?,0,0,0)";
		Object[] params = {
				entity.getName(),
				entity.getDescription(),
		};
		qr.update(sql, params);
		sql = "select MAX(id) from itcast_forum";
		Number id = (Number) qr.query(sql, new ScalarHandler());
		Forum forum = getById(id.longValue());
		forum.setPosition(id.intValue());
		update(forum);
	}

	@Override
	public void moveUp(Long id) throws SQLException {
		Forum forum = getById(id);
		String sql = "select * from itcast_forum where position<? order by position DESC limit 0,1";
		Forum other = qr.query(sql, new BeanHandler<Forum>(Forum.class), forum.getPosition());
		if (other == null) {
			return;
		}
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		update(forum);
		update(other);
	}

	@Override
	public void moveDown(Long id) throws SQLException {
		Forum forum = getById(id);
		System.out.println(forum);
		String sql = "select * from itcast_forum where position>? order by position ASC limit 0,1";
		Forum other = qr.query(sql, new BeanHandler<Forum>(Forum.class), forum.getPosition());
		System.out.println(other);
		if (other == null) {
			return;
		}
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		update(forum);
		update(other);
	}
}
