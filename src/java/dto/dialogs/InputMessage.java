package dto.dialogs;

import dto.io.FileSaver;
import dto.user.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class InputMessage {
    public static LocalDateTime publishTime = LocalDateTime.now();
    public static List<Message> editList = new ArrayList<>();
    public static List<Message> delayedMessageList = new ArrayList<>();
    public static List<Message> messageList = new ArrayList<>();


    public static Scanner chat = new Scanner(System.in);

    public static void input(File history, List<User> userL) throws IOException {

        FileSaver fileSaver = new FileSaver(history);
        Message message = null;
        do {
            for (User user : userL) {
                System.out.print(user.getLogin() + ":");
                message = new Message(chat.nextLine(), user, LocalDateTime.now());
                editList.add(message);
                if ((message.getMessage()).equals("EXIT")) {
                    System.out.println("Your session ended. Good bye.");
                    break;
                } else if ((message.getMessage()).equals("EDIT")) {
                    System.out.println("Edit message");
                    editMessage(user);
                } else if ((message.getMessage()).equals("DELAY")) {
                    System.out.println("Delay message");
                    inputDelayedMessage(user);
                }
                messageList.add(message);
                if (delayedMessageList.size() > 0) checkDelayedMessage(fileSaver);
                editList.removeIf(x -> (x.getDate().isBefore(LocalDateTime.now().minusMinutes(1))));
            }
        } while (!(message.getMessage()).equals("EXIT"));
    }

    public static void inputDelayedMessage(User user) {

        int time = 0;
        do {
            System.out.print("Minutes to delay: ");
            try {
                time = Integer.parseInt(chat.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong time");
            }
        } while (time <= 0);
        publishTime = LocalDateTime.now().plusMinutes(time);
        System.out.print(user.getLogin() + "(delayed message): ");
        String m = chat.nextLine();
        Message delayedMessageObj = new Message(m, user, publishTime);
        delayedMessageList.add(delayedMessageObj);
        System.out.println("message saved");
    }

    public static void checkDelayedMessage(FileSaver fileSaver) throws IOException {
        for (Message x :
                delayedMessageList) {
            if (x.getDate().isBefore(LocalDateTime.now())) {
                System.out.println(x.getUserLogin() + "(delayed): " + x.getMessage());
                messageList.add(x);
            }
        }
        delayedMessageList.removeIf(x -> (x.getDate().isBefore(LocalDateTime.now())));

    }

    public static void editMessage(User user) {
        System.out.println("Only 1 minute old messages are editable. Type old message");
        String oldMessage = chat.nextLine();
        Collections.sort(editList);
        Collections.reverse(editList);
        for (Message x :
                editList) {
            if (x.getMessage().equals(oldMessage) && x.getUserLogin().equals(user.getLogin()) && x.getDate().isAfter(LocalDateTime.now().minusMinutes(1))) {
                System.out.print("Message " + x.getMessage() + " was found. Type your edit: ");
                x.setMessage("(edited)"+ chat.nextLine());
                x.setDate(LocalDateTime.now());
                System.out.println("(edited) " + x.getUserLogin() + ": " + x.getMessage());
            } else if (x.getMessage().equals(oldMessage) && x.getUserLogin().equals(user) && !x.getDate().isAfter(LocalDateTime.now().minusMinutes(1))) {
                System.out.println("Old message cannot be edited.");
            }
        }
    }


}
