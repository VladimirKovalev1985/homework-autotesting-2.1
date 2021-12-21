import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {
    //создание поля класса драйвера
    private WebDriver driver;

    //будет выполняться в самом начале
    @BeforeAll
    public static void setUpAll() {
        //прописываем путь до хром драйвера, 1 строка-название проперти, 2-путь до нужного файла
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
    }

    //инициализация метода драйвера
    @BeforeEach
    //будет выполняться перед каждым методом
    public void setUp() {
        driver = new ChromeDriver();
        //driver это как браузер

    }

    @AfterEach
//выполняться будет после каждого теста, это как-будто закрыли браузер
    public void tearDown() {
        driver.quit();
        driver = null;
    }
@Test
    public void shouldSendForm(){
     //открытие нужной страницы
    driver.get("http://localhost:9999/");
   // driver.findElement().sendKeys("Владимир");
    //driver.findElement().sendKeys("+7-981-144-00-00");
    List<WebElement> textFields = driver.findElements(By.className("input__control"));
    textFields.get(0).sendKeys("Владимир"); //обращаемся к первому полю, имя
    textFields.get(1).sendKeys("+79811440000"); //обращаемся ко второму полю,телефон
    driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
    driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
    String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
    //.trim() обрезает не нужные пробелы
    String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
            //ожидаемый результат
    assertEquals(expected, text);



    }
}
