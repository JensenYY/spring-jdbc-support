/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support;

import im.dadoo.spring.jdbc.support.condition.Condition;

import java.util.List;

/**
 * 提供静态方法，用于生成供spring-jdbc使用的where或者orderBy子句
 * 
 * @author shuwen.zsw
 */
public final class Criteria {
  
  private Criteria() {}

  /**
   * 根据所提供的conditions，动态生成where的查询子句
   * 
   * @param conditions 动态查询条件
   * @return 查询sql字符串
   */
  public static final String where(final List<Condition> conditions) {
    StringBuilder sb = new StringBuilder("");
    if (conditions != null && !conditions.isEmpty()) {
      Condition condition = conditions.get(0);
      if (condition != null) {
        sb.append(" WHERE ").append(makeConditionSql(condition));

        for (int i = 1; i < conditions.size(); i++) {
          sb.append(" AND ").append(makeConditionSql(conditions.get(i)));
        }
      }
    }
    return sb.toString();
  }
  
  /**
   * 根据所提供的orders，生成order by的查询子句
   * 
   * @param orders 动态排序条件
   * @return 生成的order by字符串
   */
  public static String orderBy(final List<Pair<String, String>> orders) {
    StringBuilder sb = new StringBuilder();
    if (orders != null && !orders.isEmpty()) {
      sb.append("ORDER BY ");
      Pair<String, String> first = orders.get(0);
      sb.append(first.getV1()).append(" ").append(first.getV2());
      for (int i = 1; i < orders.size(); i++) {
        Pair<String, String> order = orders.get(i);
        sb.append(",").append(order.getV1()).append(" ").append(order.getV2());
      }
    }
    return sb.toString();
  }
  
  private static String makeConditionSql(Condition condition) {
    StringBuilder sb = new StringBuilder();
    if (condition != null && condition.getField() != null && condition.getOp() != null) {
      switch (condition.getOp()) {
        case EQ:
        case NE:
        case GT:
        case GE:
        case LT:
        case LE:
        case LIKE:
          if (condition.getValue() != null) {
            sb.append(condition.getField()).append(" ").append(condition.getOp().getName())
                .append(" ").append(condition.getValue());
          }
          break;
        case BETWEEN:
          if (condition.getValue() != null) {
            @SuppressWarnings("unchecked")
            Pair<String, String> pair = (Pair<String, String>) condition.getValue();
            sb.append(condition.getField()).append(" ").append(condition.getOp().getName())
                .append(" ").append(pair.getV1()).append(" ").append("AND").append(" ")
                .append(pair.getV2());
          }
          break;
        case IS_NULL:
          sb.append(condition.getField()).append(" ").append(condition.getOp().getName()).append(" ");
          break;
        case IN:
          if (condition.getValue() != null) {
            sb.append(condition.getField()).append(" ").append(condition.getOp().getName())
                .append(" ").append("( ").append(condition.getValue()).append(" )");
          }
          break;
      }
    }
    return sb.toString();
  }
  
}
