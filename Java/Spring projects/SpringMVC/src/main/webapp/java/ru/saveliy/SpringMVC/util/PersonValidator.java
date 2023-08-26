package ru.saveliy.SpringMVC.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.saveliy.SpringMVC.dao.PersonDAO;
import ru.saveliy.SpringMVC.model.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator{

    private PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> personCheck = personDAO.show(person.getFullName());
        if (personCheck.isPresent() && personCheck.get().getId() != ((Person) o).getId()){
            errors.rejectValue("fullName", "", "Данные ФИО уже заняты!");
        }
    }
}