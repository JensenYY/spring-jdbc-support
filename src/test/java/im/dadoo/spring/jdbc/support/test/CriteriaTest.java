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
  public void whereOne() {
    List<Condition> conds = new ArrayList<Condition>();
    conds.add(Conditions.eq("create_date"));
    Assert.assertEquals("WHERE create_date = :create_date", Criteria.where(conds).trim());
  }
  
  @Test
  public void testIsNull() {
    List<Condition> conds = new ArrayList<Condition>();
    conds.add(Conditions.isNull("name"));
    //System.out.println(Criteria.where(conds));
    Assert.assertEquals("WHERE name IS NULL", Criteria.where(conds).trim());
  }
  
}
