package com.inspiration.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceDaoImp extends BaseDao {

	private static final String sql_query = "SELECT KEY_VALUE FROM SEQUENCE_INFO WHERE KEY_NAME = ";
	private static final String sql_update = "UPDATE SEQUENCE_INFO SET KEY_VALUE = KEY_VALUE + ? WHERE KEY_NAME = ?";

	/**
	 * 请求缓存的values
	 * 
	 * @param keyname
	 * @return
	 */
	public Long queryValue(String keyName) {
		String sql = sql_query + "'" + keyName + "'";
		List<Map<String, Object>> indexList = this.getTemplate().queryForList(sql);
		if (!indexList.isEmpty()) {
			return (Long) indexList.get(0).get("key_value");
		}
		return null;
	}

	/**
	 * 执行sql
	 * 
	 * @param sql
	 * @throws DataAccessException
	 */
	public void excuteSql(String sql) throws DataAccessException {
		this.getTemplate().execute(sql);
	}

	public void updateValue(Object[] args) {
		this.getTemplate().update(sql_update, args);
	}
}
