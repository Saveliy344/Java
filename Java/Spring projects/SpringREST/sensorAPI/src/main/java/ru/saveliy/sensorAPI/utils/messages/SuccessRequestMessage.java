package ru.saveliy.sensorAPI.utils.messages;

public class SuccessRequestMessage {
    private String message;

    public SuccessRequestMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
