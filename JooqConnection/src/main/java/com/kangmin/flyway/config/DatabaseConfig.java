package com.kangmin.flyway.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence-mysql.properties")
@EnableTransactionManagement
public class DatabaseConfig {

    private final Environment env;

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource constructDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("jdbc.url"));
        config.setUsername(env.getProperty("jdbc.user"));
        config.setPassword(env.getProperty("jdbc.password"));
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
