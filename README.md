Spring Jdbc Support
===================
[![Maven central](https://maven-badges.herokuapp.com/maven-central/im.dadoo/spring-jdbc-support/badge.svg)](https://maven-badges.herokuapp.com/maven-central/im.dadoo/spring-jdbc-support)

##Introduction
dynamically build "where" clause for spring jdbc.

dynamically build "set" clause for spring jdbc.

dynamically build "order by" clause for spring jdbc.

SqlBuilder can directly build "insert","update","select","delete" sql template for spring jdbc.

##License
The Apache License, Version 2.0

##Version
0.1

##Downloads
	<dependency>
	  <groupId>im.dadoo</groupId>
	  <artifactId>spring-jdbc-support</artifactId>
	  <version>0.1</version>
	</dependency>

##Example
```javascript
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import im.dadoo.spring.jdbc.support.SqlBuilder;
import im.dadoo.spring.jdbc.support.condition.Condition;
import im.dadoo.spring.jdbc.support.condition.Conditions;
import im.dadoo.spring.jdbc.support.condition.Order;
import im.dadoo.spring.jdbc.support.util.Pair;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

//We can use this class to query from database
public class ArticleDAO {

  //table name
  private static final String TABLE = "t_article";

  //base fields except "id"
  private static final List<String> FIELDS
          = Lists.newArrayList("title", "content", "date", "click");

  //INSERT INTO t_article(title,content,date,click) VALUES(:title,:content,:date,:click)
  private static final String INSERT_SQL = SqlBuilder.buildInsertSql(TABLE, FIELDS);
  //UPDATE t_article SET title=:title,content=:content,date=:date,click=:click WHERE id=:id
  private static final String UPDATE_BY_ID_SQL = SqlBuilder.buildUpdateByIdSql(TABLE, FIELDS);
  //UPDATE t_article SET title=:title,content=:content,date=:date,click=:click
  private static final String UPDATE_ALL_SQL = SqlBuilder.buildUpdateAllSql(TABLE, FIELDS);
  //DELETE FROM t_article WHERE id=:id
  private static final String DELETE_BY_ID_SQL = SqlBuilder.buildDeleteByIdSql(TABLE);
  //DELETE FROM t_article
  private static final String DELETE_ALL_SQL = SqlBuilder.buildDeleteAllSql(TABLE);
  //SELECT * FROM t_article WHERE id=:id
  private static final String FIND_BY_ID_SQL = SqlBuilder.buildFindByIdSql(TABLE);
  //SELECT count(*) as size FROM t_article
  private static final String SIZE_ALL_SQL = SqlBuilder.buildSizeAllSql(TABLE);

  //The SQL will be executed by jdbcTemplate
  @Resource
  private NamedParameterJdbcTemplate jdbcTemplate;
  //The mapper is to make relation between table and object
  private final RowMapper<Article> mainRowMapper = new ArticleRowMapper();
  //The mapper is to get size value from sql result
  private final RowMapper<Long> sizeRowMapper = new SizeRowMapper();

  /**
   * insert article object into table t_article
   * @param article 
   * @return if insert operation is successful, return article.id, else return 0
   */
  public long insert(Article article) {
    long result = 0L;
    checkNotNull(article);
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("title", article.getTitle());
    sps.addValue("content", article.getContent());
    sps.addValue("date", article.getDate());
    sps.addValue("click", article.getClick());
    this.jdbcTemplate.update(INSERT_SQL, sps, holder);
    return holder.getKey().longValue();
  }

  /**
   * update article record by id
   * @param article 
   */
  public void updateById(Article article) {
    checkNotNull(article);
    checkArgument(article.getId() > 0L);
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", article.getId());
    sps.addValue("title", article.getTitle());
    sps.addValue("content", article.getContent());
    sps.addValue("date", article.getDate());
    sps.addValue("click", article.getClick());
    this.jdbcTemplate.update(UPDATE_BY_ID_SQL, sps);
  }
  
  /**
   * delete article record by id
   * @param id 
   */
  public void deleteById(long id) {
    checkArgument(id > 0L);
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    this.jdbcTemplate.update(DELETE_BY_ID_SQL, sps);
  }
  
  /**
   * get single article record from database by id,
   * if multiply result or null result found, this function will throw exception
   * @param id
   * @return 
   */
  public Article findById(long id) {
    Article result = null;
    checkArgument(id > 0L);
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    result = this.jdbcTemplate.queryForObject(FIND_BY_ID_SQL, sps, this.mainRowMapper);
    return result;
  }
  
  /**
   * dynamically select records from database
   * @param paramMap based on paramMap, we can dynamically genenrate where clause
   * @param orders to generate ORDER BY clause
   * @param pagecount
   * @param pagesize
   * @return if query is successful, return records.Else return empty list.
   */
  public List<Article> list(Map<String, Object> paramMap, List<Pair<String, Order>> orders, 
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
    String sql = SqlBuilder.buildListSql(TABLE, conditions, orders, pagecount, pagesize);
    result = this.jdbcTemplate.query(sql, sps, this.mainRowMapper);
    return result;
  }
  
  /**
   * get record count
   * @return 
   */
  public long sizeAll() {
    return this.jdbcTemplate.queryForObject(SIZE_ALL_SQL, (MapSqlParameterSource)null, this.sizeRowMapper);
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
  
  class SizeRowMapper implements RowMapper<Long> {
    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getLong("size");
    }
  }
}
```
##ChangeLog