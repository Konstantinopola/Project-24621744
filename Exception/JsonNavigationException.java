package bg.tu_varna.sit.f24621744.task.Exception;

/**
 * Thrown when attempting to access a non-existent key,
 * array out of bounds, or invalid path format.
 */
public class JsonNavigationException extends JsonException {
    public JsonNavigationException(String message) {
        super("Navigation Error: " + message);
    }
}