import dto.dialogs.*;
import dto.io.FileSaver;
import dto.user.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public static Set<User> userSet = new HashSet<>();

    public static void main(String[] args) throws IOException {

        File users = new File(System.getProperty("java.io.tmpdir") + File.separator + "users.txt");
        System.out.println("Users saved here: " + users.getAbsolutePath());
                //"./src/java/res/Users.rtf");
        File history = new File(System.getProperty("java.io.tmpdir") + File.separator + "history.txt");
        System.out.println("History saved here: " + history.getAbsolutePath());


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
                IValidate validate = new ValidateUser(login, password);
                if (validate.checkUser(login, password, users)) {
                    if (!userSet.add(new User(login, password))) System.out.println("User already logged in");
                    else System.out.println("Login successful.");
                    System.out.println("Add more users to chat? Type YES to add.");
                    if (!scanner.nextLine().equals("YES")) {
                        endLogin = true;
                    }
                } else {
                    System.out.println("Please check you USERNAME and PASSWORD or type REGNEW to register.");
                }
            }
        } while (!endLogin);
        FileSaver historySaver = new FileSaver(history);
        Message message = null;
        if ((InputMessage.messageList = (List<Message>) historySaver.readFromFile()) == null)
            InputMessage.messageList = new ArrayList<>();
        else for (Message x:
                  InputMessage.messageList) {
            System.out.println(x.getUserLogin() + ": " + x.getMessage() + "            date: " + x.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss d MMM uu")));
        }
        do {
            for (User user : userSet) {
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
                InputMessage.messageList.add(message);
                if (InputMessage.delayedMessageList.size() > 0) InputMessage.checkDelayedMessage(historySaver);
                InputMessage.editList.removeIf(x -> (x.getDate().isBefore(LocalDateTime.now().minusMinutes(1))));
            }
        } while (!(message.getMessage()).equals("EXIT"));
        for (Message x:
        InputMessage.messageList) {
            System.out.println(x.getUserLogin() + ": " + x.getMessage() + "            date: " + x.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss d MMM uu")));
        }
        historySaver.printToFile(InputMessage.messageList);
    }
}