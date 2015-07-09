/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support;

import im.dadoo.spring.jdbc.support.util.Pair;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Order;
import im.dadoo.spring.jdbc.support.util.Util;
import java.util.List;

/**
 *
 * @author codekitten
 */
public final class SqlBuilder {
  
  private SqlBuilder() {}
  
  public static final String buildInsertSql(String table, List<String> fields) {
    List<String> values = Util.placeholder(fields);
    return String.format("INSERT INTO %s(%s) VALUES(%s)", table, Util.join(fields), Util.join(values));
  }
  
  public static final String buildUpdateAllSql(String table, List<String> fields) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("UPDATE %s ", table));
    sb.append(Criteria.set(fields));
    return sb.toString();
  }
  
  public static final String buildUpdateByIdSql(String table, List<String> fields) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields)).append(" ");
    sb.append("WHERE id = :id");
    return sb.toString();
  }
  
  public static final String buildUpdateSql(String table, 
          List<String> fields, List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
  public static final String buildDeleteAllSql(String table) {
    return String.format("DELETE FROM %s", table);
  }
  
  public static final String buildDeleteByIdSql(String table) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildDeleteAllSql(table)).append(" ");
    sb.append("id = :id");
    return sb.toString();
  }
  
  public static final String buildDeleteSql(String table, List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildDeleteAllSql(table)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
  public static final String buildFindByFieldSql(String table, String field) {
    return String.format("SELECT * FROM %s WHERE %s = :%s LIMIT 1", table, field, field);
  }
  
  public static final String buildFindByIdSql(String table) {
    return buildFindByFieldSql(table, "id");
  }
  
  public static final String buildListSql(String table, List<Condition> conditions, 
          List<Pair<String, Order>> orders) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("SELECT * FROM %s", table));
    if (conditions != null && !conditions.isEmpty()) {
      sb.append(" ").append(Criteria.where(conditions));
    }
    if (orders != null && !orders.isEmpty()) {
      sb.append(" ").append(Criteria.orderBy(orders));
    }
    return sb.toString();
  }
  
  public static final String buildListSql(String table, List<Condition> conditions, 
          List<Pair<String, Order>> orders, long limit) {
    StringBuilder sb = new StringBuilder();
    sb.append(SqlBuilder.buildListSql(table, conditions, orders)).append(" ");
    sb.append(String.format("LIMIT %d", limit));
    return sb.toString();
  }
  
  public static final String buildListSql(String table, List<Condition> conditions, 
          List<Pair<String, Order>> orders, int pagecount, int pagesize) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildListSql(table, conditions, orders)).append(" ");
    sb.append(String.format("LIMIT %d, %d", (pagecount -1) * pagesize, pagesize));
    return sb.toString();
  }
  
  public static final String buildSizeAllSql(String table) {
    return String.format("SELECT count(*) as size FROM %s", table);
  }
  
  public static final String buildSizeSql(String table, List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildSizeAllSql(table)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
}
