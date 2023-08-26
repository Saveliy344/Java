package ru.saveliy.sensorAPI.utils.messages;

public class ExceptionRequestMessage {
    private double time;
    private String message;

    public ExceptionRequestMessage(double time, String message) {
        this.time = time;
        this.message = message;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
