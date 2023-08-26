package ru.saveliy.SpringMVC.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.saveliy.SpringMVC.dao.BookDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.saveliy.SpringMVC.dao.PersonDAO;
import ru.saveliy.SpringMVC.model.Book;
import ru.saveliy.SpringMVC.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", this.bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = this.bookDAO.show(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());
        model.addAttribute("chosenPerson", new Person());
        model.addAttribute("person", bookDAO.getOwner(book));
        return "books/show";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                       BindingResult result) {
        if (result.hasErrors()) return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping( "/free")
    public String free(@ModelAttribute("book") Book book) {
        System.out.println(book.getId());
        bookDAO.makeFree(book.getId());
        return "redirect:/books/" + book.getId();
    }

    @PatchMapping("/{id}/give")
    public String giveBook(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.giveToPerson(id, person.getFullName());
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}