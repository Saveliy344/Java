package ru.saveliy.SpringJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.saveliy.SpringJPA.model.Person;
import ru.saveliy.SpringJPA.services.PeopleService;
import ru.saveliy.SpringJPA.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", this.peopleService.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.peopleService.show(id));
        model.addAttribute("books", this.peopleService.getBooks(id));
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
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Person person = peopleService.show(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult result) {
        personValidator.validate(person, result);
        if (result.hasErrors()) return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}