package bg.tu_varna.sit.f24621744.task.parser;

import bg.tu_varna.sit.f24621744.task.jsonWork.*;
import bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType.*;

import java.util.List;

/**
 * JSON token parser.
 * <p>
 * Second stage of JSON parsing: accepts a list of tokens from {@link Lexer}
 * and builds a tree of objects ({@link JsonType}) from them.
 * </p>
 * <p>
 * Supports all JSON types:
 * <ul>
 * <li>objects ({@link JsonObject}) - {@code { "key": value }}</li>
 * <li>arrays ({@link JsonArray}) - {@code [ v1, v2, v3 ]}</li>
 * <li>strings ({@link JsonPrString}) - {@code "text"}</li>
 * <li>numbers ({@link JsonPrNumber}) - {@code 42}, {@code 3.14}</li>
 * <li>Boolean ({@link JsonPrBoolean}) - {@code true}, {@code false}</li>
 * <li>null ({@link JsonPrNull}) - {@code null}</li>
 * </ul>
 * </p>
 *
 * <p><b>Typical usage scenario:</b></p>
 * <pre>{@code
 * JsonType result = JsonParser.parseString("{\"name\": \"Alice\"}");
 * }</pre>
 */
public class JsonParser {

    /** List of tokens received from {@link Lexer}. */
    private final List<Token> tokens;

    /** Current position in the token list. */
    private int pos = 0;

    /**
     * Creates a parser for the given list of tokens.
     *
     * @param tokens list of tokens received from {@link Lexer#tokenize()}
     */
    public JsonParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses a list of tokens and returns the root JSON node.
     *
     * @return the root {@link JsonType} node,
     * or {@code null} if the token list is empty.
     */
    public JsonType parse() {
        if (tokens.isEmpty()) return null;
        return parseValue();
    }

    /**
     * Static helper method: performs a full parsing cycle
     * of a JSON string (tokenization + parsing) and returns a JSON node.
     * <p>
     * This is the main public API for parsing strings in other parts of the application.
     * </p>
     *
     * @param arguments JSON string to parse
     * @return root {@link JsonType} node of the parsed JSON structure
     * @throws RuntimeException if the string contains invalid JSON
     */
    public static JsonType parseString (String arguments){
        Lexer lexer = new Lexer(arguments);
        List<Token> tokens = lexer.tokenize();

        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }

    /**
     * Determines the type of the current token and delegates parsing
     * to the appropriate method.
     * <p>
     * Called recursively for each nested value
     * when parsing objects and arrays.
     * </p>
     *
     * @return {@link JsonType} node corresponding to the current token
     * @throws RuntimeException if an unexpected token is encountered
     */
    private JsonType parseValue() {
        Token token = tokens.get(pos);

        switch (token.type) {
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            case STRING:
                pos++;
                return new JsonPrString(token.value);
            case NUMBER:
                String numStr = token.value;
                pos++;
                try {
                    // Checking if a number is an integer (without a dot ".")
                    if (!numStr.contains(".")) {
                        return new JsonPrNumber(Long.parseLong(numStr));
                    }
                } catch (NumberFormatException ignored) {}
                return new JsonPrNumber(Double.parseDouble(numStr));
            case TRUE:
                pos++;
                return new JsonPrBoolean(true);
            case FALSE:
                pos++;
                return new JsonPrBoolean(false);
            case NULL:
                pos++;
                return new JsonPrNull();
            default:
                throw new RuntimeException("Parser error: Unexpected token " + token.type);
        }
    }

    /**
     * Parses a JSON object ({@code { "key": value, ... }}).
     * <p>
     * Expects the current token to be {@link TokenType#LEFT_BRACE}.
     * Reads comma-separated key-value pairs sequentially,
     * until {@link TokenType#RIGHT_BRACE} is encountered.
     * </p>
     *
     * @return the constructed {@link JsonObject} with all parsed properties
     * @throws RuntimeException if the object structure is corrupted
     * (missing key, colon, or closing parenthesis)
     */
    private JsonObject parseObject() {
        JsonObject jsonObject = new JsonObject();
        pos++;

        while (tokens.get(pos).type != TokenType.RIGHT_BRACE) {
            Token keyToken = tokens.get(pos);
            if (keyToken.type != TokenType.STRING) {
                throw new RuntimeException("Parser error: Expected String key");
            }
            String key = keyToken.value;
            pos++;

            if (tokens.get(pos).type != TokenType.COLON) {
                throw new RuntimeException("Parser error: Expected ':' after key");
            }
            pos++;

            JsonType value = parseValue();
            jsonObject.addChild(key, value);

            if (tokens.get(pos).type == TokenType.COMMA) {
                pos++;
            }
        }
        pos++;
        return jsonObject;
    }

    /**
     * Parses a JSON array ({@code [ value1, value2, ... ]}).
     * <p>
     * Expects the current token to be {@link TokenType#LEFT_BRACKET}.
     * Reads comma-separated values ​​sequentially,
     * until {@link TokenType#RIGHT_BRACKET} is encountered.
     * </p>
     *
     * @return the constructed {@link JsonArray} with all parsed elements
     * @throws RuntimeException if the array structure is corrupted
     * (e.g., a closing bracket is missing)
     */
    private JsonArray parseArray() {
        JsonArray jsonArray = new JsonArray();
        pos++;

        while (tokens.get(pos).type != TokenType.RIGHT_BRACKET) {
            JsonType value = parseValue();
            jsonArray.add(value);

            if (tokens.get(pos).type == TokenType.COMMA) {
                pos++;
            }
        }
        pos++;
        return jsonArray;
    }
}