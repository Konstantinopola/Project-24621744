package bg.tu_varna.sit.f24621744.task.parser;

/**
 * Represents a lexical token obtained by tokenizing a JSON string.
 * <p>
 * Each token consists of a type ({@link TokenType}) and a string value.
 * Tokens are created by the lexer ({@link Lexer}) and passed to the parser ({@link JsonParser})
 * to build the JSON tree.
 * </p>
 * <p>
 * A token is an immutable object—all fields are declared as {@code final}.
 * </p>
 *
 * <p>Examples of tokens:</p>
 * <ul>
 * <li>{@code type=STRING, value="hello"}</li>
 * <li>{@code type=NUMBER, value="42"}</li>
 * <li>{@code type=LEFT_BRACE, value="{"}</li>
 * </ul>
 */
public class Token {

    /** The type of this token, defining its role in the JSON structure. */
    public final TokenType type;

    /** The string value of the token, extracted from the original JSON string. */
    public final String value;

    /**
     * Creates a new token with the specified type and value.
     *
     * @param type is the token type from the {@link TokenType} enumeration
     * @param value is the string value of the token (e.g., {@code "{"}, {@code "42"}, {@code "hello"})
     */
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
}