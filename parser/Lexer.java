package bg.tu_varna.sit.f24621744.task.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
            } else if (c == '{') {
                tokens.add(new Token(TokenType.LEFT_BRACE, "{")); pos++;
            } else if (c == '}') {
                tokens.add(new Token(TokenType.RIGHT_BRACE, "}")); pos++;
            } else if (c == '[') {
                tokens.add(new Token(TokenType.LEFT_BRACKET, "[")); pos++;
            } else if (c == ']') {
                tokens.add(new Token(TokenType.RIGHT_BRACKET, "]")); pos++;
            } else if (c == ':') {
                tokens.add(new Token(TokenType.COLON, ":")); pos++;
            } else if (c == ',') {
                tokens.add(new Token(TokenType.COMMA, ",")); pos++;
            } else if (c == '"') {
                tokens.add(new Token(TokenType.STRING, readString()));
            } else if (Character.isDigit(c) || c == '-') {
                tokens.add(new Token(TokenType.NUMBER, readNumber()));
            } else if (input.startsWith("true", pos)) {
                tokens.add(new Token(TokenType.TRUE, "true")); pos += 4;
            } else if (input.startsWith("false", pos)) {
                tokens.add(new Token(TokenType.FALSE, "false")); pos += 5;
            } else if (input.startsWith("null", pos)) {
                tokens.add(new Token(TokenType.NULL, "null")); pos += 4;
            } else {
                throw new RuntimeException("Lexer error: Unexpected character '" + c + "' at pos " + pos);
            }
        }
        return tokens;
    }

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

    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.' || input.charAt(pos) == '-')) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }
}