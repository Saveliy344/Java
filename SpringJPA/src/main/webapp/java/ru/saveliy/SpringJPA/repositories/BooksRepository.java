package ru.saveliy.SpringJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saveliy.SpringJPA.model.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    public List<Book> findAllByNameStartingWith(String name);
}
