package bg.tu_varna.sit.f24621744.task.jsonWork;
import java.util.Collection;
public interface JsonType {

    String toJsonString(); // From objects to primitive text

    String toPrettyString(int indent); // From primitive text to classic look json

    JsonType getChild(String token); // Get inserted element (child) or null if not found

    boolean removeChild(String token);// Found and deleting inserted element (child) or null if not found

    boolean replaceChild(String key, JsonType newValue);

    Collection<JsonType> getValues(); // for searching

    boolean append(JsonType value); // for create new child (without key)

    void addChild(String key, JsonType value); // for create new child (with key)
}