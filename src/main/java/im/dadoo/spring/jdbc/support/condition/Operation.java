/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

/**
 *
 * @author shuwen.zsw
 */
public enum Operation {
  
  EQ("="),
  GT(">"),
  GE(">="), 
  LT("<"),
  LE("<="),
  NE("<>"),
  BETWEEN("BETWEEN"),
  LIKE("LIKE"),
  IN("IN");
  
  private final String name;
  
  private Operation(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
