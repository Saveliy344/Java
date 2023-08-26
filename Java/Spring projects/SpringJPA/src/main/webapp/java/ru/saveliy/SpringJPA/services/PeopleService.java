package ru.saveliy.SpringJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saveliy.SpringJPA.model.Book;
import ru.saveliy.SpringJPA.model.Person;
import ru.saveliy.SpringJPA.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Person show(String fullName) {
        return peopleRepository.findPeopleByFullName(fullName);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(int id) {
        return peopleRepository.findById(id).map(Person::getBooks).orElse(null);
    }

    public void save(Person person) {
        peopleRepository.save(person);
    }

    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
