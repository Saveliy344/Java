package ru.saveliy.sensorAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurementDTO {
    @Min(value = -100, message = "Value must be more than or equal -100!")
    @Max(value = 100, message = "Value must be less then or equal 100!")
    @NotNull(message = "Field value should not be null!")
    private Double value;
    @NotNull(message = "Field raining should not be null!")
    private Boolean raining;
    @NotEmpty(message = "Field sensor must not be empty!")
    private Map<String, String> sensor;

    public MeasurementDTO() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Map<String, String> getSensor() {
        return sensor;
    }

    public void setSensor(Map<String, String> sensor) {
        this.sensor = sensor;
    }
}
