package cn.com.grocery.dao.impl.mysql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Title: BaseDao.java
 * @Package tdxy.dao
 * @Description: TODO(baseDao 数据库操作实现类)
 * @author dapeng
 * @date 2014年5月7日 下午5:09:22
 * @version V1.0
 */
@Repository
public class BaseDao {

	/**
	 * Autowired 自动装配 相当于get() set()
	 */
	@Autowired
	@Qualifier(value = "hibernateSessionFactory")
	protected SessionFactory sessionFactory;

	/**
	 * gerCurrentSession 会自动关闭session，使用的是当前的session事务
	 * 
	 * @return
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * openSession 需要手动关闭session 意思是打开一个新的session
	 * 
	 * @return
	 */
	public Session getNewSession() {
		return sessionFactory.openSession();
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * 根据 id 查询信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object load(Class c, String id) {
		Session session = getSession();
		return session.get(c, id);
	}

	/**
	 * 获取所有信息
	 * 
	 * @param c
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getAllList(Class c) {
		String hql = "from " + c.getName();
		Session session = getSession();
		return session.createQuery(hql).list();
	}

	/**
	 * 获取总数量
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long getTotalCount(Class c) {
		Session session = getNewSession();
		String hql = "select count(*) from " + c.getName();
		Long count = (Long) session.createQuery(hql).uniqueResult();
		session.close();
		return count != null ? count.longValue() : 0;
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 * 
	 */
	public void save(Object bean) {
		Session session = getSession();
		try {
			session.save(bean);
			// session.flush();
		} finally {
			// if (session != null) {
			// session.clear();
			// session.close();
			// }
		}
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * 
	 */
	public void update(Object bean) {
		Session session = getNewSession();
		session.update(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * 
	 */
	public void delete(Object bean) {
		Session session = getSession();
		session.delete(bean);
		// session.flush();
		// session.clear();
		// session.close();
	}

	/**
	 * 根据ID删除
	 * 
	 * @param c
	 *            类
	 * 
	 * @param id
	 *            ID
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String id) {
		Session session = getSession();
		Object obj = session.get(c, id);
		// session.delete(obj);
		// flush();
		// clear();
	}

	/**
	 * 批量删除
	 * 
	 * @param c
	 *            类
	 * 
	 * @param ids
	 *            ID 集合
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String[] ids) {
		for (String id : ids) {
			Object obj = getSession().get(c, id);
			if (obj != null) {
				getSession().delete(obj);
			}
		}
	}

}