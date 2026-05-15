package bg.tu_varna.sit.f24621744.task.parser;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonArray;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import jsonWork.primitiveType.JsonPrBoolean;
import jsonWork.primitiveType.JsonPrNull;
import jsonWork.primitiveType.JsonPrNumber;
import jsonWork.primitiveType.JsonPrString;

import java.util.List;

public class JsonParser {
    private final List<Token> tokens;
    private int pos = 0;

    public JsonParser(List<Token> tokens) {
        this.tokens = tokens;
    }


    public JsonType parse() {
        if (tokens.isEmpty()) return null;
        return parseValue();
    }

    public static JsonType parseString (String arguments){
        Lexer lexer = new Lexer(arguments);
        List<Token> tokens = lexer.tokenize();

        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }


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
                pos++;
                return new JsonPrNumber(Double.parseDouble(token.value));
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
            jsonObject.add(key, value);

            if (tokens.get(pos).type == TokenType.COMMA) {
                pos++;
            }
        }
        pos++;
        return jsonObject;
    }

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