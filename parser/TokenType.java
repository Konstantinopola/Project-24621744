package bg.tu_varna.sit.f24621744.task.parser;

/**
 * Enumeration of JSON lexical token types.
 * <p>
 * Used by the lexer ({@link Lexer}) to classify characters in the input string
 * and by the parser ({@link JsonParser}) to construct a JSON tree from tokens.
 * </p>
 * <p>
 * Token types correspond to all valid elements of the JSON format (RFC 8259).
 * </p>
 */
public enum TokenType {

    /** Opening brace {@code {}. Beginning of a JSON object. */
    LEFT_BRACE,

    /** Closing brace {@code }}. End of a JSON object. */
    RIGHT_BRACE,

    /** Opening square bracket {@code [}. Beginning of a JSON array. */
    LEFT_BRACKET,

    /** Closing square bracket {@code ]}. End of a JSON array. */
    RIGHT_BRACKET,

    /** Colon {@code :}. Separator between key and value in a JSON object. */
    COLON,

    /** Comma {@code ,}. Separator between elements in an object or array. */
    COMMA,

    /** A string value enclosed in double quotation marks. */
    STRING,

    /** Numeric value (integer or fractional, positive or negative). */
    NUMBER,

    /** Boolean value {@code true}. */
    TRUE,

    /** Boolean value {@code false}. */
    FALSE,

    /** Value {@code null}. */
    NULL
}