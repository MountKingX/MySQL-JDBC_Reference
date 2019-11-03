package com.kangmin.flyway.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
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
    public DataSource dataSource() {
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

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));

        String sqlDialectName = env.getRequiredProperty("jooq.sql.dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public ExceptionTranslator exceptionTransformer() {
        return new ExceptionTranslator();
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }
}
