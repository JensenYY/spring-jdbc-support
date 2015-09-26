package im.dadoo.spring.jdbc.support;

import im.dadoo.spring.jdbc.support.util.Pair;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.util.Util;
import java.util.ArrayList;

import java.util.List;

/**
 * Criteria is used to generate "where" clause, "set" clause and "order" clause dynamically
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
   *   List&lt;String&gt; fields = new ArrayList&lt;&gt;();
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
    return set(fields, null);
  }
  
  /**
   * This function is to generate "SET" string for the "UPDATE" sentence.But the value could be customized.
   * When you want to update the fields such as "name" and "state", you can follow the code as below.
   * <code>
   *   List&lt;String&gt; fields = new ArrayList&lt;&gt;();
   *   fields.add("name");
   *   fields.add("state");
   *   
   *   List&lt;String&gt; values1 = new ArrayList&lt;&gt;();
   *   values1.add("test_name");
   *   values1.add("test_state");
   *   String clause1 = Criteria.set(fields,values1);
   *   //clause1 will be "SET name = :test_name, state = :test_state"
   *   
   *   List&lt;String&gt; values2 = new ArrayList&lt;&gt;();
   *   values2.add(null);
   *   values2.add("test_state");
   *   String clause2 = Criteria.set(fields,values2);
   *   //clause2 will be "SET name = :name, state = :test_state"
   * </code>
   * Then you can use spring-jdbc to update the datebase.
   * 
   * Caution:If the values is not null, the length of fields and values must be the same!
   * 
   * @param fields the fields expected to be updated
   * @param values the placeholder of values
   * @return SET clause
   * @since 0.3
   */
  public static final String set(List<String> fields, List<String> values) {
    String result = "";
    if (fields != null && !fields.isEmpty()) {
      if (!Util.checkFields(fields)) {
        throw new IllegalArgumentException("some field in fields is null or empty");
      }
      if (values == null) {
        values = new ArrayList<>();
        for (String field : fields) {
          values.add(field);
        }
      }
      if (fields.size() != values.size()) {
        throw new IllegalArgumentException("The length of fields and values should be the same");
      }
      
      List<String> kvs = new ArrayList<>(fields.size());
      for (int i = 0; i < fields.size(); i++) {
        String field = fields.get(i);
        String value = values.get(i);
        if (value == null || value.isEmpty()) {
          value = field;
        }
        kvs.add(String.format("%s = %s", field, Util.placeholder(value)));
      }
      result = String.format("SET %s", Util.join(kvs));
    }
    return result;
  }
  
  /**
   * This function is to generate "WHERE" clause for all the sql sentence.
   * When you want to select or update or delete the records with some "WHERE" conditions, 
   * you can follow the code as below.
   * <code>
   *   List&lt;Condition&gt; conds = new ArrayList&lt;&gt;();
   *   conds.add(Conditions.eq(name));
   *   conds.add(Conditions.gt(date));
   *   String clause = Criteria.where(conds);
   *   //clause will be "WHERE name = :name and date &gt; :date"
   * </code>
   * Then you can use spring-jdbc to handle the datebase.
   * @param conditions conditions for where clause
   * @return WHERE clause
   */
  public static final String where(final List<Condition> conditions) {
    String result = "";
    if (conditions != null && !conditions.isEmpty()) {
      if (!Util.checkConditions(conditions)) {
        throw new IllegalArgumentException("conditions illegal");
      }
      List<String> list = new ArrayList<>();
      for (Condition condition : conditions) {
        list.add(makeConditionSql(condition));
      }
      result = String.format("WHERE %s", Util.join(list, " AND "));
    } 
    return result;
  }
  
  /**
   * This function is to generate "ORDER BY" clause for select sql sentence.
   * you can follow the code as below to use this function.
   * <code>
   *   List&lt;String&gt; fields = new ArrayList&lt;&gt;();
   *   fields.add("name");
   *   fields.add("date");
   *   String clause = Criteria.orderBy(fields);
   *   //clause will be "ORDER BY date :order@date,name :order@name"
   * </code>
   * Then you can use spring-jdbc to handle the datebase.
   * 
   * @param fields fields for ordering
   * @return generated parted sql with order
   * @since 0.3
   */
  public static final String orderBy(final List<String> fields) {
    return orderBy(fields, null);
  }
  
  /**
   * This function is to generate "ORDER BY" clause for select sql sentence.
   * you can follow the code as below to use this function.
   * <code>
   *   List&lt;String&gt; fields = new ArrayList&lt;&gt;();
   *   fields.add("name");
   *   fields.add("date");
   *   List&lt;String&gt; values = new ArrayList&lt;&gt;();
   *   fields.add("order_name");
   *   fields.add("order_date");
   *   String clause = Criteria.orderBy(fields,values);
   *   //clause will be "ORDER BY date :order_date,name :order_name"
   * </code>
   * Then you can use spring-jdbc to handle the datebase.
   * 
   * @param fields fields for ordering
   * @param values placeholder for value
   * @return generated parted sql with order
   * @since 0.3
   */
  public static final String orderBy(List<String> fields, List<String> values) {
    String result = "";
    if (fields != null && !fields.isEmpty()) {
      if (!Util.checkFields(fields)) {
        throw new IllegalArgumentException("some field in fields is null or empty");
      }
      if (values == null) {
        values = new ArrayList<>();
        for (String field : fields) {
          values.add(String.format("order@%s", field));
        }
      }
      if (fields.size() != values.size()) {
        throw new IllegalArgumentException("The length of fields and values should be the same");
      }
      List<String> kvs = new ArrayList<>(fields.size());
      for (int i = 0; i < fields.size(); i++) {
        String field = fields.get(i);
        String value = values.get(i);
        if (value == null || value.isEmpty()) {
          value = String.format("order@%s", field);
        }
        kvs.add(String.format("%s %s", field, Util.placeholder(value)));
      }
      result = String.format("ORDER BY %s", Util.join(kvs));
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
