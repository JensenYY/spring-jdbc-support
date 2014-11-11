/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.condition;

import java.io.Serializable;

/**
 * 描述sql中where的查询条件
 * @author shuwen.zsw
 */
public class Condition implements Serializable {

  private static final long serialVersionUID = 6471949889767283512L;

  /** 查询条件中的字段名  */
  protected String    field;
  
  /** 查询条件中的操作方式，例如=,<,>等 */
  protected Operation op;
  
  /** 查询条件中的值  */
  protected Object    value;
  
  public Condition() {
  }

  public Condition(String field, Operation op, Object value) {
    this.field = field;
    this.op = op;
    this.value = value;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Condition [field=");
    builder.append(field);
    builder.append(", op=");
    builder.append(op);
    builder.append(", value=");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

  public String getField() {
    return field;
  }
  
  public void setField(String field) {
    this.field = field;
  }
  
  public Operation getOp() {
    return op;
  }
  
  public void setOp(Operation op) {
    this.op = op;
  }
  
  public Object getValue() {
    return value;
  }
  
  public void setValue(Object value) {
    this.value = value;
  }
  
  
}
