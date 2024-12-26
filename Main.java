import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Sample input code
        String code = "int x = 10; if (x > 5) { x = x + 1; } else { x = x - 1; }";

        // Step 1: Tokenize the input code using Lexer
        Lexer lexer = new Lexer();
        List<String> tokens = lexer.tokenize(code);
        System.out.println("Tokens: " + tokens);

        // Step 2: Parse the tokens using Parser
        Parser parser = new Parser(tokens);
        boolean isValid = parser.parse();

        // Step 3: Print result
        if (isValid) {
            System.out.println("Code is syntactically valid.");
        } else {
            System.out.println("Code contains syntax errors.");
        }
    }
}
