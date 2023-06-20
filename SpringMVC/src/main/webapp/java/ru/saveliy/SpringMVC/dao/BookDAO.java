package ru.saveliy.SpringMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.saveliy.SpringMVC.model.Book;
import org.springframework.stereotype.Component;
import ru.saveliy.SpringMVC.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }
    public Person getOwner(Book book){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{book.getReaderId()}, new PersonMapper()).stream().findFirst().orElse(null);
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BookMapper()).stream().findFirst().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, year, author) VALUES(?, ?, ?)", book.getName(), book.getYear(), book.getAuthor());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, year = ?, author=? WHERE id = ?", book.getName(), book.getYear(), book.getAuthor(), id);
    }

    public void makeFree(int id){
        jdbcTemplate.update("UPDATE book SET readerId = null WHERE id = ?", id);
    }

    public void giveToPerson(int id, String personFullName){
        Optional<Person> person = personDAO.show(personFullName);
        int readerId = 0;
        if (person.isPresent()) readerId = person.get().getId();
        jdbcTemplate.update("UPDATE book SET readerId = ? WHERE id = ?", readerId, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

};