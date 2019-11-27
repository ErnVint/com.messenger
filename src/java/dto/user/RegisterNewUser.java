package dto.user;

import dto.io.FileSaver;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterNewUser {

    public static void newUser(File file) {
        String login = "";
        boolean checkExistingUser = true;
        FileSaver fileSaver = new FileSaver(file);
        Set<User> userSet;
        if ((userSet = (Set<User>) fileSaver.readFromFile()) == null){
            userSet = new HashSet<>();
        }


        // userSet.add(new User("rrrrr", "rrrrr"));
        // fileSaver.printToFile(userSet);
        Scanner scanner = new Scanner(System.in);
        while (checkExistingUser) {
            System.out.print("     /**\n" +
                    "     * only english letters\n" +
                    "     * min 5 symbols\n" +
                    "     * not starting with number\n" +
                    "     * should not contain #,!,\\\n" +
                    "     **/ \n" +
                    "Enter USERNAME: ");
            login = scanner.nextLine();
            Pattern pattern2 = Pattern.compile("^[^\\d!#\\\\А-яЁё].*[^!#\\\\А-яЁё]{4,}");
            Matcher matcher2 = pattern2.matcher(login);
            if (!matcher2.matches()) {
                System.out.println("Check username requirements:");
            } else {
                if (userSet.size() == 0) checkExistingUser = false;
                else for (User x :
                        userSet) {
                    if (login.equals(x.getLogin())) {
                        System.out.println("Username already exist. Choose another username.");
                        break;
                    } else checkExistingUser = false;
                }
            }
        }

        System.out.print("Enter password: ");
        String pw = scanner.nextLine();
        User newUser = new User(login, pw);
        if (userSet.add(newUser)) {
            fileSaver.printToFile(userSet);
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration FAILED. Try again.");
        }
    }
}
