package com.kangmin.flyway.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

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
        // https://github.com/wwadge/bonecp
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        dataSource.setAcquireIncrement(1);
        dataSource.setAcquireRetryAttempts(2);
        dataSource.setAcquireRetryDelayInMs(2000);
        dataSource.setIdleConnectionTestPeriodInMinutes(1);
        dataSource.setIdleMaxAgeInMinutes(10);
        dataSource.setMaxConnectionsPerPartition(2);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(1);
        dataSource.setStatementsCacheSize(100);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(constructDataSource());
        sessionFactory.setPackagesToScan("com.kangmin.hibernate.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        final Properties p = new Properties();
        p.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        p.getProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        p.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        p.setProperty("hibernate.globally_quoted_identifiers", "true");
        return p;
    }

    private int getIntProperty(String propName) {
        final String propVal = env.getProperty(propName);
        assert propVal != null;
        return Integer.parseInt(propVal);
    }
}
