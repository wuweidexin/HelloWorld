package com.chen.database.mysql.sqlbuilder;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

public class TestBuilder {
	public static void main(String[] args) {
//		createTableSQL();
//		selectSQL();
//		insertPrepareSQL();
//		updateSQL();
		updatePrepareSQL();
	}
	
	/**
	 * 创建表的Sql方式
	 */
	public static void createTableSQL(){
		String createQuery =
				  (new CreateTableQuery(new CustomSql("t_user")))
				  .addCustomColumns(new CustomSql("id varchar(36)"),
				                    new CustomSql("accout varchar(36)"),
				                    new CustomSql("password varchar(36)"))
				  .validate().toString();
		System.out.println(createQuery);
	}
	/**
	 * 基本查询带join的
	 */
	public static void selectSQL(){
		String selectQuery =
				  (new SelectQuery())
				  .addCustomColumns(new CustomSql("foo"),
				                    new CustomSql("baz"),
				                    new CustomSql("buzz"))
				  .addCustomJoin(SelectQuery.JoinType.INNER,
				                 new CustomSql("table1"), new CustomSql("table2"),
				                 BinaryCondition.equalTo(
				                   new CustomSql("table1.id"), new CustomSql("table2.id")))
				  .addCustomOrderings(new CustomSql("foo"))
				  .validate().toString();
		System.out.println(selectQuery);
	}
	/**
	 * 新增直接拼接成sql
	 */
	public static void insertSQL(){
		InsertQuery inquery = new InsertQuery("t_user");
		byte[] by = new byte[16];
		inquery.addCustomColumn("id", "123456");
		inquery.addCustomColumn("account", "chenjunquan");
		inquery.addCustomColumn("age", 20);
		inquery.addCustomColumn("pic", by);
		String sql = inquery.validate().toString();
		System.out.println(sql);
	}
	
	/**
	 * 新增使用预编译方式
	 */
	public static void insertPrepareSQL(){
		InsertQuery inquery = new InsertQuery("t_user");
		inquery.addCustomPreparedColumns("id");
		inquery.addCustomPreparedColumns("account");
		inquery.addCustomPreparedColumns("age");
		inquery.addCustomPreparedColumns("pic");
		String sql = inquery.validate().toString();
		System.out.println(sql);
	}
	
	/**
	 * 修改使用预编译方式
	 */
	public static void updateSQL(){
		UpdateQuery upsql = new UpdateQuery("t_user");
		byte[] by = new byte[16];
		upsql.addCustomSetClause("id", "3654789");
		upsql.addCustomSetClause("account", "junquan_chen");
		upsql.addCustomSetClause("age", 20);
		upsql.addCustomSetClause("pic", by);
		upsql.getWhereClause().addCondition(BinaryCondition.equalTo("id", "123456"));
		upsql.getWhereClause().addCondition(BinaryCondition.equalTo("age", 28));
		String sql = upsql.validate().toString();
		System.out.println(sql);
	}
	
	    
	/**
	 * 修改使用预编译方式
	 */
	public static void updatePrepareSQL(){
		UpdateQuery upsql = new UpdateQuery("t_user");
		byte[] by = new byte[16];
		upsql.addCustomSetClause("id", "?");
		upsql.addCustomSetClause("account", "?");
		upsql.addCustomSetClause("age", "?");
		upsql.addCustomSetClause("pic", "?");
		upsql.getWhereClause().addCondition(BinaryCondition.equalTo("id", "?"));
		upsql.getWhereClause().addCondition(BinaryCondition.equalTo("age", "?"));
		String sql = upsql.validate().toString();
		System.out.println(sql);
	}
}
