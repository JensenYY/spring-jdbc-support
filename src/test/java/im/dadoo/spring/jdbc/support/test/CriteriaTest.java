/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.test;

import im.dadoo.spring.jdbc.support.Criteria;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Conditions;

import java.util.ArrayList;
import java.util.List;

import im.dadoo.spring.jdbc.support.condition.Order;
import im.dadoo.spring.jdbc.support.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author codekitten
 */
public class CriteriaTest {

  @Test
  public void test_where_1() {
    List<Condition> conds = new ArrayList<>();
    conds.add(Conditions.eq("create_date"));
    conds.add(Conditions.in("name"));
    conds.add(Conditions.between("end_date"));
    conds.add(Conditions.isNull("deleted"));
    Assert.assertEquals("WHERE create_date = :create_date AND name IN (:name) AND end_date BETWEEN :end_date_1 AND :end_date_2 AND deleted IS NULL", Criteria.where(conds));
  }
  
  @Test
  public void test_where_2() {
    List<Condition> conds = new ArrayList<>();
    conds.add(Conditions.notBetween("date"));
    Assert.assertEquals("WHERE date NOT BETWEEN :date_1 AND :date_2", Criteria.where(conds));
    conds.add(Conditions.notLike("name"));
    conds.add(Conditions.notIn("ids"));
    conds.add(Conditions.notEq("state"));
    Assert.assertEquals("WHERE date NOT BETWEEN :date_1 AND :date_2 AND name NOT LIKE :name AND ids NOT IN (:ids) AND state != :state", Criteria.where(conds));
  }
  
  @Test
  public void test_where_3() {
    List<Condition> conds = new ArrayList<>();
    Assert.assertEquals("", Criteria.where(conds));
  }

  @Test
  public void test_where_4() {
    List<Condition> conds = new ArrayList<>();
    conds.add(Conditions.eq("create_date"));
    List<String> queries = new ArrayList<>();
    queries.add("EXISTS(SELECT * FROM rtag WHERE rtag.article_id=article.id)");
    Assert.assertEquals("WHERE create_date = :create_date AND EXISTS(SELECT * FROM rtag WHERE rtag.article_id=article.id)", Criteria.where(conds, queries));
  }
  
  @Test
  public void test_orderBy() {
    List<Pair<String, Order>> fields = new ArrayList<>();
    fields.add(Pair.of("date", Order.DESC));
    Assert.assertEquals("ORDER BY date DESC", Criteria.orderBy(fields));
    fields.add(Pair.of("name", Order.ASC));
    Assert.assertEquals("ORDER BY date DESC,name ASC", Criteria.orderBy(fields));
  }
  
  @Test
  public void test_set() {
    List<String> fields = new ArrayList<>();
    fields.add("name");
    Assert.assertEquals("SET name = :name", Criteria.set(fields));
    fields.add("date");
    Assert.assertEquals("SET name = :name,date = :date", Criteria.set(fields));
    List<String> values = null;
    Assert.assertEquals("SET name = :name,date = :date", Criteria.set(fields, values));
    values = new ArrayList<>();
    values.add(null);
    values.add("a_date");
    Assert.assertEquals("SET name = :name,date = :a_date", Criteria.set(fields, values));
    values.set(0, "a_name");
    Assert.assertEquals("SET name = :a_name,date = :a_date", Criteria.set(fields, values));
  }
}
