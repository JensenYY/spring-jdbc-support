/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

import im.dadoo.spring.jdbc.support.util.Pair;
import im.dadoo.spring.jdbc.support.util.Util;


/**
 * Support various methods to generate Condition object
 * @author codekitten
 * 
 * @since 0.1
 */
public final class Conditions {
  
  private Conditions() {}
  

  public static Condition eq(String field, String value) {
    return new Condition(field, Operation.EQ, value);
  }

  public static Condition eq(String field) {
    return Conditions.eq(field, Util.placeholder(field));
  }
  

  public static Condition notEq(String field, String value) {
    return new Condition(field, Operation.NOT_EQ, value);
  }
  

  public static Condition notEq(String field) {
    return Conditions.notEq(field, Util.placeholder(field));
  }

  public static Condition ne(String field, String value) {
    return new Condition(field, Operation.NE, value);
  }
  
 
  public static Condition ne(String field) {
    return Conditions.ne(field, Util.placeholder(field));
  }

  public static Condition gt(String field, String value) {
    return new Condition(field, Operation.GT, value);
  }
  
  public static Condition gt(String field) {
    return Conditions.gt(field, Util.placeholder(field));
  }
  

  public static Condition ge(String field, String value) {
    return new Condition(field, Operation.GE, value);
  }

  public static Condition ge(String field) {
    return Conditions.ge(field, Util.placeholder(field));
  }
  
  
  public static Condition lt(String field, String value) {
    return new Condition(field, Operation.LT, value);
  }
  

  public static Condition lt(String field) {
    return Conditions.lt(field, Util.placeholder(field));
  }

  public static Condition le(String field, String value) {
    return new Condition(field, Operation.LE, value);
  }

  public static Condition le(String field) {
    return Conditions.lt(field, Util.placeholder(field));
  }

  public static Condition like(String field, String value) {
    return new Condition(field, Operation.LIKE, value);
  }
  

  public static Condition like(String field) {
    return Conditions.like(field, Util.placeholder(field));
  }

  public static Condition notLike(String field, String value) {
    return new Condition(field, Operation.NOT_LIKE, value);
  }

  public static Condition notLike(String field) {
    return Conditions.notLike(field, Util.placeholder(field));
  }

  public static Condition between(String field, String begin, String end) {
    return new Condition(field, Operation.BETWEEN, Pair.of(begin, end));
  }
  

  public static Condition between(String field) {
    return Conditions.between(field, Util.placeholder(field + "_1"), Util.placeholder(field + "_2"));
  }
  

  public static Condition notBetween(String field, String begin, String end) {
    return new Condition(field, Operation.NOT_BETWEEN, Pair.of(begin, end));
  }
  

  public static Condition notBetween(String field) {
    return Conditions.notBetween(field, Util.placeholder(field + "_1"), Util.placeholder(field + "_2"));
  }

  public static Condition in(String field, String value) {
    return new Condition(field, Operation.IN, value);
  }


  public static Condition in(String field) {
    return Conditions.in(field, Util.placeholder(field));
  }

  public static Condition notIn(String field, String value) {
    return new Condition(field, Operation.NOT_IN, value);
  }

  public static Condition notIn(String field) {
    return Conditions.notIn(field, Util.placeholder(field));
  }

  public static Condition isNull(String field) {
    return new Condition(field, Operation.IS_NULL, null);
  }
 
  public static Condition isNotNull(String field) {
    return new Condition(field, Operation.IS_NOT_NULL, null);
  }
  
}
