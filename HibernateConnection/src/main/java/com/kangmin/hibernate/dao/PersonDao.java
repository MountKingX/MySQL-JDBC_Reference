package com.kangmin.hibernate.dao;

import com.kangmin.hibernate.model.Person;

import java.util.List;

public interface PersonDao {

    void save(Person p);

    List<Person> getPersons();
}
