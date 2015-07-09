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
 * @author shuwen.zsw
 */
public final class SqlBuilder {
  
  private SqlBuilder() {}
  
  public static final String buildDeleteByIdSql(String table) {
    return String.format("DELETE FROM %s WHERE id = :id", table);
  }
  
  public static final String buildFindByIdSql(String table) {
    return String.format("SELECT * FROM %s WHERE id = :id", table);
  }
  
  public static final String buildListSql(String table, List<Condition> conditions) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("SELECT * FROM %s ", table));
    sb.append(Criteria.where(conditions));
    return sb.toString();
  }
  
}
