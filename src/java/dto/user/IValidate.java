package dto.user;

import java.io.File;
import java.io.FileNotFoundException;

public interface IValidate {
    boolean checkUser(String login, String password, File users) throws FileNotFoundException;
}
