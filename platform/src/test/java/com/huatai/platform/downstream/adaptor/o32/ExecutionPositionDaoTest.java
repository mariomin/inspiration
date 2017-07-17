package com.huatai.platform.downstream.adaptor.o32;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huatai.platform.downstream.adaptor.o32.common.ExecutionPosition;
import com.huatai.platform.downstream.adaptor.o32.common.ExecutionPositionDao;

import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.DbfLibException;

public class ExecutionPositionDaoTest {
	
	private ExecutionPositionDao dao;
	
	File file = new File("./data/execPost.dbf");
	
	@Before
	public void setUp() throws Exception {
		File dir = new File("./data");
		if (!dir.exists()) {
			FileUtils.forceMkdir(dir);
		}
		
		//		if (!file.exists()) {
		//			file.createNewFile();
		//		}
		dao = new ExecutionPositionDao(file);
	}
	
	@After
	public void tearDown() throws IOException {
		if (null != dao) {
			dao.close();
		}
	}
	
	@Test
	public void test() throws IOException, DbfLibException {
		for (int i = 0; i < 2; i++) {
			ExecutionPosition executionPosition = new ExecutionPosition("2102", "3162", "0");
			int index = dao.insert(executionPosition);
			System.out.println("index=" + index);
			executionPosition.setPositionStr("1");
			dao.update(index, executionPosition);
		}
	}
	
	@Test
	public void testLoadAll() throws CorruptedTableException, IOException {
		System.out.println(dao.loadAll());
	}
	
}
