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
