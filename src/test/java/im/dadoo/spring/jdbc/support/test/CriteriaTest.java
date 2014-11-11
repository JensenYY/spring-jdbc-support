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

import org.junit.Test;

/**
 *
 * @author shuwen.zsw
 */
public class CriteriaTest {
  
  @Test
  public void testWhere() {
    List<Condition> conds = new ArrayList<Condition>();
    conds.add(Conditions.eq("id"));
    conds.add(Conditions.between("utc"));
    conds.add(Conditions.in("name"));
    System.out.println(Criteria.where(conds));
  }
  
  @Test
  public void testOrderBy() {
    List<Pair<String, String>> orders = new ArrayList<>();
    orders.add(Pair.of("name", "DESC"));
    orders.add(Pair.of("id", "ASC"));
    System.out.println(Criteria.orderBy(orders));
  }
  
}
