package tests.RegistrationTest;
/* д.з.11
запуск из терминала gradle clean test -Dlogin=user1 -Dpassword=1234 - запускаются все тесты
gradle clean registration_test -Dlogin=user1 -Dpassword=1234 */
import com.codeborne.selenide.Configuration;
import com.sun.source.util.SourcePositions;
import helpers.Attach;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.sql.SQLOutput;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";

        String login = System.getProperty("login", "notRight");
        String password = System.getProperty("password", "123");

        //дженкинс подключается удаленно к хрому, сначала пишем логин/пароль, потом название сайта selenoid.autotests.cloud
        Configuration.remote = "https://" + login + ":" + password + "@" + "selenoid.autotests.cloud/wd/hub";
        //+ System.getProperty("remoteBrowser");


        //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";


        //специальная функция, чтобы был доступен просмотр и видео
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterAll
    static void afterAll() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
