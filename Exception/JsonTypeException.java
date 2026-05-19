package bg.tu_varna.sit.f24621744.task.Exception;

/**
 * Thrown when an operation is incompatible with the node type
 */
public class JsonTypeException extends JsonException {
    public JsonTypeException(String message) {
        super("Json Type Error: " + message);
    }
}