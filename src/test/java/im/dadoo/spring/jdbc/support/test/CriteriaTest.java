/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.spring.jdbc.support.test;

import im.dadoo.spring.jdbc.support.Criteria;
import im.dadoo.spring.jdbc.support.Pair;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Conditions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author shuwen.zsw
 */
public class CriteriaTest {

  @Test
  public void test_where() {
    List<Condition> conds = new ArrayList<>();
    conds.add(Conditions.eq("create_date"));
    conds.add(Conditions.in("name"));
    conds.add(Conditions.between("end_date"));
    conds.add(Conditions.isNull("deleted"));
    Assert.assertEquals("WHERE create_date=:create_date AND name IN (:name) AND end_date BETWEEN :end_date_1 AND :end_date_2 AND deleted IS NULL", Criteria.where(conds));
  }
  
  @Test
  public void test_order() {
    List<Pair<String, String>> orders = new ArrayList<>();
    orders.add(new Pair("id", Criteria.DESC));
    orders.add(new Pair("name", Criteria.ASC));
    System.out.println(Criteria.orderBy(orders));
  }
  
  @Test
  public void test_set() {
    List<String> fields = new ArrayList<>();
    Assert.assertEquals("", Criteria.set(fields));
    fields.add("name");
    Assert.assertEquals("SET name=:name", Criteria.set(fields));
    fields.add("date");
    Assert.assertEquals("SET name=:name,date=:date", Criteria.set(fields));
  }
}
