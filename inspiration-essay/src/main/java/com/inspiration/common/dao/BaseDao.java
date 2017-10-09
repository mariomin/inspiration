package com.inspiration.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {

	@Autowired
	private JdbcTemplate template;

	protected JdbcTemplate getTemplate() {
		return template;
	}

}
