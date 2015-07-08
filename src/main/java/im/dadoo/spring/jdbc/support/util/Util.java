/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shuwen.zsw
 */
public final class Util {
  
  private Util() {}
  
  public static String placeholder(String field) {
    return ":" + field;
  }
  
  public static List<String> placeholder(List<String> fields) {
    List<String> result = new ArrayList<String>();
    if (fields != null && !fields.isEmpty()) {
      for (String field : fields) {
        result.add(placeholder(field));
      }
    }
    return result;
  }
  
}
