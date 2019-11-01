package com.kangmin.hibernate.dao;

import com.kangmin.hibernate.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    private final SessionFactory sessionFactory;

    public PersonDaoImpl(final SessionFactory sessionFactory) {
        this.sessionFactory =sessionFactory;
    }

    @Override
    @Transactional
    public void save(Person p) {
        Session session = sessionFactory.getCurrentSession();
        session.save(p);
    }

    @Override
    @Transactional
    public List<Person> getPersons() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Person");
        List<Person> result = (List<Person>) query.getResultList();
        return result;
    }
}
