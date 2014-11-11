/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

import im.dadoo.spring.jdbc.support.Pair;
import im.dadoo.spring.jdbc.support.util.Util;

/**
 * 辅助类，提供一系列的方法以便于生成Condition
 * @author shuwen.zsw
 */
public final class Conditions {
  
  private Conditions() {}
  
  //field = value
  public static Condition eq(String field, String value) {
    return new Condition(field, Operation.EQ, value);
  }
  
  //field = :field
  public static Condition eq(String field) {
    return Conditions.eq(field, Util.placeholder(field));
  }
  
  //field <> value
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
  
  public static Condition between(String field, String begin, String end) {
    return new Condition(field, Operation.BETWEEN, Pair.of(begin, end));
  }
  
  public static Condition between(String field) {
    return Conditions.between(field, Util.placeholder(field + "_1"), Util.placeholder(field + "_2"));
  }
  
  public static Condition in(String field, String value) {
    return new Condition(field, Operation.IN, value);
  }

  public static Condition in(String field) {
    return Conditions.in(field, Util.placeholder(field));
  }

}
