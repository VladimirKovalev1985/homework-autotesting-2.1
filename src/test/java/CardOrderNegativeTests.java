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

public class CardOrderNegativeTests {
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
    public void ShouldSendEmptyForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.className("input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void ShouldSendFormWithoutPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendFormWithoutName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78888888888");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendWithoutClick() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78888888888");
        // driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__text")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendNameWithEnglish(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Vladimir Ivanov");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78888888888");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.className("input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneWithoutPlus(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("78888888888");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendNameWithSpesialSumbol(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("-*/&$");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78888888888");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.className("input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);

    }

    @Test
    public void shouldSendShortPhoneNumber(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("78888888");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendLongPhoneNumber(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("7888888800000");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneNumberWithSumbol(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7**********");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }

    @Test
    public void shouldSendPhoneNumberWithLetter(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("7aaaaaaaaaa");
        driver.findElement(By.className("checkbox__box")).click(); //ставим галочку что с условиями согласен
        driver.findElement(By.tagName("button")).click(); //нажал на кнопку "отправить"
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim(); //проверка какой текст возвращается после отправки формы
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }





}

