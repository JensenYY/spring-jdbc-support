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
0.5

##Downloads
	<dependency>
	  <groupId>im.dadoo</groupId>
	  <artifactId>spring-jdbc-support</artifactId>
	  <version>0.5</version>
	</dependency>

##Example
```java
private static final String LIST_SQL_TPL = "SELECT * FROM article %s %s LIMIT :offset,:length";

private static final String UPDATE_SQL_TPL = "UPDATE article %s WHERE id=:id";

private ArticleRowMapper mainRowMapper = new ArticleRowMapper();

public List<Article> page(Map<String, Object> params, List<Pair<String, Order>> orders, int pageCount, int pageSize) {
    List<Article> result = new ArrayList<>();
    List<Condition> conditions = new ArrayList<>();
    List<String> queries = new ArrayList<>();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    if (params.containsKey("title")) {
      conditions.add(Conditions.like("title"));
      sps.addValue("title", params.get("title"));
    }
    if (params.containsKey("click")) {
      conditions.add(Conditions.ge("click"));
      sps.addValue("click", params.get("click"));
    }
    queries.add("EXISTS(SELECT * FROM rtag WHERE rtag.article_id=article.id)");
    String where = Criteria.where(conditions);
    //SELECT * FROM article WHERE title LIKE :title AND click=:click AND EXISTS(SELECT * FROM rtag WHERE rtag.article_id=article.id) ORDER BY id DESC LIMIT :offset,:length
    sps.addValue("offset", (pageCount - 1) * pageSize);
    sps.addValue("length", pageSize);
    String orderBy = Criteria.orderBy(orders);
    String sql = String.format(LIST_SQL_TPL, where, orderBy);
    result = this.jdbcTemplate.query(sql, sps, this.mainRowMapper);
    return result;
}

public void update(Map<String, Object> params) {
    List<String> fields = new ArrayList<>();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    for (String key : params.keySet()) {
      fields.add(key);
      sps.addValue(key, params.get(key));
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

```
