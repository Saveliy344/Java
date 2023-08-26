package ru.saveliy.sensorAPI.utils.exceptions;

public class SensorDoesNotExistError extends RuntimeException{
    @Override
    public String getMessage() {
        return "Sensor does not exist!";
    }
}
