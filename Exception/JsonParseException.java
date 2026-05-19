package bg.tu_varna.sit.f24621744.task.Exception;

/**
 * Thrown by the parser when invalid JSON string syntax is detected.
 */
public class JsonParseException extends JsonException {
    public JsonParseException(String message) {
        super("Parse/Lexer Error: " + message);
    }
}