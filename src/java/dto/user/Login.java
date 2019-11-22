package dto.user;

import dto.user.RegisterNewUser;
import dto.user.User;
import dto.user.ValidateUser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    public static User user;

    public static void login(File users) throws IOException {
        System.out.println("Type in your username and password or REGNEW to register.");
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
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
                    user = new User(login, password);
                    success = true;
                } else {
                    System.out.println("Please check you USERNAME and PASSWORD or type REGNEW to register.");
                }
            }
        } while (!success);

    }

    public static String login(File users, String userq, String pwq) throws IOException {
        System.out.println("Type in your username and password or REGNEW and empty password to register.");
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        while (true){
            System.out.print("USERNAME: ");
            String login = userq;
            if (login.equals("REGNEW")) {
            //    RegisterNewUser.newUser(users);
                return "REGNEW";
            } else {
                System.out.print("PASSWORD: ");
                String password = pwq;
                ValidateUser validateUser = new ValidateUser(login, password);
                if (validateUser.checkUser(users)) {
                    System.out.println("Login successful.");
                    user = new User(userq, pwq);
                    success = true;
                    return "success";
                } else {
                    return "invalid";
                    //throw new IllegalArgumentException("Please check you USERNAME and PASSWORD or type REGNEW to register.");
                    //System.out.println("Please check you USERNAME and PASSWORD or type REGNEW to register.");
                }
            }
        }

    }

}
