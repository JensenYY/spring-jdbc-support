/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

/**
 *
 * @author codekitten
 * @since 0.1
 */
public enum Operation {
  
  EQ("="),
  NE("<>"),
  NOT_EQ("!="),
  GT(">"),
  GE(">="), 
  LT("<"),
  LE("<="),
  
  BETWEEN("BETWEEN"),
  NOT_BETWEEN("NOT BETWEEN"),
  LIKE("LIKE"),
  NOT_LIKE("NOT LIKE"),
  IN("IN"),
  NOT_IN("NOT IN"),
  IS_NULL("IS NULL"),
  IS_NOT_NULL("IS NOT NULL");
  
  private final String name;
  
  private Operation(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
