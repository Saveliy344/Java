package ru.saveliy.SpringJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saveliy.SpringJPA.model.Book;
import ru.saveliy.SpringJPA.model.Person;
import ru.saveliy.SpringJPA.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;


    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }


    @Transactional(readOnly = true)
    public List<Book> index(Integer page, Integer books_per_page, boolean sort_by_year) {
        //Номер страницы должен быть хотя бы 0, количество страниц - хотя бы 1
        if (page != null && page < 0) page = null;
        if (books_per_page != null && books_per_page < 1) books_per_page = null;
        //Передаются сортировка и пагинация
        if (page != null && books_per_page != null && sort_by_year) return booksRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
        //Передаётся только пагинация
        if (page != null && books_per_page != null) return booksRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        //Передаётся только сортировка
        if (sort_by_year) return booksRepository.findAll(Sort.by("year"));
        return booksRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> search(String query){
        //Если поисковый запрос пустой, возвращаются все книги
        if (query == null) return booksRepository.findAll();
        return booksRepository.findAllByNameStartingWith(query);
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        booksRepository.save(book);
    }

    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    public void makeFree(int id) {
        Book book = this.show(id);
        if (book == null) return;
        Person owner = book.getOwner();
        if (owner == null) return;
        owner.getBooks().remove(book);
        book.setOwner(null);
        book.setTaken(null);
    }

    public void giveToPerson(int id, String personFullName) {
        Person person = peopleService.show(personFullName);
        Book book = this.show(id);
        if (person == null || book == null) return;
        if (person.getBooks() == null) {
            person.setBooks(new ArrayList<>());
        }
        person.getBooks().add(book);
        book.setOwner(person);
        book.setTaken(new Date());
    }

    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
