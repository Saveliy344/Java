package ru.saveliy.SpringMVC.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.saveliy.SpringMVC.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setYear(rs.getInt("year"));
        book.setAuthor(rs.getString("author"));
        book.setReaderId(rs.getInt("readerId"));
        return book;
    }
}
