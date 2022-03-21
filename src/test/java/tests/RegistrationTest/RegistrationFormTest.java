package tests.RegistrationTest;
//д.з. 10
        import com.codeborne.selenide.Configuration;
        import com.codeborne.selenide.logevents.SelenideLogger;
        import helpers.Attach;
        import io.qameta.allure.selenide.AllureSelenide;
        import org.junit.jupiter.api.AfterAll;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.Test;
        import org.openqa.selenium.remote.DesiredCapabilities;

        import static com.codeborne.selenide.Condition.text;
        import static com.codeborne.selenide.Selectors.byText;
        import static com.codeborne.selenide.Selenide.*;
        import static io.qameta.allure.Allure.step;

public class RegistrationFormTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        //дженкинс подключается удаленно к хрому, сначала пишем логин/пароль, потом название сайта selenoid.autotests.cloud
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

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

    @Test
    void registrationFormTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        step("Открываем главную страницу", () -> {
            open("/automation-practice-form");
            $(".main-header").shouldHave(text("Practice Form"));
        });

        step("Заполняем имя", () -> {
            $("#firstName").setValue("Alex");
        });

        step("Заполняем фамилию", () -> {
            $("#lastName").setValue("Ivanov");
        });

        step("Заполняем почту", () -> {
            $("#userEmail").setValue("Alex@test.com");
        });

        step("Заполняем пол", () -> {
            $(byText("Male")).click();
        });

        step("Заполняем номер телефона", () -> {
            $("#userNumber").setValue("7904540014");
        });

        step("Заполняем дату рождения", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("July");
            $(".react-datepicker__year-select").selectOption("2008");
            $("[aria-label$='July 30th, 2008']").click();
        });

        step("Заполняем предмет", () -> {
            $("#subjectsInput").setValue("Physics").pressEnter();
        });

        step("Заполняем хобби", () -> {
            $(byText("Sports")).click();
        });

        /*step("Заполняем картинку", () -> {
            $("#uploadPicture").uploadFromClasspath("img/1.jpg");
             });*/

        step("Заполняем адрес", () -> {
            $("#currentAddress").setValue("999999");
        });

        step("Заполняем штат", () -> {
            $(byText("Select State")).click();
            $(byText("NCR")).click();
        });

        step("Заполняем город", () -> {
            $(byText("Select City")).click();
            $(byText("Delhi")).click();
        });

        step("Отправляем", () -> {
            $("#submit").click();
        });

        step("Проверяем Фамилию имя", () -> {
            $(".table-responsive").$(byText("Student Name"))
                    .parent().shouldHave(text("Alex Ivanov"));
        });

        step("Проверяем почту", () -> {
            $(".table-responsive").$(byText("Student Email"))
                    .parent().shouldHave(text("Alex@test.com"));
        });

        step("Проверяем пол", () -> {
            $(".table-responsive").$(byText("Gender"))
                    .parent().shouldHave(text("Male"));
        });

        step("Проверяем телефон", () -> {
            $(".table-responsive").$(byText("Mobile"))
                    .parent().shouldHave(text("7904540014"));
        });

        step("Проверяем дату рождения", () -> {
            $(".table-responsive").$(byText("Date of Birth"))
                    .parent().shouldHave(text("30 July,2008"));
        });

        step("Проверяем хобби", () -> {
            $(".table-responsive").$(byText("Hobbies"))
                    .parent().shouldHave(text("Sports"));
        });

        /*step("Проверяем файл", () -> {
        $(".table-responsive").$(byText("Picture"))
                .parent().shouldHave(text("1.jpg"));
        });*/

        step("Проверяем адрес", () -> {
           $(".table-responsive").$(byText("Address"))
                    .parent().shouldHave(text("999999"));
        });

        step("Проверяем штат и город", () -> {
            $(".table-responsive").$(byText("State and City"))
                    .parent().shouldHave(text("NCR Delhi"));
        });
    }
}
