import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.Visibility;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfNestedElementLocatedBy;

public class setTest {
    private static boolean inArray(String value,String[] arr) {
        for (String i:
             arr) {
            if(i.equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    private static String checkCondition(boolean condition) {
        if(condition) return "OK";
        return "Please fix it";
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10, 1000).ignoring(NoSuchElementException.class);
        try {
            driver.get("http://aplana.ru");
//            driver.navigate().refresh();
//            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
            WebElement firstResult = ((ChromeDriver) driver).findElementByClassName("header-nav"); //wait.until(presenceOfElementLocated(By.cssSelector("header-menu > a")));
//            for(int i = 0; i < firstResult.getSize(); )
            List<WebElement> elements = firstResult.findElements(By.xpath("./li/a"));
            String testHeaderMenuStrings[] = new String[] {"Компания", "Услуги", "Наш опыт",
                    "Инфо-центр", "Карьера", "Контакты"};
            System.out.println(elements.size());

            CheckedElementsBool testHeaderMenu = new CheckedElementsBool(testHeaderMenuStrings);
            for(WebElement i: elements) {
                testHeaderMenu.setStatus(i.getText(), true);
            }
            System.out.println("First test: " + testHeaderMenu.getStatus());

            new Actions(driver).moveToElement(firstResult.findElement(By.className("nav-company"))).perform();
            elements = firstResult.findElements(By.xpath("./li[contains(@class,'nav-company')]/ul[contains(@class,'header-dropdown')]/li/a"));
            String testCompanyString[] = new String[] {"Аплана сегодня", "Руководство", "Партнёры", "Университет Аплана"};
            CheckedElementsBool testCompany = new CheckedElementsBool(testCompanyString);
            for(WebElement i: elements) {
                testCompany.setStatus(i.getText(), true);
            }
            System.out.println("Second test: "+testCompany.getStatus());

            for(WebElement i: elements) {
                if(i.getText().equalsIgnoreCase("Партнёры")) {
                    new Actions(driver).click(i).perform();
                    break;
                }
            }
            //Ждем загрузки страницы
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("page-heading"))); //h1[contains(@class,'page-heading')]")));
            List<WebElement> partnersMenu = driver.findElements(
                    By.xpath("//div[contains(@class, 'aside-inner')]/ul[contains(@class, 'aside-nav')]/li"));

            CheckedElementsBool testPartnerMenu = new CheckedElementsBool(testCompanyString);
            boolean partnersStatus = false;
            for(WebElement i: partnersMenu) {
                String linkText = i.findElement(By.tagName("a")).getText();
                testPartnerMenu.setStatus(linkText, true);
                if(linkText.equalsIgnoreCase("Партнёры") &&
                        i.getAttribute("class").equalsIgnoreCase("active")) partnersStatus = true;
            }
            System.out.println("Existence test: " + testPartnerMenu.getStatus() +
                    ". Active element test: " + partnersStatus);

            //Найдем нужный для перехода на страницу Университет Аплана элемент
            WebElement links = ((ChromeDriver) driver).findElementByClassName("aside-nav");
            WebElement link = links.findElement(By.linkText("Университет Аплана"));
            new Actions(driver).click(link).perform();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("page-heading"))); //h1[contains(@class,'page-heading')]")));
            WebElement searchButton = ((ChromeDriver) driver).findElementByCssSelector("div.header-search");
            new Actions(driver).click(searchButton).perform();
            driver.findElement(By.name("q")).sendKeys("Вакансии" + Keys.ENTER);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
            WebElement currentPage = ((ChromeDriver) driver).findElement(By.tagName("body"));
            if(currentPage.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Поиск")){
                System.out.println("Search page status OK");
            } else System.out.println("Search page status ERROR");

            if(currentPage
                    .findElement(By.xpath(".//form[contains(@class, 'search-form')]/input"))
                    .getAttribute("value")
                    .equalsIgnoreCase("вакансии")){
                System.out.println("Search field value OK");
            } else System.out.println("Search field value ERROR");
            String url = driver.getCurrentUrl();
            if( URLDecoder.decode(url, "UTF-8").equalsIgnoreCase("http://aplana.ru/search?q=Вакансии")) {
                System.out.println("Page URL Ok");
            } else System.out.println("Page URL ERROR:" + driver.getCurrentUrl());
            //Переходим на главную страницу

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
