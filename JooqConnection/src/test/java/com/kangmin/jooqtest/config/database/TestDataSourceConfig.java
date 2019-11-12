package com.kangmin.jooqtest.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence-mysql.properties")
public class TestDataSourceConfig {

    private final Environment env;

    @Autowired
    public TestDataSourceConfig(final Environment env) {
        this.env = env;
    }

    @Bean
    @Qualifier("testDataSource")
    public DataSource testDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("test.db.url"));
        config.setUsername(env.getProperty("test.db.user"));
        config.setPassword(env.getProperty("test.db.password"));
        config.addDataSourceProperty( "cachePrepStmts" ,
                env.getProperty("cachePrepStmts") );
        config.addDataSourceProperty( "prepStmtCacheSize" ,
                getIntProperty("prepStmtCacheSize") );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" ,
                getIntProperty("prepStmtCacheSqlLimit") );
        return new HikariDataSource(config);
    }

    private int getIntProperty(String propName) {
        final String propVal = env.getProperty(propName);
        assert propVal != null;
        return Integer.parseInt(propVal);
    }
}
