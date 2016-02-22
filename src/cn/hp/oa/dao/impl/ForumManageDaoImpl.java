package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.ForumManageDao;
import cn.hp.oa.domain.Forum;
import cn.hp.oa.domain.Topic;
import cn.itcast.commons.CommonUtils;

public class ForumManageDaoImpl extends BaseDaoImpl<Forum> implements
		ForumManageDao {

	/**
	 * 重写getByid方法，关联集合字段topics的内容
	 */
	@Override
	public Forum getById(Long id) throws SQLException {
		String sql = "select * from itcast_forum where id=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(), id);
		return toForum(map);
	}
	
	private Forum toForum(Map<String, Object> map) throws SQLException {
		Forum forum = CommonUtils.toBean(map, Forum.class);
		if (forum != null) {
//			forum.setTopics(topics);
			String sql = "select * from itcast_topic where forumId=?";
			List<Topic> topicList = qr.query(sql, new BeanListHandler<Topic>(Topic.class), forum.getId());
			Set<Topic> topics = new HashSet<Topic>(topicList);
			forum.setTopics(topics);
		}
		return forum;
	}

	/**
	 * 由于新加了集合字段，重写update方法
	 */
	@Override
	public void update(Forum entity) throws SQLException {
//		String sql = "update itcast_forum set name=?,description=?,position=?,topicCount=?,articleCount=? where id=?";
		String sql = "";
		if (entity.getPosition() == 0) {
			sql = "update itcast_forum set name=?,description=? where id=?";
		} else {
			sql = "update itcast_forum set name=?,description=?,position=? where id=?";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getName());
		params.add(entity.getDescription());
		if (entity.getPosition() != 0) {
			params.add(entity.getPosition());
		}
		params.add(entity.getId());
		qr.update(sql, params.toArray());
		
	}
	
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
