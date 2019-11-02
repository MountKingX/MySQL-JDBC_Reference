package com.kangmin.flyway;


import com.kangmin.flyway.config.AppConfig;
import com.kangmin.flyway.model.Person;
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
