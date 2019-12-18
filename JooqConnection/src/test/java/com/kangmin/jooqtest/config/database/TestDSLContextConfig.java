package com.kangmin.jooqtest.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence-mysql.properties")
@ComponentScan({"com.kangmin.jooqtest"})
@EnableTransactionManagement
public class TestDSLContextConfig {

    private final Environment env;

    @Autowired
    public TestDSLContextConfig(final Environment env) {
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

    @Bean
    @Qualifier("testDSLContext")
    public DefaultDSLContext testDSLContext() {
        return new DefaultDSLContext(testConfiguration());
    }

    @Bean
    public DefaultConfiguration testConfiguration() {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        // DataSourceConnectionProvider
        jooqConfiguration.set(testConnectionProvider());
        // ExceptionTranslator
        jooqConfiguration.set(new DefaultExecuteListenerProvider(testExceptionTransformer()));
        // SQLDialect
        final String sqlDialectName = env.getRequiredProperty("jooq.sql.dialect");
        final SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);
        return jooqConfiguration;
    }

    @Bean
    public DataSourceConnectionProvider testConnectionProvider() {
        return new DataSourceConnectionProvider(testTransactionAwareDataSource());
    }

    @Bean
    public ExceptionTranslator testExceptionTransformer() {
        return new ExceptionTranslator();
    }

    @Bean
    public TransactionAwareDataSourceProxy testTransactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(testDataSource());
    }

    @Bean
    public DataSourceTransactionManager testTransactionManager() {
        return new DataSourceTransactionManager(testDataSource());
    }

    private int getIntProperty(String propName) {
        final String propVal = env.getProperty(propName);
        assert propVal != null;
        return Integer.parseInt(propVal);
    }
}
