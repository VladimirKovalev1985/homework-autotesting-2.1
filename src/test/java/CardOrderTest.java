import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {
    //создание поля класса драйвера
    private WebDriver driver;

    //будет выполняться в самом начале
    @BeforeAll
    public static void setUpAll() {
        //прописываем путь до хром драйвера, 1 строка-название проперти, 2-путь до нужного файла
        //System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    //инициализация метода драйвера
    @BeforeEach
    //будет выполняться перед каждым методом
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        // driver = new ChromeDriver();
        //driver это как браузер
        //driver.get("http://localhost:9999/");

    }

    @AfterEach
//выполняться будет после каждого теста, это как-будто закрыли браузер
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        //открытие нужной страницы
        driver.get("http://localhost:9999/");
        //driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир");
        //т.е. будет искать все инпуты с таким названием
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79811440000");
        //List<WebElement> textFields = driver.findElements(By.className("input__control"));
        //textFields.get(0).sendKeys("Владимир"); //обращаемся к первому полю, имя
        //textFields.get(1).sendKeys("+79811440000"); //обращаемся ко второму полю,телефон
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click(); //ставим галочку что с условиями согласен
        //можно так By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        //можно так By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        //.trim() обрезает не нужные пробелы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        //ожидаемый результат
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendNormForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир Иванов");
        //можно так (By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир");
        //т.е. будет искать все инпуты с таким названием
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        //List<WebElement> textFields = driver.findElements(By.className("input__control"));
        //textFields.get(0).sendKeys("Владимир"); //обращаемся к первому полю, имя
        //textFields.get(1).sendKeys("+79811440000"); //обращаемся ко второму полю,телефон
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        //можно так By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        //можно так By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        //.trim() обрезает не нужные пробелы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        //ожидаемый результат
        assertEquals(expected, text);


    }


    @Test
    public void shouldSendShortName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вл Ив");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);

    }

    @Test
    public void shouldSendNameByOneLetter(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("В И");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendJustName(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendDoubleName(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Софья-Мария Иванова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendDoubleSurname(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна Петрова-Водкина");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendDoubleNameAndSurname(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна-София Петрова-Водкина");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneWhichStartsWith8(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вл Ив");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+89811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneWhichStartsWith9(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вл Ив");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+99811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneWhichStartsWith3(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вл Ив");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+39811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneWhichStartsWith0(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вл Ив");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+09811440000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    }


