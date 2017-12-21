# Spring JDBC

## Prepare the environment

Add the following dependencies in your pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
</dependencies>
```

Create the Launcher class that will ([example here](../src/main/java/learn/Launcher.java))

- start the application context
- start the h2 console
- execute the application processing logic

Create the common configuration that we will use throughout the application ([example here](../src/main/java/learn/common/Config.java)):

```java
@Configuration
public class Config {
    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("script-learn1.sql")
                .generateUniqueName(true)
                .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

For the embedded database, I used `EmbeddedDatabaseBuilder` class from spring JDBC, and configure it to work with **H2** database engine. Other possibilities are **HSQL** and **DERBY**.

> Notice:
> - the script used to initialize the database (`.addScript("script-learn1.sql")`) with some dummy data in it. To generate data for yourself, you can use http://www.mockaroo.com/ . See [the file](../src/main/resources/script-learn1.sql)
> - the `.generateUniqueName(true)` flag that will ensure us a new database will be created every time we start the application.

Also the `JdbcTemplate` bean that will be used to make database operations.

## JdbcTemplate basics

### Simple database queries 

See [Learn1Config example](../src/main/java/learn/learn1/Learn1Config.java)

When running the application with `learn1` parameter, we will print the number of rows from the LOG_DATA table.

### Using parameters in query

See [Learn2Config example](../src/main/java/learn/learn2/Learn2Config.java)
When running the application with `learn2` parameter, we will print the name of all the users online on 7/11/2017.

See [Learn3Config example](../src/main/java/learn/learn3/Learn3Config.java)
When running the application with `learn3` parameter, we will print the name of all the users online on 30/11/2017 and 01/12/2017.

**Difference between `NamedParameterJdbcTemplate` and `JdbcTemplate`**

The obvious difference between these 2 approaches to substitute parameters in queries is the use of named parameters in the first object.

While the JdbcTemplate only accepts an Object[] as a parameter, meaning that the order inside the object array should match with the order in the sql query, the NamedParameterJdbcTemplate allows you to use a map of parameter names, thus making the program less error prone.

Notes:

- use JdbcTemplate for simple operations, as is slightly more optimal (counts, selects with few parameters)
- use NamedParameterJdbcTemplate when you have queries with more parameters (insert statements for example) or parameters that you cannot map in simple JdbcTemplate, like `WHERE IN` clause.

### The update operation

See [Learn4Config example](../src/main/java/learn/learn4/Learn4Config.java)

When selecting from a database, we have the option to use a `RowMapper` in order to map the result directly to an Java object:

```java
public interface RowMapper<T> {
	T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
```

The following query in JdbcTemplate will return a `List<CountPerName>`:

```java
template.query("SELECT full_name, COUNT(*) as `day_count` FROM log_data GROUP BY full_name",
                (rs, i) -> new CountPerName(rs.getString("full_name"), rs.getInt("day_count")));
```

In order to execute update/insert operations, JdbcTemplate has the update method:

```java
template.update("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", countPerName.getName(), countPerName.getPercent());
```

When you have to update/insert multiple items, it is faster to execute them in a batch operations, instead of adding them one at a time. For this, JdbcTemplate offers batchUpdate methods:

See [Learn5Config example](../src/main/java/learn/learn5/Learn5Config.java)

The following method will prepare one batch, and will execute the batch operation in order to add all items at once. The return of batchUpdate operation is an array with the number of rows affected by each item in the list

```java
private Integer batchInsertBenefits(List<CountPerName> countPerNames) {
    List<Object[]> batches = new ArrayList<>();
    for(CountPerName countPerName : countPerNames) {
        batches.add(new Object[]{countPerName.getName(), countPerName.getPercent()});
    }
    int[] result = template.batchUpdate("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", batches);
    return countPerNames.size();
}
```

However, sometimes we need to split the batches in multiple parts. The easy way to do this is using a `ParameterizedPreparedStatementSetter`. The return of this batchUpdate operation is an array containing for each batch another array containing the numbers of rows affected by each update in the batch.

```java
private Integer batchInsertBenefits2(List<CountPerName> countPerNames) {
    int[][] result = template.batchUpdate("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", countPerNames, 50, (ps, arg) -> {
        ps.setString(1, arg.getName());
        ps.setDouble(2, arg.getPercent());
    });
    return countPerNames.size();
}
```

This way we can check if the batch operation was successful, and also what element in the batch operation failed.
