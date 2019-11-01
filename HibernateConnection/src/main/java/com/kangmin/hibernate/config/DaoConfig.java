package com.kangmin.hibernate.config;

import com.kangmin.hibernate.dao.PersonDao;
import com.kangmin.hibernate.dao.PersonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Import({DatabaseConfig.class})
@Configuration
public class DaoConfig {

    final LocalSessionFactoryBean localSessionFactoryBean;

    @Autowired
    public DaoConfig(LocalSessionFactoryBean localSessionFactoryBean) {
        this.localSessionFactoryBean = localSessionFactoryBean;
    }

    @Bean
    public PersonDao personDao() {
        return new PersonDaoImpl(localSessionFactoryBean.getObject());
    }
}
