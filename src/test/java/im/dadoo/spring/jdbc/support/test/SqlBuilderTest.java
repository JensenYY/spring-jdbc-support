/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.spring.jdbc.support.test;

import im.dadoo.spring.jdbc.support.SqlBuilder;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Conditions;
import im.dadoo.spring.jdbc.support.condition.Order;
import im.dadoo.spring.jdbc.support.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author codekitten
 * @since 0.1
 */
public class SqlBuilderTest {
  
  private static final String TABLE = "t_article";
  
  private final String table;
  private final List<String> fields;
  private final List<Condition> conditions;
  private final List<Pair<String, Order>> orders;
  
  public SqlBuilderTest() {
    this.table = "t_article";
    
    this.fields = new ArrayList<>();
    fields.add("title");
    fields.add("content");
    fields.add("date");
    fields.add("click");
    
    this.conditions = new ArrayList<>();
    conditions.add(Conditions.eq("author"));
    conditions.add(Conditions.ge("date"));
    
    this.orders = new ArrayList<>();
    this.orders.add(Pair.of("id", Order.DESC));
    this.orders.add(Pair.of("title", Order.ASC));
  }
  
  @Test
  public void test_buildInsertSql() {
    String sql = SqlBuilder.buildInsertSql(this.table, this.fields);
    Assert.assertEquals("INSERT INTO t_article(title,content,date,click) VALUES(:title,:content,:date,:click)", sql);
    
    Map<String, String> valueMap = new HashMap<>();
    valueMap.put("date", "CURRENT_TIMESTAMP");
    sql = SqlBuilder.buildInsertSql(this.table, this.fields, valueMap);
    Assert.assertEquals("INSERT INTO t_article(title,content,date,click) VALUES(:title,:content,CURRENT_TIMESTAMP,:click)", sql);
  }
  
  @Test
  public void test_buildUpdateByIdSql() {
    String sql = SqlBuilder.buildUpdateByIdSql(this.table, this.fields);
    Assert.assertEquals("UPDATE t_article SET title = :title,content = :content,date = :date,click = :click WHERE id = :id", sql);
  }
  
  @Test
  public void test_buildUpdateSql() {
    String sql = SqlBuilder.buildUpdateSql(this.table, this.fields, this.conditions);
    Assert.assertEquals("UPDATE t_article SET title = :title,content = :content,date = :date,click = :click WHERE author = :author AND date >= :date", sql);
    
    Map<String, String> valueMap = new HashMap<>();
    valueMap.put("date", "CURRENT_TIMESTAMP");
    sql = SqlBuilder.buildUpdateSql(table, fields, valueMap, conditions);
    Assert.assertEquals("UPDATE t_article SET title = :title,content = :content,date = CURRENT_TIMESTAMP,click = :click WHERE author = :author AND date >= :date", sql);
  }
  
  @Test
  public void test_buildListSql() {
    String sql = SqlBuilder.buildListSql(this.table, this.conditions, this.orders, 1, 10);
    Assert.assertEquals("SELECT * FROM t_article WHERE author = :author AND date >= :date ORDER BY id DESC,title ASC LIMIT 0, 10", sql);
    //System.out.println(sql);
  }
  
  @Test
  public void test_buildSizeSql() {
    String sql = SqlBuilder.buildSizeSql(this.table, this.conditions);
    Assert.assertEquals("SELECT count(*) as size FROM t_article WHERE author = :author AND date >= :date", sql);
    //System.out.println(sql);
  }
}
