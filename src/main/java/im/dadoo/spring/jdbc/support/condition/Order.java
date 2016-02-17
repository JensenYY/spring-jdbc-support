package im.dadoo.spring.jdbc.support.condition;

/**
 * Created by codekitten on 16/2/16.
 *
 */
public enum Order {

  ASC("ASC"),
  DESC("DESC");

  private final String name;

  private Order(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
