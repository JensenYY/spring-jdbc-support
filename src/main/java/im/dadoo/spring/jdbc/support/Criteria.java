/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support;

import im.dadoo.spring.jdbc.support.util.Pair;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Order;
import im.dadoo.spring.jdbc.support.util.Util;
import java.util.ArrayList;

import java.util.List;

/**
 * Criteria is used to generate "where" clause and "set" clause dynamically
 * 
 * @author codekitten
 * @since 0.1
 */
public final class Criteria {
  
  private Criteria() {}

  /**
   * This function is to generate "SET" string for the "UPDATE" sentence.
   * When you want to update the fields such as "name" and "state", you can follow the code as below.
   * <code>
   *   List<String> fields = new ArrayList<>();
   *   fields.add("name");
   *   fields.add("state");
   *   String clause = Criteria.set(fields);
   *   //clause will be "SET name = :name, state = :state"
   * </code>
   * Then you can use spring-jdbc to update the datebase.
   * @param fields the fields expected to be updated
   * @return SET clause
   * @since 0.1
   */
  public static final String set(final List<String> fields) {
    String result = null;
    if (fields != null && !fields.isEmpty()) {
      List<String> list = new ArrayList<>();
      for (String field : fields) {
        list.add(String.format("%s = :%s", field, field));
      }
      result = String.format("SET %s", Util.join(list));
    }
    return result;
  }
  
  /**
   * This function is to generate "WHERE" clause for all the sql sentence.
   * When you want to select or update or delete the records with some "WHERE" conditions, 
   * you can follow the code as below.
   * <code>
   *   List<Condition> conds = new ArrayList<>();
   *   conds.add(Conditions.eq(name));
   *   conds.add(Conditions.gt(date));
   *   String clause = Criteria.where(conds);
   *   //clause will be "WHERE name = :name and date > :date"
   * </code>
   * Then you can use spring-jdbc to handle the datebase.
   * @param conditions conditions for where clause
   * @return WHERE clause
   */
  public static final String where(final List<Condition> conditions) {
    String result = null;
    if (conditions != null) {
      List<String> list = new ArrayList<>();
      for (Condition condition : conditions) {
        list.add(makeConditionSql(condition));
      }
      result = String.format("WHERE %s", Util.join(list, " AND "));
    }
    return result;
  }
  
  /**
   * make "ORDER BY" clause
   * 
   * @param orders 动态排序条件
   * @return 生成的order by字符串
   */
  public static final String orderBy(final List<Pair<String, Order>> orders) {
    String result = null;
    if (orders != null && !orders.isEmpty()) {
      List<String> list = new ArrayList<>();
      for (Pair<String, Order> order : orders) {
        list.add(String.format("%s %s", order.getV1(), order.getV2().getName()));
      }
      result = String.format("ORDER BY %s", Util.join(list));
    }
    return result;
  }
  
  private static String makeConditionSql(final Condition condition) {
    StringBuilder sb = new StringBuilder();
    if (condition != null && condition.getField() != null && condition.getOp() != null) {
      switch (condition.getOp()) {
        case EQ:
        case NE:
        case NOT_EQ:
        case GT:
        case GE:
        case LT:
        case LE:
        case LIKE:
        case NOT_LIKE:
          if (condition.getValue() != null) {
            sb.append(String.format("%s %s %s", condition.getField(), 
                    condition.getOp().getName(), 
                    condition.getValue()));
          }
          break;
        case BETWEEN:
        case NOT_BETWEEN:
          if (condition.getValue() != null) {
            @SuppressWarnings("unchecked")
            Pair<String, String> pair = (Pair<String, String>) condition.getValue();
            sb.append(String.format("%s %s %s AND %s", 
                    condition.getField(), condition.getOp().getName(), pair.getV1(), pair.getV2()));
          }
          break;
        case IS_NULL:
        case IS_NOT_NULL:
          sb.append(String.format("%s %s", condition.getField(), condition.getOp().getName()));
          break;
        case IN:
        case NOT_IN:
          if (condition.getValue() != null) {
            sb.append(String.format("%s %s (%s)", 
                    condition.getField(), condition.getOp().getName(), condition.getValue()));
          }
          break;
      }
    }
    return sb.toString();
  }
  
  
}
