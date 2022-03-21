package tests.RegistrationTest;
//д.з. 1
        import com.codeborne.selenide.Configuration;
        import com.codeborne.selenide.logevents.SelenideLogger;
        import io.qameta.allure.selenide.AllureSelenide;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.Test;
        import org.openqa.selenium.remote.DesiredCapabilities;

        import static com.codeborne.selenide.Condition.text;
        import static com.codeborne.selenide.Selectors.byText;
        import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTest {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

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

    @Test
    void successFillTest() {
        open("/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form"));

        $("#firstName").setValue("Alex");
        $("#lastName").setValue("Ivanov");
        $("#userEmail").setValue("Alex@test.com");
        $(byText("Male")).click();
        $("#userNumber").setValue("7904540014");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("July");
        $(".react-datepicker__year-select").selectOption("2008");
        $("[aria-label$='July 30th, 2008']").click();
        $("#subjectsInput").setValue("888"); //исчезает(?)
        $(byText("Sports")).click();
        //$("#uploadPicture").uploadFromClasspath("img/1.jpg");
        $("#currentAddress").setValue("999999");
        $(byText("Select State")).click();
        $(byText("NCR")).click();
        $(byText("Select City")).click();
        $(byText("Delhi")).click();

        $("#submit").click();

        $(".table-responsive").$(byText("Student Name"))
                .parent().shouldHave(text("Alex Ivanov"));
        $(".table-responsive").$(byText("Student Email"))
                .parent().shouldHave(text("Alex@test.com"));
        $(".table-responsive").$(byText("Gender"))
                .parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile"))
                .parent().shouldHave(text("7904540014"));
        $(".table-responsive").$(byText("Date of Birth"))
                .parent().shouldHave(text("30 July,2008"));
        $(".table-responsive").$(byText("Hobbies"))
                .parent().shouldHave(text("Sports"));
        /*$(".table-responsive").$(byText("Picture"))
                .parent().shouldHave(text("1.jpg"));*/
        $(".table-responsive").$(byText("Address"))
                .parent().shouldHave(text("999999"));
        $(".table-responsive").$(byText("State and City"))
                .parent().shouldHave(text("NCR Delhi"));


    }
}
