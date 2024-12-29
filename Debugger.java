import java.util.*;
public class Debugger {

    public static String fixSemicolons(List<Lexer.Token> tokens) {
        StringBuilder correctedCode = new StringBuilder();

        for (int i = 0; i < tokens.size(); i++) {
            Lexer.Token token = tokens.get(i);
            correctedCode.append(token.value).append(" ");

            // If a statement ends without a semicolon, add it
            if (token.type == Lexer.TokenType.NUMBER || token.type == Lexer.TokenType.IDENTIFIER) {
                if (i + 1 < tokens.size() && !tokens.get(i + 1).value.equals(";")) {
                    correctedCode.append("; ");
                }
            }
        }

        return correctedCode.toString();
    }
}
