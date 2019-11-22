package dto.dialogs;

import dto.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String message;
    private final User user;
    public LocalDateTime date;

    public Message(String message, User user, LocalDateTime date) {
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", user=" + user.getLogin() +
                ", date=" + date +
                '}';
    }
}
