package bg.tu_varna.sit.f24621744.task.parser;

import bg.tu_varna.sit.f24621744.task.Exception.JsonParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexical analyzer (lexer) for JSON strings.
 * <p>
 * The first stage of JSON parsing: converts the input string into a list
 * of lexical tokens ({@link Token}), which are then passed
 * to the parser ({@link JsonParser}) to build a JSON tree.
 * </p>
 * <p>
 * Supported token types:
 * <ul>
 * <li>Structural characters: {@code { } [ ] : ,}</li>
 * <li>Strings: {@code "text in double quotes"}</li>
 * <li>Numbers: integers and fractions, including negative ones</li>
 * <li>Keywords: {@code true}, {@code false}, {@code null}</li>
 * </ul>
 * </p>
 */
public class Lexer {

    /** The original JSON string to be tokenized. */
    private final String input;

    /** The current cursor position in the original string. */
    private int pos = 0;

    /**
     * Creates a new lexer to parse the passed string.
     *
     * @param input The JSON string to be tokenized (must not be {@code null})
     */
    public Lexer(String input) {
        this.input = input;
    }

    /**
     * Performs full tokenization of the input string.
     * <p>
     * Traverses the string character by character, omitting whitespace
     * and creates a token for each recognized JSON element.
     * </p>
     *
     * @return a list of {@link Token} tokens representing all elements of the input string
     * @throws RuntimeException if an unknown character is encountered
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }

            switch (c) {
                case '{' -> {
                    tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                    pos++;
                }
                case '}' -> {
                    tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                    pos++;
                }
                case '[' -> {
                    tokens.add(new Token(TokenType.LEFT_BRACKET, "["));
                    pos++;
                }
                case ']' -> {
                    tokens.add(new Token(TokenType.RIGHT_BRACKET, "]"));
                    pos++;
                }
                case ':' -> {
                    tokens.add(new Token(TokenType.COLON, ":"));
                    pos++;
                }
                case ',' -> {
                    tokens.add(new Token(TokenType.COMMA, ","));
                    pos++;
                }
                case '"' -> tokens.add(new Token(TokenType.STRING, readString()));

                default -> {
                    if (Character.isDigit(c) || c == '-') {
                        tokens.add(new Token(TokenType.NUMBER, readNumber()));
                    } else if (Character.isLetter(c)) {
                        tokens.add(readBoolean()); // true/false/null
                    } else {
                        throw new RuntimeException("Lexer error: Unexpected character '" + c + "' at pos " + pos);
                    }
                }
            }

        }
        return tokens;
    }

    /**
     * Reads a keyword starting at the current position and returns the corresponding token.
     * <p>
     * Recognizes the words {@code true}, {@code false}, and {@code null}.
     * After reading, the cursor position is set to the last character of the word.
     * </p>
     *
     * @return a token of type {@link TokenType#TRUE}, {@link TokenType#FALSE}
     * or {@link TokenType#NULL}
     * @throws JsonParseException if the word is not a valid JSON keyword
     */
    private Token readBoolean() {
        int start = pos;
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) {
            pos++;
        }
        String word = input.substring(start, pos);
        return switch (word) {
            case "true" -> new Token(TokenType.TRUE, "true");
            case "false" -> new Token(TokenType.FALSE, "false");
            case "null" -> new Token(TokenType.NULL, "null");
            default -> throw new JsonParseException("Unknown keyword: " + word);
        };
    }

    /**
     * Reads a double-quoted string starting at the current position.
     * <p>
     * Assumes the current character is the opening quotation mark {@code "}.
     * Reads characters up to the next closing quotation mark and returns
     * the string's contents without quotes.
     * </p>
     * <p>
     * <b>Note:</b> Escape sequences (e.g., {@code \"}, {@code \\})
     * are not processed in the current implementation.
     * </p>
     *
     * @return the string's contents without the enclosing quotes
     */
    private String readString() {
        pos++;
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && input.charAt(pos) != '"') {
            sb.append(input.charAt(pos));
            pos++;
        }
        pos++;
        return sb.toString();
    }

    /**
     * Reads a numeric sequence starting from the current position.
     * <p>
     * Reads digits, the period character {@code .}, and the minus sign {@code -}.
     * Supports integers, fractional numbers, and negative numbers.
     * </p>
     * <p>
     * <b>Note:</b> Numbers in scientific notation (e.g., {@code 1e10})
     * are not supported in the current implementation.
     * </p>
     *
     * @return the string representation of the number
     */
    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.' || input.charAt(pos) == '-')) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }
}