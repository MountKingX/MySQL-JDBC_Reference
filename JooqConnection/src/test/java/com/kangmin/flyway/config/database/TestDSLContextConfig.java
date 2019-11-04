package com.kangmin.flyway.config.database;

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
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Import({
        TestDataSourceConfig.class
})
@Configuration
@ComponentScan({"com.kangmin.flyway"})
@EnableTransactionManagement
public class TestDSLContextConfig {

    private final Environment env;
    private final DataSource dataSource;

    @Autowired
    public TestDSLContextConfig(final Environment env,
                                @Qualifier("testDataSource")final DataSource testDataSource) {
        this.env = env;
        this.dataSource = testDataSource;
    }

    @Bean
    @Qualifier("testDSLContext")
    public DefaultDSLContext testDSLContext() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DefaultConfiguration configuration() {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        // DataSourceConnectionProvider
        jooqConfiguration.set(connectionProvider());
        // ExceptionTranslator
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
        // SQLDialect
        final String sqlDialectName = env.getRequiredProperty("jooq.sql.dialect");
        final SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);
        return jooqConfiguration;
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
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
