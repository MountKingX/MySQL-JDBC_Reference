[Warning] Unable to resolve location classpath:db/migration 

Flyway is looking for .sql files under directory `db/migration` by default
* better to create package db first then create sub migration
* not create `db.migration` by once
