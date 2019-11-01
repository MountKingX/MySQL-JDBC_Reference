package com.kangmin.hibernate;


import com.kangmin.hibernate.config.AppConfig;
import com.kangmin.hibernate.dao.PersonDao;
import com.kangmin.hibernate.model.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public final class App {
    public static void main(final String[] args) {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        final Person p = context.getBean("myPerson", Person.class);
        System.out.println(p.getAge());
        System.out.println(p.getName());

        final PersonDao dao = context.getBean("personDao", PersonDao.class);
        dao.save(p);
        List<Person> list = dao.getPersons();
        list.forEach(each -> System.out.println(each.getName()));
    }
}
