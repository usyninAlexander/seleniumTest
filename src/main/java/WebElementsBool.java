import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

class WebElementWithStatusBool implements WebElementWithStatus {
    private String value = "";
    private Boolean status = false;
    WebElementWithStatusBool(String value) {
        this.value = value;
    }
    public String getElement() {
        return this.value;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return this.status;
    }
}
public class WebElementsBool implements WebElementsList {

    private List<WebElementWithStatus> webElementsList;
    WebElementsBool(List<WebElement> values) {
        newElementsList(values);
    }
    public List<WebElementWithStatus> newElementsList(List<WebElement> values){
        this.webElementsList = new ArrayList<WebElementWithStatus>();
        for (WebElement i:
             values) {
            this.webElementsList.add(new WebElementWithStatusBool(i.getText()));
        }
        return this.webElementsList;
    }
    public boolean checkStatus() {
        for(WebElementWithStatus i:
        this.webElementsList) {
            if(!i.getStatus()) return false;
        }
        return true;
    }

    public void setStatus(String condition, boolean status) {
        for(WebElementWithStatus i: this.webElementsList) {
            if(i.getElement().equalsIgnoreCase(condition)){
                i.setStatus(status);
            }
        }
    }



}
