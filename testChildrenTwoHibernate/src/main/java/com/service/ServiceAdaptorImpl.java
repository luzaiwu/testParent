/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * @author sikandar
 */
public class ServiceAdaptorImpl implements IService {

	private SessionFactory sessionFactory;
	public ServiceAdaptorImpl() {
		Logger log = Logger.getLogger("org.hibernate.SQL");
		log.setLevel(Level.FATAL);
	}

	public Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	protected Session getSession() {
		Session session;
		try {
			session = sessionFactory.openSession();
		} catch (Exception ex) {
			session = sessionFactory.getCurrentSession();
			ex.printStackTrace();
		}
		return session;
	}

	public void closeSession(Session session) {
		try {

			if (session != null) {
				if (session.isOpen()) {
					session.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	SessionWrapper begin() {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		return new SessionWrapper(session, tx);
	}

	void end(SessionWrapper session) {
		if (session != null) {
			try {
				session.getTx().commit();
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
			try {
				closeSession(session.getSession());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	SessionWrapper beginOnlySession() {
		Session session = getSession();
		return new SessionWrapper(session, null);
	}
	
	void endOnlySession(SessionWrapper session) {
		if (session != null) {
			try {
				closeSession(session.getSession());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	void rollback(SessionWrapper sessionWrapper) {
		if (sessionWrapper != null && sessionWrapper.getTx() != null)
			sessionWrapper.getTx().rollback();
	}

	public boolean save(Object bean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.save(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			closeSession(session);
		}
	}

	public int getCount(String hql) {
		SessionWrapper sessionWrapper = null;
		try {
			sessionWrapper = begin();
			hql = "SELECT COUNT(*) " + hql.substring(hql.toUpperCase().indexOf("from"));
			Query query = sessionWrapper.getSession().createQuery(hql);
			return ((Number) query.iterate().next()).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			rollback(sessionWrapper);
			return 0;
		} finally {
			end(sessionWrapper);
		}
	}
	
	public int getSqlCount(String sql) {
		SessionWrapper sessionWrapper = null;
		try {
			sessionWrapper = begin();
			sql = "SELECT COUNT(1) from ( select 1 " + sql.substring(sql.toLowerCase().indexOf("from"))+" limit 5000) as sum ";
			Query query = sessionWrapper.getSession().createSQLQuery(sql);
			return ((Number) query.uniqueResult()).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			rollback(sessionWrapper);
			return 0;
		} finally {
			end(sessionWrapper);
		}
	}

	public synchronized boolean update(Object bean) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.update(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			closeSession(session);
		}
	}
	
	public synchronized boolean merge(Object bean) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.merge(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			closeSession(session);
		}
	}

	public boolean save(List<?> beanList) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			if (beanList != null && beanList.size() > 0) {
				for (Object bean : beanList)
					session.save(bean);
			}
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
			return false;
		} finally {
			closeSession(session);
		}
	}

	public synchronized boolean update(List<?> beanList) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			if (beanList != null && beanList.size() > 0) {
				for (Object bean : beanList)
					session.update(bean);
			}
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {

			closeSession(session);
		}
	}
	
	public synchronized boolean merge(List<?> beanList) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			if (beanList != null && beanList.size() > 0) {
				for (Object bean : beanList)
					session.merge(bean);
			}
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {

			closeSession(session);
		}
	}

	public synchronized int update(String table, String whereField, Object whereValue, String updateField, Object value) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();

			String hqlUpdate = "update com.betterone.server.db.entity." + table + " set " + updateField + "=:val1 where " + whereField + "= :val2";
			Query query = session.createQuery(hqlUpdate);

			if (value instanceof Long)
				query.setLong("val1", ((Long) value).longValue());
			else if (value instanceof String)
				query.setString("val1", (String) value);
			else if (value instanceof Byte)
				query.setByte("val1", (Byte) value);
			else if (value instanceof Integer)
				query.setInteger("val1", (Integer) value);
			else if (value instanceof Boolean)
				query.setBoolean("val1", (Boolean) value);
			else if (value instanceof Timestamp)
				query.setTimestamp("val1", (Timestamp) value);
			else if (value instanceof Date)
				query.setDate("val1", (Date) value);
			else
				query.setEntity("val1", value);

			if (whereValue instanceof Long)
				query.setLong("val2", ((Long) whereValue).longValue());
			else if (whereValue instanceof String)
				query.setString("val2", (String) whereValue);
			else if (whereValue instanceof Boolean)
				query.setBoolean("val2", (Boolean) whereValue);
			else
				query.setEntity("val2", whereValue);

			int updateEntities = query.executeUpdate();
			tx.commit();
			return updateEntities;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {

			closeSession(session);
		}
	}

	public synchronized int update(String table, String whereField, Object whereValue, String updateField, Object value, String comparission) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();

			String hqlUpdate = "update com.betterone.server.db.entity." + table + " set " + updateField + "=:val1 where " + whereField + comparission
					+ " :val2";
			Query query = session.createQuery(hqlUpdate);

			if (value instanceof Long)
				query.setLong("val1", ((Long) value).longValue());
			else if (value instanceof String)
				query.setString("val1", (String) value);
			else if (value instanceof Byte)
				query.setByte("val1", (Byte) value);
			else if (value instanceof Integer)
				query.setInteger("val1", (Integer) value);
			else if (value instanceof Boolean)
				query.setBoolean("val1", (Boolean) value);
			else if (value instanceof Timestamp)
				query.setTimestamp("val1", (Timestamp) value);
			else if (value instanceof Date)
				query.setDate("val1", (Date) value);
			else
				query.setEntity("val1", value);

			if (whereValue instanceof Long)
				query.setLong("val2", ((Long) whereValue).longValue());
			else if (whereValue instanceof String)
				query.setString("val2", (String) whereValue);
			else if (whereValue instanceof Boolean)
				query.setBoolean("val2", (Boolean) whereValue);
			else if (whereValue instanceof Timestamp)
				query.setTimestamp("val2", (Timestamp) whereValue);
			else if (whereValue instanceof Date)
				query.setDate("val2", (Date) whereValue);
			else
				query.setEntity("val2", whereValue);

			int updateEntities = query.executeUpdate();
			tx.commit();
			return updateEntities;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {

			closeSession(session);
		}
	}

	public synchronized int update(String table, String whereFields[], Object whereValues[], String updateField, Object value) {
		Session session = null;
		Transaction tx = null;
		try {
			if (whereFields.length != whereValues.length)
				return -1;

			session = getSession();
			tx = session.beginTransaction();

			String hqlUpdate = "update com.betterone.server.db.entity." + table + " ";
			String strWhere = " where ";
			for (int i = 0; i < whereFields.length; i++) {
				strWhere = strWhere + whereFields[i] + " = :val" + i + " and ";
			}
			hqlUpdate = hqlUpdate + " set " + updateField + " = :val" + strWhere;
			hqlUpdate = hqlUpdate.substring(0, hqlUpdate.lastIndexOf(" and"));
			Query query = session.createQuery(hqlUpdate);

			if (value instanceof Long)
				query.setLong("val", ((Long) value).longValue());
			else if (value instanceof String)
				query.setString("val", (String) value);
			else if (value instanceof Byte)
				query.setByte("val", (Byte) value);
			else if (value instanceof Integer)
				query.setInteger("val", (Integer) value);
			else if (value instanceof Boolean)
				query.setBoolean("val", (Boolean) value);
			else if (value instanceof Timestamp)
				query.setTimestamp("val", (Timestamp) value);
			else if (value instanceof Date)
				query.setDate("val", (Date) value);
			else
				query.setEntity("val", value);

			for (int i = 0; i < whereValues.length; i++) {
				if (whereValues[i] instanceof Long)
					query.setLong("val" + i, ((Long) whereValues[i]).longValue());
				else if (whereValues[i] instanceof Integer)
					query.setLong("val" + i, ((Integer) whereValues[i]).intValue());
				else if (whereValues[i] instanceof String)
					query.setString("val" + i, (String) whereValues[i]);
				else if (whereValues[i] instanceof Boolean)
					query.setBoolean("val" + i, (Boolean) whereValues[i]);
				else if (whereValues[i] instanceof Timestamp)
					query.setTimestamp("val" + i, (Timestamp) whereValues[i]);
				else if (whereValues[i] instanceof Date)
					query.setDate("val" + i, (Date) whereValues[i]);
				else
					query.setEntity("val" + i, whereValues[i]);
			}

			int updateEntities = query.executeUpdate();
			tx.commit();
			return updateEntities;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {
			closeSession(session);
		}
	}

	public synchronized int update(String table, String whereField, Object whereValue, String updateFields[], Object updateValues[]) {
		Session session = null;
		Transaction tx = null;
		try {
			if (updateFields.length != updateValues.length)
				return -1;

			session = getSession();
			tx = session.beginTransaction();

			String strUpdate = "set ";

			for (int i = 0; i < updateFields.length - 1; i++) {
				strUpdate = strUpdate + updateFields[i] + "=:val" + i + ", ";
			}
			strUpdate = strUpdate + updateFields[updateFields.length - 1] + "=:val" + (updateFields.length - 1) + "";

			String hqlUpdate = "update com.betterone.server.db.entity." + table + " " + strUpdate + " where " + whereField + "=:val";
			Query query = session.createQuery(hqlUpdate);

			if (whereValue instanceof Long)
				query.setLong("val", ((Long) whereValue).longValue());
			else if (whereValue instanceof String)
				query.setString("val", (String) whereValue);
			else if (whereValue instanceof Byte)
				query.setByte("val", (Byte) whereValue);
			else if (whereValue instanceof Integer)
				query.setInteger("val", (Integer) whereValue);
			else if (whereValue instanceof Boolean)
				query.setBoolean("val", (Boolean) whereValue);
			else
				query.setEntity("val", whereValue);

			for (int i = 0; i < updateValues.length; i++) {
				if (updateValues[i] instanceof Long)
					query.setLong("val" + i, ((Long) updateValues[i]).longValue());
				else if (updateValues[i] instanceof String)
					query.setString("val" + i, (String) updateValues[i]);
				else if (updateValues[i] instanceof Byte)
					query.setByte("val" + i, (Byte) updateValues[i]);
				else if (updateValues[i] instanceof Integer)
					query.setInteger("val" + i, (Integer) updateValues[i]);
				else if (updateValues[i] instanceof Boolean)
					query.setBoolean("val" + i, (Boolean) updateValues[i]);
				else
					query.setEntity("val" + i, updateValues[i]);
			}

			int updateEntities = query.executeUpdate();
			tx.commit();
			return updateEntities;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {

			closeSession(session);
		}
	}

	public synchronized int delete(String table, String whereField, Object whereValue) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();

			String hqlDelete = "delete from com.betterone.server.db.entity." + table + " where " + whereField + "= :val";

			Query query = session.createQuery(hqlDelete);

			if (whereValue instanceof Long)
				query.setLong("val", ((Long) whereValue).longValue());
			else if (whereValue instanceof Integer)
				query.setInteger("val", ((Integer) whereValue).intValue());

			else if (whereValue instanceof String)
				query.setString("val", (String) whereValue);

			int deleteEntities = query.executeUpdate();
			tx.commit();
			return deleteEntities;
		} catch (ConstraintViolationException v) {
			tx.rollback();
			throw v;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {

			closeSession(session);
		}
	}

	public synchronized int delete(String table, String whereFields[], Object whereValues[]) {
		Session session = null;
		Transaction tx = null;
		try {
			if (whereFields.length != whereValues.length)
				return -1;

			session = getSession();
			tx = session.beginTransaction();

			String hqlUpdate = "delete from com.betterone.server.db.entity." + table + " where ";
			for (int i = 0; i < whereFields.length; i++) {
				hqlUpdate = hqlUpdate + whereFields[i] + " = :val" + i + " and ";
			}
			hqlUpdate = hqlUpdate.substring(0, hqlUpdate.lastIndexOf(" and") + 1);
			Query query = session.createQuery(hqlUpdate);

			for (int i = 0; i < whereValues.length; i++) {
				if (whereValues[i] instanceof Long)
					query.setLong("val" + i, ((Long) whereValues[i]).longValue());
				else if (whereValues[i] instanceof Integer)
					query.setLong("val" + i, ((Integer) whereValues[i]).intValue());
				else if (whereValues[i] instanceof String)
					query.setString("val" + i, (String) whereValues[i]);
				else if (whereValues[i] instanceof Boolean)
					query.setBoolean("val" + i, (Boolean) whereValues[i]);
				else if (whereValues[i] instanceof Timestamp)
					query.setTimestamp("val" + i, (Timestamp) whereValues[i]);
				else if (whereValues[i] instanceof Date)
					query.setDate("val" + i, (Date) whereValues[i]);
				else
					query.setEntity("val" + i, whereValues[i]);
			}
			int updateEntities = query.executeUpdate();
			tx.commit();
			return updateEntities;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return -1;
		} finally {
			closeSession(session);
		}
	}

	public boolean saveOrUpdate(List<?> beanList) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			for (Object bean : beanList)
				session.saveOrUpdate(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
			return false;
		} finally {
			closeSession(session);
		}
	}

	public synchronized boolean saveOrUpdate(Object bean) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException v) {
			tx.rollback();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			closeSession(session);
		}

	}

	public synchronized boolean delete(Object bean) throws ConstraintViolationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.delete(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException v) {
			tx.rollback();
			throw v;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			closeSession(session);
		}

	}

	public synchronized boolean delete(List<?> beans) throws ConstraintViolationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			for (Object bean : beans)
				session.delete(bean);
			tx.commit();
			return true;
		} catch (ConstraintViolationException v) {
			tx.rollback();
			throw v;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {

			closeSession(session);
		}

	}

	public Disjunction getLocationDisjunction(String location) {
		Disjunction or = Restrictions.disjunction();
		int index = location.length();
		or.add(Restrictions.eq("location", "all"));
		do {
			location = location.substring(0, index);
			or.add(Restrictions.eq("location", location));
			index = location.lastIndexOf('-');
		} while (index != -1);
		return or;
	}

	final class SessionWrapper {
		private Session session = null;
		private Transaction tx = null;

		public SessionWrapper(Session session, Transaction tx) {
			super();
			this.session = session;
			this.tx = tx;
		}

		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public Transaction getTx() {
			return tx;
		}

		public void setTx(Transaction tx) {
			this.tx = tx;
		}

	}
	
	
	public boolean saveBySql(String sql) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit();
			return true;
		} catch (ConstraintViolationException cvx) {
			throw cvx;
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			closeSession(session);
		}
	}
	
	public Date convert2BeginOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}

	public Date convert2EndOfDate(Date to) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(to);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		return calendar.getTime();
	}

}
