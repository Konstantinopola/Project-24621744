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
            default -> throw new RuntimeException("Unknown keyword: " + word);
        };
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