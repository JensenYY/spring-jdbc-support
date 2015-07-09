/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.test;

import im.dadoo.spring.jdbc.support.Criteria;
import im.dadoo.spring.jdbc.support.util.Pair;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Conditions;
import im.dadoo.spring.jdbc.support.condition.Order;

import java.util.ArrayList;
import java.util.List;

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
    System.out.println(Criteria.where(conds));
  }
  
  @Test
  public void test_orderBy() {
    List<Pair<String, Order>> orders = new ArrayList<>();
    orders.add(new Pair("id", Order.DESC));
    Assert.assertEquals("ORDER BY id DESC", Criteria.orderBy(orders));
    orders.add(new Pair("name", Order.ASC));
    Assert.assertEquals("ORDER BY id DESC,name ASC", Criteria.orderBy(orders));
    System.out.println(Criteria.orderBy(orders));
  }
  
  @Test
  public void test_set() {
    List<String> fields = new ArrayList<>();
    Assert.assertEquals(null, Criteria.set(fields));
    fields.add("name");
    Assert.assertEquals("SET name = :name", Criteria.set(fields));
    fields.add("date");
    Assert.assertEquals("SET name = :name,date = :date", Criteria.set(fields));
  }
}
