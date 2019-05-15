import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class setTest {
    private static boolean inArray(String value,String[] arr) {
        for (String i:
             arr) {
            if(i.equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            driver.get("http://aplana.ru");
//            driver.navigate().refresh();
//            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
            WebElement firstResult = ((ChromeDriver) driver).findElementByClassName("header-nav"); //wait.until(presenceOfElementLocated(By.cssSelector("header-menu > a")));
//            for(int i = 0; i < firstResult.getSize(); )
            List<WebElement> elements = firstResult.findElements(By.xpath("./li/a"));
            String testHeaderMenuStrings[] = new String[] {"Компания", "Услуги", "Наш опыт", "Инфо-центр", "Карьера", "Контакты"};
            System.out.println(elements.size());

            CheckedElementsBool testHeaderMenu = new CheckedElementsBool(testHeaderMenuStrings);
            for(WebElement i: elements) {
                testHeaderMenu.setStatus(i.getText(), true);
            }
            System.out.println("First test: " + testHeaderMenu.getStatus());

            elements = firstResult.findElements(By.xpath("./li[contains(@class,'nav-company')]/ul[contains(@class,'header-dropdown')]/li/a"));
            new Actions(driver).moveToElement(firstResult.findElement(By.className("nav-company"))).perform();
            String testCompanyString[] = new String[] {"Аплана сегодня", "Руководство", "Партнёры", "Университет Аплана"};
            CheckedElementsBool testCompany = new CheckedElementsBool(testCompanyString);
            for(WebElement i: elements) {
                testCompany.setStatus(i.getText(), true);
            }
            System.out.println("Second test: "+testCompany.getStatus());
        } finally {
            driver.quit();
        }
    }
}
