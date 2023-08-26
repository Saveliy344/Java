package ru.saveliy.SpringJPA.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    //id человека
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    //ФИО
    @NotEmpty(message = "ФИО не должно быть пустым!")
    @Pattern(regexp =  "[A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+", message = "ФИО должны быть в формате: Фамилия Имя Отчество.")
    @Column(name = "fullName")
    private String fullName;
    //Год рождения
    @Min(value = 1901, message = "Год рождения должен быть больше 1900.")
    @Column(name = "birthYear")
    private int year;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> books;

    public Person() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
