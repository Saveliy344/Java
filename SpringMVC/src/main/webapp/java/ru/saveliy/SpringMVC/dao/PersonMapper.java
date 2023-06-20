package ru.saveliy.SpringMVC.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.saveliy.SpringMVC.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFullName(rs.getString("fullName"));
        person.setYear(rs.getInt("birthYear"));
        return person;
    }
}
