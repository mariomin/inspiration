package com.huatai.common.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceUtil {
	private static final Logger log = LoggerFactory.getLogger(PersistenceUtil.class);
	
	public static void truncateData(SessionFactory sessionFactory, Date date) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "delete from ExchangeOrder where created < :created";
			Query query = session.createQuery(hql);
			query.setParameter("created", date);
			int rowCount = query.executeUpdate();
			
			log.debug("ExchangeOrder Records deleted: {}", rowCount);
			
			hql = "delete from Execution where created < :created";
			query = session.createQuery(hql);
			query.setParameter("created", date);
			rowCount = query.executeUpdate();
			
			log.debug("Execution Records deleted: {}", rowCount);
			
			tx.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {
			session.close();
		}
	}
	
	public static Long selectCount(SessionFactory sessionFactory, Class<?> clazz, Criterion[] criterions) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if ((criterions != null) && (criterions.length > 0)) {
				for (Criterion criterion : criterions) {
					criteria = criteria.add(criterion);
				}
			}
			return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		}
		finally {
			session.close();
		}
	}
	
	public static List<?> recover(SessionFactory sessionFactory, Class<?> clazz, Criterion[] criterions) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if ((criterions != null) && (criterions.length > 0)) {
				for (Criterion criterion : criterions) {
					criteria = criteria.add(criterion);
				}
			}
			return criteria.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		finally {
			session.close();
		}
	}
	
	public static List<?> recover(SessionFactory sessionFactory, Class<?> clazz, Criterion[] criterions, int startPos, int pageSize) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if ((criterions != null) && (criterions.length > 0)) {
				for (Criterion criterion : criterions) {
					criteria = criteria.add(criterion);
				}
			}
			criteria.setFirstResult(startPos).setMaxResults(pageSize);
			return criteria.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		finally {
			session.close();
		}
	}
	
	public static List<?> recover(SessionFactory sessionFactory, Class<?> clazz, Criterion[] criterions, int startPos, int pageSize, Order order) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if ((criterions != null) && (criterions.length > 0)) {
				for (Criterion criterion : criterions) {
					criteria = criteria.add(criterion);
				}
			}
			if (null != order) {
				criteria.addOrder(order);
			}
			criteria.setFirstResult(startPos).setMaxResults(pageSize);
			return criteria.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		finally {
			session.close();
		}
	}
	
	public static boolean persist(SessionFactory sessionFactory, Object[] objects) {
		return persist(sessionFactory, objects, null, null);
	}
	
	public static boolean persist(SessionFactory sessionFactory, Object[] objects, Integer transactionId, String associatedAccount) {
		if ((objects == null) || (objects.length == 0)) return true;
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Object object : objects) {
				if (object instanceof Collection) {
					Collection<?> collection = (Collection<?>) object;
					for (Object element : collection) {
						session.saveOrUpdate(element);
					}
				} else {
					session.saveOrUpdate(object);
				}
			}
			
			tx.commit();
		} catch (Throwable t) {
			log.error("failed to persist data: {}", t.getMessage(), t);
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
		finally {
			session.close();
		}
		
		return true;
	}
	
	public static boolean clear(SessionFactory sessionFactory, List<?> objects) {
		return clear(sessionFactory, objects.toArray());
	}
	
	public static boolean clear(SessionFactory sessionFactory, Object[] objects) {
		if ((objects == null) || (objects.length == 0)) return false;
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Object object : objects) {
				if (object != null) {
					if (object instanceof Collection) {
						Collection<?> collection = (Collection<?>) object;
						for (Object element : collection) {
							session.delete(element);
						}
					} else {
						session.delete(object);
					}
				}
			}
			tx.commit();
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
		finally {
			session.close();
		}
		return true;
	}
}
