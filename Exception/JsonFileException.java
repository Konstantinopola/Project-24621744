package bg.tu_varna.sit.f24621744.task.Exception;

/**
 * Thrown when there are problems reading or saving files.
 */
public class JsonFileException extends JsonException {
    public JsonFileException(String message) {
        super("File System Error: " + message);
    }
}