import org.openqa.selenium.WebElement;

import java.util.List;

interface WebElementWithStatus {

    String getElement();
    boolean getStatus();
    void setStatus(boolean status);
}

public interface WebElementsList {

    List<WebElementWithStatus> newElementsList(List<WebElement> values);
    void setStatus(String condition, boolean status);
}


