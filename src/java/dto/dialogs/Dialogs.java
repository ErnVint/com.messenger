package dto.dialogs;

import java.time.LocalDateTime;
import java.util.Arrays;

import dto.io.*;

public class Dialogs {
    private Message[] messages = new Message[0];
    private Message[] delayedMessages = new Message[0];

    public void addMessages(Message message) {
        this.addMessages(new Message[]{message});
    }

    public void addMessages(Message[] messages) {
        if (messages != null && messages.length != 0) {
            this.messages = Arrays.copyOf(this.messages, this.messages.length + messages.length);
            int messagesLength = messages.length;
            for (Message mes : messages) {
                this.messages[this.messages.length - messagesLength--] = mes;
            }
        }
    }

    public void addDelayedMessages(Message message) {
        this.addDelayedMessages(new Message[]{message});
    }

    public void addDelayedMessages(Message[] messages) {
        if (messages != null && messages.length != 0) {
            this.delayedMessages = Arrays.copyOf(this.delayedMessages, this.delayedMessages.length + messages.length);
            int messagesLength = messages.length;
            for (Message mes : messages) {
                this.messages[this.messages.length - messagesLength--] = mes;
            }
        }
    }

    public Message[] getMessages() {
        return messages;
    }

    public void history(IHistorySaver saver) {
        for (Message delayedMessage : this.delayedMessages) {
            if (delayedMessage.getDate().isBefore(LocalDateTime.now())) {
                addMessages(delayedMessage);
            }
        }
        for (int i = this.messages.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.messages[j].getDate().isAfter(this.messages[j + 1].getDate())) {
                    Message x = this.messages[j];
                    this.messages[j] = this.messages[j + 1];
                    this.messages[j + 1] = x;
                }
            }
        }
        for (Message message : this.messages) {
            saver.println(message.toString());
        }

    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }


}
