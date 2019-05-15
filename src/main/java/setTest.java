import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


public class setTest {

    public static void main(String[] args) {
//        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10, 1000).ignoring(NoSuchElementException.class);
        try {
            driver.get("http://aplana.ru");

            WebElement firstResult = ((ChromeDriver) driver).findElementByClassName("header-nav"); //wait.until(presenceOfElementLocated(By.cssSelector("header-menu > a")));


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
            new Actions(driver).click(currentPage.findElement(By.className("header-brand"))).perform();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("header-brand")));
            link = driver.findElement(By.xpath(".//li[contains(@class, 'nav-contacts')]/a"));


            new Actions(driver).click(link).perform();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));


            //Находим элементы с нужными полями
            WebElement name = ((ChromeDriver) driver).findElementByName("feedback[name]");
            WebElement email = ((ChromeDriver) driver).findElementByName("feedback[email]");
            WebElement commitButton = driver.findElement(By.xpath(".//div[contains(@class, 'frm-commit')]/button"));

            //Тесты над элементами
            name.sendKeys("Тест Тест");
            email.sendKeys("test@");
            commitButton.click();


            if(email.getAttribute("class").equalsIgnoreCase("error")) {
                System.out.println("Incorrect email OK");
            } else System.out.println("Incorrect email ERROR");
            email.clear();
            email.sendKeys("test@test.com");
            commitButton.click();


            //вот тут явно так себе решение, не уверен, что оно будет работать так, как надо
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("result")));
                WebElement result = ((ChromeDriver) driver).findElementByClassName("result-heading");
                    System.out.println("Modal message OK: " + result.getText());

            } catch (Throwable e) {
                System.out.println("Modal message ERROR");
            }


            //Закрываем модальное окно
            new Actions(driver).click(driver.findElement
                    (By.xpath(".//a[contains(@data-dismiss, 'result')]"))).perform();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
