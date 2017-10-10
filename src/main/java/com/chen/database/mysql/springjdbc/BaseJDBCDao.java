package com.chen.database.mysql.springjdbc;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseJDBCDao extends JdbcDaoSupport{
	public void findNews(String id) {
			String sqlStr = "select * from news where id=?";
//			List list = this.getJdbcTemplate().query(sqlStr, new Object[]{id}, new Object());
	}
}
