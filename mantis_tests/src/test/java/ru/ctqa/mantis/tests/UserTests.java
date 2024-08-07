package ru.ctqa.mantis.tests;

import ru.ctqa.mantis.common.CommonFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.ctqa.mantis.model.UserData;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UserTests extends TestBase {
    public static Stream<String> randomUsers() {
        Supplier<String> randomUser = () -> CommonFunctions.randomString(8);
        return Stream.generate(randomUser).limit(1);
    }

    @ParameterizedTest
    @MethodSource("randomUsers")
    void canRegisterUser(String username){
        // создать пользователя (адрес) на почтовом сервисе (JamesApiHelper)
        var email = String.format("%s@localhost", username);
        var password = "password";
        app.jamesApi().addUser(email, password);

        // заполняем форму создания и отправляем (RestApiHelper)
        app.rest().createUser(new UserData()
                .withUsername(username)
                .withRealname(username)
                .withPassword(password)
                .withEmail(email));

        // ждём почту (MailHelper)
        var duration = Duration.ofSeconds(60);
        var messages = app.mail().receive(email, password, duration);
        Assertions.assertEquals(1, messages.size());

        // извлекаем ссылку из письма (MailHelper)
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find());
        var url = text.substring(matcher.start(), matcher.end());

        // проходим по ссылке и завершаем регистрацию пользователя (SessionHelper)
        app.session().confirmPassword(url, password);
        Assertions.assertTrue(app.session().isLoginForm());

        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
