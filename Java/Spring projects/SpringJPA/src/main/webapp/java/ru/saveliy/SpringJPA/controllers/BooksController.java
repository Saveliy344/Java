package ru.saveliy.SpringJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.saveliy.SpringJPA.model.Book;
import ru.saveliy.SpringJPA.model.Person;
import ru.saveliy.SpringJPA.services.BooksService;
import ru.saveliy.SpringJPA.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer books_per_page, @RequestParam(required = false) boolean sort_by_year) {
        model.addAttribute("books", this.booksService.index(page, books_per_page, sort_by_year));
        return "books/index";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String query){
        model.addAttribute("books", this.booksService.search(query));
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = this.booksService.show(id);
        model.addAttribute("book", book);
        model.addAttribute("people", peopleService.index());
        model.addAttribute("chosenPerson", new Person());
        model.addAttribute("person", book.getOwner());
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
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = booksService.show(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                       BindingResult result) {
        if (result.hasErrors()) return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping( "/free")
    public String free(@ModelAttribute("book") Book book) {
        System.out.println(book.getId());
        booksService.makeFree(book.getId());
        return "redirect:/books/" + book.getId();
    }

    @PatchMapping("/{id}/give")
    public String giveBook(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.giveToPerson(id, person.getFullName());
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}