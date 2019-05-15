import java.util.ArrayList;
import java.util.List;

class CheckedElementBool implements CheckedElement {
    private String value;
    private boolean status = false;

    public CheckedElementBool(String value) {
        this.value = value;
    }
    public CheckedElementBool(String value, boolean status) {
        this.value = value;
        this.status = status;
    }

    public String getElement() {
        return this.value;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

public class CheckedElementsBool implements CheckedElements {

    private ArrayList<CheckedElement> elements;

    CheckedElementsBool(String[] values) {
        this.elements = newElementsList(values);
    }

    public ArrayList<CheckedElement> newElementsList(String[] values) {
        ArrayList<CheckedElement> elements = new ArrayList<CheckedElement>();
        for(String i: values) {
            elements.add(new CheckedElementBool(i));
        }
        return elements;
    }

    public void setStatus(String condition, boolean status) {
        for(CheckedElement i: this.elements) {
            if(i.getElement().equalsIgnoreCase(condition)) i.setStatus(status);
        }
    }

    public boolean getStatus() {
        for(CheckedElement i: this.elements) if(!i.getStatus()) {
            System.out.println(i.getElement());
            return false;
        }
        return true;
    }
}
