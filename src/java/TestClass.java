import dto.user.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;

public class TestClass {
    @DisplayName("Login/password availability test")
    @ParameterizedTest(name = "(index) -> login = {0}, password = {1}, res = {2}")
    @CsvSource({"rrrrr,rrrrr, success", "testUSER, testUSER, success", "fault, a, invalid", "REGNEW,,REGNEW"})
    public void loginTest(String login, String password, String expect) throws IOException {
        Assertions.assertEquals(expect, Login.login(new File("/Users/ernvint/IdeaProjects/com.messenger/src/java/res/Users.rtf"), login, password));
    }
}