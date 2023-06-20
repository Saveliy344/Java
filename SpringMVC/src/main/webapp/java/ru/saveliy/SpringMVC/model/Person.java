package ru.saveliy.SpringMVC.model;

import javax.validation.constraints.*;

public class Person {
    //id человека
    private int id;
    //ФИО
    @NotEmpty(message = "ФИО не должно быть пустым!")
    @Pattern(regexp =  "[A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+", message = "ФИО должны быть в формате: Фамилия Имя Отчество.")
    private String fullName;
    //Год рождения
    @Min(value = 1901, message = "Год рождения должен быть больше 1900.")
    private int year;

    public Person() {
        this.id = 0;
        this.fullName = null;
        this.year = 0;
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
}
