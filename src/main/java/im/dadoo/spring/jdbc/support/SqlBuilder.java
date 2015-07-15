/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support;

import im.dadoo.spring.jdbc.support.condition.Condition;
import java.util.List;
import java.util.Map;

/**
 *
 * @author codekitten
 */
public final class SqlBuilder {
  
  private SqlBuilder() {}
  
  public static final String buildInsertSql(final String table, final List<String> fields, 
          final Map<String, String> valueMap) {
    String result = String.format("INSERT INTO %s%s", table, Criteria.into(fields, valueMap));
    return result;
  }
  
  public static final String buildInsertSql(final String table, final List<String> fields) {
    String result = String.format("INSERT INTO %s%s", table, Criteria.into(fields));
    return result;
  }
  
  public static final String buildUpdateAllSql(final String table, final List<String> fields, 
          final Map<String, String> valueMap) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("UPDATE %s ", table));
    sb.append(Criteria.set(fields, valueMap));
    return sb.toString();
  }
  
  public static final String buildUpdateAllSql(final String table, final List<String> fields) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("UPDATE %s ", table));
    sb.append(Criteria.set(fields));
    return sb.toString();
  }
  
  public static final String buildUpdateByIdSql(final String table, final List<String> fields) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields)).append(" ");
    sb.append("WHERE id = :id");
    return sb.toString();
  }
  
  public static final String buildUpdateByIdSql(final String table, final List<String> fields,
          final Map<String, String> valueMap) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields, valueMap)).append(" ");
    sb.append("WHERE id = :id");
    return sb.toString();
  }
  
  public static final String buildUpdateSql(final String table, 
          final List<String> fields, final List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
  public static final String buildUpdateSql(final String table, 
          final List<String> fields, final Map<String, String> valueMap, 
          final List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateAllSql(table, fields, valueMap)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
  public static final String buildDeleteAllSql(final String table) {
    return String.format("DELETE FROM %s", table);
  }
  
  public static final String buildDeleteByIdSql(final String table) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildDeleteAllSql(table)).append(" ");
    sb.append("id = :id");
    return sb.toString();
  }
  
  public static final String buildDeleteSql(final String table, final List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildDeleteAllSql(table)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
  public static final String buildFindByFieldSql(final String table, final String field) {
    return String.format("SELECT * FROM %s WHERE %s = :%s LIMIT 1", table, field, field);
  }
  
  public static final String buildFindByIdSql(final String table) {
    return buildFindByFieldSql(table, "id");
  }
  
  public static final String buildListSql(final String table, final List<Condition> conditions, 
          final List<String> orderFields, final Map<String, String> orderValueMap) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("SELECT * FROM %s", table));
    if (conditions != null && !conditions.isEmpty()) {
      sb.append(" ").append(Criteria.where(conditions));
    }
    if (orderFields != null && !orderFields.isEmpty()) {
      sb.append(" ").append(Criteria.orderBy(orderFields, orderValueMap));
    }
    return sb.toString();
  }
  
  public static final String buildListLimitSql(final String table, final List<Condition> conditions, 
          final List<String> orderFields, final Map<String, String> orderValueMap) {
    StringBuilder sb = new StringBuilder();
    sb.append(SqlBuilder.buildListSql(table, conditions, orderFields, orderValueMap)).append(" ");
    sb.append("LIMIT :limit");
    return sb.toString();
  }
  
  public static final String buildListLimitSql(final String table, final List<Condition> conditions, 
          final List<String> orderFields, final Map<String, String> orderValueMap, long limit) {
    StringBuilder sb = new StringBuilder();
    sb.append(SqlBuilder.buildListSql(table, conditions, orderFields, orderValueMap)).append(" ");
    sb.append(String.format("LIMIT %d", limit));
    return sb.toString();
  }
  
  public static final String buildPageSql(final String table, final List<Condition> conditions, 
          final List<String> orderFields, final Map<String, String> orderValueMap) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildListSql(table, conditions, orderFields, orderValueMap)).append(" ");
    sb.append("LIMIT :offset, :pagesize");
    return sb.toString();
  }
  
  public static final String buildPageSql(final String table, final List<Condition> conditions, 
          final List<String> orderFields, final Map<String, String> orderValueMap,
          int pagecount, int pagesize) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildListSql(table, conditions, orderFields, orderValueMap)).append(" ");
    sb.append(String.format("LIMIT %d, %d", (pagecount - 1) * pagesize, pagesize));
    return sb.toString();
  }
  
  public static final String buildSizeAllSql(final String table) {
    return String.format("SELECT count(*) as size FROM %s", table);
  }
  
  public static final String buildSizeSql(final String table, final List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildSizeAllSql(table)).append(" ");
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
}
