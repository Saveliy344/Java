package ru.saveliy.SpringJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saveliy.SpringJPA.model.Person;


public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findPeopleByFullName(String fullName);
}
