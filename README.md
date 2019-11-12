# MySQL Database connection and pool configuration

Primarily for my own reference, to help Spring projects

So far, my feasible choices are:
* Raw JDBC Connection - JdbcConnection
* Hibernate ORM - HibernateConnection
* Database Migration with Flyway - FlywayMigration
* JOOQ - Java Object Oriented Querying as DSL - JooqConnection

DSL: domain-specific language

### JDBC Connection Pool notes:

C3PO (comboPooled)
- https://www.mchange.com/projects/c3p0/

DBCP (Apache Commons Basic Datasource for Database Connection Pool)
- https://commons.apache.org/proper/commons-dbcp/download_dbcp.cgi

Tomcat dbcp
- https://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html
- https://stackoverflow.com/questions/4711943/tomcat-dbcp-vs-commons-dbcp (rename of Apache DBCP)

BoneCP
- https://github.com/wwadge/bonecp

HikariCP
- https://github.com/brettwooldridge/HikariCP

H2-jdbc Connection Pool
- https://www.javatips.net/blog/h2-database-connection-pool-example


Slow: DriverManagerDataSource
- `import org.springframework.jdbc.datasource.DriverManagerDataSource`; Not really cp setup
- http://romacodewiki.blogspot.com/2015/03/java-spring-drivermanagerdatasource-vs.html


References:
* https://www.baeldung.com/java-connection-pooling
* http://blog.didispace.com/Springboot-2-0-HikariCP-default-reason/
* https://www.javatips.net/blog/bonecp-connection-pooling-example
* https://www.developer.com/java/data/understanding-jdbc-connection-pooling.html

---

last updated on Nov-2019
