package ru.saveliy.sensorAPI.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorDTO {
    @Size(min = 3, max = 30, message = "The size of name should be from 3 to 30 chars!")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDTO(String name) {
        this.name = name;
    }
    public SensorDTO(){}
}
