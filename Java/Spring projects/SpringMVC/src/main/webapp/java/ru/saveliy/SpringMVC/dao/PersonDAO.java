package ru.saveliy.SpringMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.saveliy.SpringMVC.model.Book;
import ru.saveliy.SpringMVC.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new PersonMapper()).stream().findFirst().orElse(null);
    }

    public Optional<Person> show(String fullName){
        return jdbcTemplate.query("SELECT * FROM person WHERE fullName = ?", new Object[]{fullName}, new PersonMapper()).stream().findFirst();
    }

    public List<Book> getBooks(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE readerId = ?", new Object[]{id},new BookMapper());
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fullName, birthYear) VALUES(?, ?)", person.getFullName(), person.getYear());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET fullName = ?, birthYear = ? WHERE id = ?", person.getFullName(), person.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

};