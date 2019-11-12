package com.kangmin.jooqtest;


import com.kangmin.jooqtest.config.AppConfig;
import com.kangmin.jooqtest.model.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public final class App {
    public static void main(final String[] args) {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        final Person p = context.getBean("myPerson", Person.class);
        System.out.println(p.getAge());
        System.out.println(p.getName());
    }
}
