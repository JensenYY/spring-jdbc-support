Spring Jdbc Support
===================
[![Maven central](https://maven-badges.herokuapp.com/maven-central/im.dadoo/spring-jdbc-support/badge.svg)](https://maven-badges.herokuapp.com/maven-central/im.dadoo/spring-jdbc-support)
[![Build Status](https://travis-ci.org/dadooteam/spring-jdbc-support.svg?branch=master)](https://travis-ci.org/dadooteam/spring-jdbc-support)

##Introduction
dynamically build "where" clause for spring jdbc.

dynamically build "set" clause for spring jdbc.

dynamically build "order by" clause for spring jdbc.


##License
The Apache License, Version 2.0

##Version
0.3

##Downloads
	<dependency>
	  <groupId>im.dadoo</groupId>
	  <artifactId>spring-jdbc-support</artifactId>
	  <version>0.3</version>
	</dependency>

##Example
```java
private static final String LIST_SQL_TPL = "SELECT * FROM article %s %s LIMIT :offset,:pagesize";

private static final String UPDATE_SQL_TPL = "UPDATE article %s WHERE id=:id";

private RowMapper<Article> mainRowMapper = new ArticleRowMapper();

public List<Article> list(Map<String, Object> paramMap, 
          List<Pair<String, Order>> orders, 
          int pagecount, int pagesize) {
    List<Article> result = Lists.newArrayList();
    List<Condition> conditions = Lists.newArrayList();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    if (paramMap.containsKey("title")) {
      conditions.add(Conditions.like("title"));
      sps.addValue("title", paramMap.get("title"));
    }
    if (paramMap.containsKey("click")) {
      conditions.add(Conditions.ge("click"));
      sps.addValue("click", paramMap.get("click"));
    }
    String where = Criteria.where(conditions);
    List<String> fields = new ArrayList<>();
    for (Pair<String, Order> order : orders) {
        fields.add(order.getV1());
        sps.addValue(String.format("order@%s",order.getV1()), order.getV2().getName());
    }
    sps.addValue("offset", (pagecount - 1) * pagesize);
    sps.addValue("pagesize", pagesize);
    String orderBy = Criteria.orderBy(fields);
    String sql = String.format(LIST_SQL_TPL, where, orderBy);
    result = this.jdbcTemplate.query(sql, sps, this.mainRowMapper);
    return result;
}

public void update(Map<String, Object> updateMap) {    
    List<String> fields = new ArrayList<>();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    for (String key : updateMap.keySet()) {
      fields.add(key);
      sps.addValue(key, updateMap.get(key));
    }
    String update = Criteria.set(fields);
    String sql = String.format(UPDATE_SQL_TPL, update);
    result = this.jdbcTemplate.update(sql, sps);
    return result;
}

class ArticleRowMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
      Article article = new Article();
      article.setId(rs.getLong("id"));
      article.setTitle(rs.getString("title"));
      article.setContent(rs.getString("content"));
      article.setDate(rs.getDate("date"));
      article.setClick(rs.getLong("click"));
      return article;
    }
}

```
##ChangeLog