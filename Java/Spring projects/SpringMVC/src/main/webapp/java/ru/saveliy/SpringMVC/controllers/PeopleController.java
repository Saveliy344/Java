package ru.saveliy.SpringMVC.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.saveliy.SpringMVC.dao.PersonDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.saveliy.SpringMVC.model.Person;
import ru.saveliy.SpringMVC.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", this.personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.personDAO.show(id));
        model.addAttribute("books", this.personDAO.getBooks(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult result) {
        personValidator.validate(person, result);
        if (result.hasErrors()) return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Person person = personDAO.show(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult result) {
        personValidator.validate(person, result);
        if (result.hasErrors()) return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}