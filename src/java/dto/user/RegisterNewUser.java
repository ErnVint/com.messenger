package dto.user;

import dto.io.FileSaver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterNewUser {

    public static void newUser(File file) throws IOException {
        String login = "";
        boolean checkExistingUser = true;
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = new Scanner(new FileReader(file));
        while (checkExistingUser) {
            System.out.print("     /**\n" +
                    "     * only english letters\n" +
                    "     * min 5 symbols\n" +
                    "     * not starting with number\n" +
                    "     * should not contain #,!,\\\n" +
                    "     **/ \n" +
                    "Enter USERNAME: ");
            login = scanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String baseUser = fileScanner.nextLine();
                Pattern pattern1 = Pattern.compile(".+login='" + login + "'.+");
                Pattern pattern2 = Pattern.compile("^[^\\d!#\\\\А-яЁё].*[^!#\\\\А-яЁё]{4,}");
                Matcher matcher = pattern1.matcher(baseUser);
                Matcher matcher2 = pattern2.matcher(login);
                if (matcher.matches()) {
                    System.out.println("Username already exist. Choose another username.");
                    break;
                } else if (!matcher.matches() && matcher2.matches() && !fileScanner.hasNextLine()) {
                    checkExistingUser = false;
                } else if (!matcher.matches() && !matcher2.matches()) {
                    System.out.println("Check username requirements:");
                    break;
                }
            }
        }

        System.out.print("Enter password: ");
        String pw = scanner.nextLine();
        User newUser = new User(login, pw);
        FileSaver fileSaver = new FileSaver(file);
        fileSaver.printToFile(newUser.toString());
        System.out.println("Registration successful.");
        //  Login.login(this.file); //непонятно почему я это изначалально писал
    }
}
