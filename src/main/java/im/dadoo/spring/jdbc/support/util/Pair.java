/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.util;

import java.io.Serializable;

/**
 * <p>Pair is used to define sql parameter which contains two field.</p>
 * <p>For example, BETWEEN clause contains two fields such as begin and end</p>
 * @author codekitten
 * @since 0.1
 */
public final class Pair<V1, V2> implements Serializable {
  
  private static final long serialVersionUID = -6106927893230115331L;
  
  private V1 v1;
  private V2 v2;
  
  public Pair(V1 v1, V2 v2) {
    this.v1 = v1;
    this.v2 = v2;
  }
  
  /**
   * This function used to create a new Pair object.
   * 
   * @param v1 left value
   * @param v2 right value
   * @return pair object
   * @since 0.1
   */
  public static <V1, V2> Pair<V1, V2> of(V1 v1, V2 v2) {
    return new Pair<>(v1, v2);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Pair [v1=");
    builder.append(v1);
    builder.append(", v2=");
    builder.append(v2);
    builder.append("]");
    return builder.toString();
  }

  public V1 getV1() {
    return v1;
  }

  public void setV1(V1 v1) {
    this.v1 = v1;
  }

  public V2 getV2() {
    return v2;
  }

  public void setV2(V2 v2) {
    this.v2 = v2;
  }
  
}
