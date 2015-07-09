/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

import im.dadoo.spring.jdbc.support.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Support various methods to generate Condition object
 * @author codekitten
 * 
 * @since 0.1
 */
public final class Conditions {
  
  private Conditions() {}
  
  /**
   * field = value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition eq(String field, String value) {
    return new Condition(field, Operation.EQ, value);
  }
  
  /**
   * field = :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition eq(String field) {
    return Conditions.eq(field, placeholder(field));
  }
  
  /**
   * field != value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition notEq(String field, String value) {
    return new Condition(field, Operation.NOT_EQ, value);
  }
  
  /**
   * field != :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition notEq(String field) {
    return Conditions.notEq(field, placeholder(field));
  }
  
  /**
   * field <> value
   * @param field
   * @param value
   * @return 
   * @sinc 0.1
   */
  public static Condition ne(String field, String value) {
    return new Condition(field, Operation.NE, value);
  }
  
  /**
   * field <> :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition ne(String field) {
    return Conditions.ne(field, placeholder(field));
  }
  
  /**
   * field > value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition gt(String field, String value) {
    return new Condition(field, Operation.GT, value);
  }
  
  /**
   * field > :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition gt(String field) {
    return Conditions.gt(field, placeholder(field));
  }
  
  /**
   * field >= value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition ge(String field, String value) {
    return new Condition(field, Operation.GE, value);
  }
  
  /**
   * field >= :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition ge(String field) {
    return Conditions.ge(field, placeholder(field));
  }
  
  /**
   * field < value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition lt(String field, String value) {
    return new Condition(field, Operation.LT, value);
  }
  
  /**
   * field < :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition lt(String field) {
    return Conditions.lt(field, placeholder(field));
  }
  
  /**
   * field <= value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition le(String field, String value) {
    return new Condition(field, Operation.LE, value);
  }
  
  /**
   * field <= :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition le(String field) {
    return Conditions.lt(field, placeholder(field));
  }
  
  /**
   * field LIKE value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition like(String field, String value) {
    return new Condition(field, Operation.LIKE, value);
  }
  
  /**
   * field LIKE :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition like(String field) {
    return Conditions.like(field, placeholder(field));
  }
  
  /**
   * field NOT LIKE value
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition notLike(String field, String value) {
    return new Condition(field, Operation.NOT_LIKE, value);
  }
  
  /**
   * field NOT LIKE :field
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition notLike(String field) {
    return Conditions.notLike(field, placeholder(field));
  }
  
  /**
   * field BETWEEN begin AND end
   * @param field
   * @param begin
   * @param end
   * @return 
   * @since 0.1
   */
  public static Condition between(String field, String begin, String end) {
    return new Condition(field, Operation.BETWEEN, Pair.of(begin, end));
  }
  
  /**
   * field BETWEEN :field_1 AND :field_2
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition between(String field) {
    return Conditions.between(field, placeholder(field + "_1"), placeholder(field + "_2"));
  }
  
  /**
   * field NOT BETWEEN begin AND end
   * @param field
   * @param begin
   * @param end
   * @return 
   * @since 0.1
   */
  public static Condition notBetween(String field, String begin, String end) {
    return new Condition(field, Operation.NOT_BETWEEN, Pair.of(begin, end));
  }
  
  /**
   * field NOT BETWEEN :field_1 AND :field_2
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition notBetween(String field) {
    return Conditions.notBetween(field, placeholder(field + "_1"), placeholder(field + "_2"));
  }
  
  /**
   * field IN (value)
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition in(String field, String value) {
    return new Condition(field, Operation.IN, value);
  }

  /**
   * field IN (:field)
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition in(String field) {
    return Conditions.in(field, placeholder(field));
  }
  
  /**
   * field NOT IN (value)
   * @param field
   * @param value
   * @return 
   * @since 0.1
   */
  public static Condition notIn(String field, String value) {
    return new Condition(field, Operation.NOT_IN, value);
  }

  /**
   * field NOT IN (:field)
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition notIn(String field) {
    return Conditions.notIn(field, placeholder(field));
  }
  
  /**
   * field IS NULL
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition isNull(String field) {
    return new Condition(field, Operation.IS_NULL, null);
  }
  
  /**
   * field IS NOT NULL
   * @param field
   * @return 
   * @since 0.1
   */
  public static Condition isNotNull(String field) {
    return new Condition(field, Operation.IS_NOT_NULL, null);
  }

  private static String placeholder(String field) {
    return ":" + field;
  }
  
  private static List<String> placeholder(List<String> fields) {
    List<String> result = new ArrayList<>();
    if (fields != null && !fields.isEmpty()) {
      for (String field : fields) {
        result.add(placeholder(field));
      }
    }
    return result;
  }
}
