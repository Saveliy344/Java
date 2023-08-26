package ru.saveliy.sensorAPI.utils.exceptions;

public class SensorRegistrationError extends RuntimeException {
    public SensorRegistrationError(String error){
        super(error);
    }
}
