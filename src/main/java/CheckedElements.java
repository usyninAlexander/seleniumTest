import java.util.List;

interface CheckedElement {
    String getElement();
    boolean getStatus();
    void setStatus(boolean status);
}

public interface CheckedElements {
    List<CheckedElement> newElementsList(String[] values);
    void setStatus(String condition, boolean status);
}
