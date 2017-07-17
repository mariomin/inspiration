package com.huatai.platform.downstream.util.count;

import static org.junit.Assert.assertEquals;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huatai.platform.downstream.count.CountGeneratorFactory;
import com.huatai.platform.downstream.count.CountReference;
import com.huatai.platform.downstream.count.CountReferenceId;
import com.huatai.platform.downstream.count.ICountGenerator;

@Ignore
@ContextConfiguration(locations = { "classpath:com/huatai/platform/downstream/util/count/count.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class CountGeneratorTest {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private ICountGenerator generator;
	
	@Before
	public void before() {
		truncateTable();
	}
	
	@After
	public void after() {
		truncateTable();
	}
	
	@Test
	public void test() {
		generator = getCountGenerator();
		for (int i = 1; i <= 99999; i++) {
			generator.next();
		}
		generator = getCountGenerator();
		for (int i = 1; i <= 100001; i++) {
			generator.next();
		}
		assertEquals(205000, fetchCount());
	}
	
	private ICountGenerator getCountGenerator() {
		return CountGeneratorFactory.getInstance().getGenerator(CountReferenceId.CountGeneratorTest);
	}
	
	private void truncateTable() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "truncate platform.t_count_reference";
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	private int fetchCount() {
		Session session = sessionFactory.openSession();
		String hql = "from CountReference where countId =:countId";
		Query query = session.createQuery(hql);
		query.setParameter("countId", CountReferenceId.CountGeneratorTest.name());
		CountReference cr = (CountReference) (query.list().get(0));
		return cr.getCount();
	}
	
}
