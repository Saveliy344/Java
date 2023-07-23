package ru.saveliy.SpringJPA.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name= "book")
public class Book {
    //id книги
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    //Название книги
    @NotEmpty(message = "Название книги не должно быть пустым!")
    @Column(name="name")
    private String name;
    //Год выпуска
    @Min(value = 0, message = "Возраст должен быть неотрицательным!")
    @Column(name="year")
    private int year;
    //Автор книги
    @NotEmpty(message = "Автор не может быть пустым!")
    @Column(name="author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "readerId", referencedColumnName = "id")
    private Person owner;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="taken")
    private Date taken;

    @Transient
    private boolean isOverDue;

    public Book() {
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTaken() {
        return taken;
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }

    public boolean isOverDue() {
        if (this.getTaken() == null) return false;
        long differenceInMilliseconds = Math.abs(new Date().getTime() - this.getTaken().getTime());
        long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);
        return differenceInDays >= 10;
    }
}
