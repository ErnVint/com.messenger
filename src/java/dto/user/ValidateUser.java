package dto.user;

import dto.io.FileSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUser implements IValidate {

    private String login;
    private String password;

    public ValidateUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean checkUser(String login, String password, File users) {
        FileSaver fileSaver = new FileSaver(users);
        Set<User> userList;
        if ((userList = (Set<User>) fileSaver.readFromFile()) != null) {
            for (User x :
                    userList) {
                if (login.equals(x.getLogin()) && password.equals(x.getPassword())) return true;
            }
        }
        return false;
    }


}