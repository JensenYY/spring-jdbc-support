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
public enum Order {
  ASC("ASC"),
  DESC("DESC");
  
  private final String name;
  
  Order(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
}
