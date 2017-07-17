package com.huatai.platform.downstream.count;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CountGenerator implements ICountGenerator {
	private static final int Default_Commit = 5000;
	private final CountReferenceId id;
	private AtomicInteger count = new AtomicInteger(0);
	private final SessionFactory sessionFactory;
	
	public CountGenerator(CountReferenceId id, SessionFactory sessionFactory) {
		this.id = id;
		this.sessionFactory = sessionFactory;
		fetch();
	}
	
	@Override
	public int next() {
		int current = count.incrementAndGet();
		if ((current % (Default_Commit)) == 0) {
			commit();
		}
		return current;
	}
	
	@SuppressWarnings("unchecked")
	private void fetch() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CountReference where countId = :countId");
		query.setParameter("countId", id.name());
		List<CountReference> list = query.list();
		if ((list != null) && (!list.isEmpty())) {
			Integer cur = list.get(0).getCount() + Default_Commit;
			count = new AtomicInteger(cur);
		}
		commit();
		session.close();
	}
	
	private void commit() {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			CountReference cr = new CountReference();
			cr.setCountId(id.name());
			cr.setCount(count.get() + Default_Commit);
			session.saveOrUpdate(cr);
			session.getTransaction().commit();
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
