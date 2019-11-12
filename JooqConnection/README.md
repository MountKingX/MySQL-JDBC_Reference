# JooqConnection (Spring Style)

* Configuration of Jooq
* Add flyway bean


Still have things to do if add <inputCatalog>:
<inputCatalog>${test.db.schema}</inputCatalog>
[WARNING] No catalogs were loaded  : Your database reported only a default catalog, which was filtered by your <inputCatalog/> co
nfigurations. jOOQ does not support catalogs for all databases, in case of which <inputCatalog/> configurations will not work.


Careful:

[WARNING] Unable to resolve location classpath:db/migration 

Flyway is looking for .sql files under directory `db/migration` by default
* better to create package db first then create sub migration
* not create `db.migration` by once


---
last updated on Mov-2019

Reference:

- https://www.jianshu.com/p/46164f9ba53c
- https://www.jianshu.com/p/d471b9a6fbda
- https://amao12580.github.io/post/2016/04/JOOQ-from-entry-to-improve/
- https://github.com/eugenp/tutorials/blob/master/spring-jooq/src/test/java/com/baeldung/jooq/introduction/PersistenceContextIntegrationTest.java
- https://github.com/amao12580/JOOQ
- https://github.com/amao12580/JOOQ-With-Spring/tree/master/src/test/java/org/jooq/example
- https://www.petrikainulainen.net/programming/jooq/using-jooq-with-spring-configuration/
