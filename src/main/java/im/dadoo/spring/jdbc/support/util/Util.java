/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support.util;

import java.util.ArrayList;
import java.util.List;

import im.dadoo.spring.jdbc.support.condition.Condition;

/**
 *
 * @author codekitten
 * @since 0.1
 */
public final class Util {
  
  private Util() {}
  
  public static String placeholder(String field) {
    String result = null;
    if (field != null && !field.isEmpty()) {
      result = ":" + field;
    }
    return result;
  }
  
  public static List<String> placeholder(List<String> fields) {
    List<String> result = new ArrayList<>();
    if (fields != null && !fields.isEmpty()) {
      for (String field : fields) {
        result.add(placeholder(field));
      }
    }
    return result;
  }
  
  public static final String join(List<String> fields) {
    return join(fields, ",");
  }
  
  public static final String join(List<String> list, String seperator) {
    StringBuilder sb = new StringBuilder();
    if (list != null && !list.isEmpty()) {
      sb.append(list.get(0));
      for (int i = 1; i < list.size(); i++) {
        sb.append(seperator).append(list.get(i));
      }
    }
    return sb.toString();
  }
  
  public static final boolean checkFields(List<String> fields) {
    boolean result = true;
    result = result && fields != null && !fields.isEmpty();
    for (String field : fields) {
      result = result && field != null && !field.isEmpty();
    }
    return result;
  }
  
  public static final boolean checkConditions(List<Condition> conditions) {
    boolean result = true;
    for (Condition condition : conditions) {
      result = result && condition != null;
      result = result && condition.getField() != null && !condition.getField().isEmpty();
      result = result && condition.getOp() != null;
    }
    return result;
  }
}
