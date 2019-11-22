import dto.dialogs.*;
import dto.io.FileSaver;
import dto.user.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Messenger {
    /**
     * Мессенджер
     * 1. Вводить ник:
     * 1.1 Должен быть только латинскими буквами
     * 1.2 Должен быть не меньше 5 символов
     * 1.3 Не должен начинаться с цифры
     * 1.4 Не должен содержать #,!,\
     * 2. Сообщения вводятся через консоль
     * 3. Сообщения хранятся в массиве
     * 4. Можно отредактировать своё сообщение если оно не старше минуты
     * 5. Можно писать отложенные сообщения
     **/

    public static List<User> userList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        File users = new File("/Users/ernvint/IdeaProjects/com.messenger/src/java/res/Users.rtf");
        File history = new File("/Users/ernvint/IdeaProjects/com.messenger/src/java/res/History.rtf");

        System.out.println("Type in your username and password or REGNEW and empty password to register.");
        Scanner scanner = new Scanner(System.in);
        boolean endLogin = false;
        do {
            System.out.print("USERNAME: ");
            String login = scanner.nextLine();
            if (login.equals("REGNEW")) {
                RegisterNewUser.newUser(users);
            } else {
                System.out.print("PASSWORD: ");
                String password = scanner.nextLine();
                ValidateUser validateUser = new ValidateUser(login, password);
                if (validateUser.checkUser(users)) {
                    System.out.println("Login successful.");
                    userList.add(new User(login, password));
                    System.out.println("Add more users to chat? Type YES to add.");
                    if (!scanner.nextLine().equals("YES")){
                        endLogin = true;
                    }
                } else {
                    System.out.println("Please check you USERNAME and PASSWORD or type REGNEW to register.");
                }
            }
        } while (!endLogin);
        FileSaver fileSaver = new FileSaver(history);
        Message message = null;
        do {
            for (User user : userList) {
                System.out.print(user.getLogin() + ":");
                message = new Message(InputMessage.chat.nextLine(), user, LocalDateTime.now());
                InputMessage.editList.add(message);
                if ((message.getMessage()).equals("EXIT")) {
                    System.out.println("Your session ended. Good bye.");
                    break;
                } else if ((message.getMessage()).equals("EDIT")) {
                    System.out.println("Edit message");
                    InputMessage.editMessage(user);
                } else if ((message.getMessage()).equals("DELAY")) {
                    System.out.println("Delay message");
                    InputMessage.inputDelayedMessage(user);
                }
                fileSaver.printToFile(message.toString());
                if (InputMessage.delayedMessageList.size() > 0) InputMessage.checkDelayedMessage(fileSaver);
                InputMessage.editList.removeIf(x -> (x.getDate().isBefore(LocalDateTime.now().minusMinutes(1))));
            }
        } while (!(message.getMessage()).equals("EXIT"));
    }
}