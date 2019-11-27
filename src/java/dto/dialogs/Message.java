package dto.dialogs;

import dto.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Message implements Serializable, Comparable<Message> {
    private String message;
    private final String userLogin;
    public LocalDateTime date;

    public Message(String message, User user, LocalDateTime date) {
        this.message = message;
        this.userLogin = user.getLogin();
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getUserLogin() {
        return userLogin;
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
                ", user=" + userLogin +
                ", date=" + date +
                '}';
    }


    @Override
    public int compareTo(Message message) {
        return this.date.compareTo(message.date);
    }
}
