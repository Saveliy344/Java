package ru.saveliy.SpringMVC.model;

import javax.validation.constraints.*;

public class Book {
    //id книги
    private int id;
    //Название книги
    @NotEmpty(message = "Название книги не должно быть пусытм!")
    private String name;
    //Год выпуска
    @Min(value = 0, message = "Возраст должен быть неотрицательным!")
    private int year;
    //Автор книги
    @NotEmpty(message = "Автор не может быть пустым!")
    private String author;
    //id читателя (может быть 0, если книга находится в библиотеке)
    private int readerId;

    public Book() {
        this.id = 0;
        this.name = null;
        this.year = 0;
        this.author = null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}
