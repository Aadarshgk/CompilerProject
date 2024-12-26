import java.util.*;

public class Parser {
    private List<String> tokens;
    private int currentTokenIndex;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    private String currentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null;
    }

    private void consumeToken() {
        if (currentTokenIndex < tokens.size()) {
            currentTokenIndex++;
        }
    }

    public boolean parse() {
        while (currentToken() != null) {
            if (currentToken().equals("if") || currentToken().equals("while") || currentToken().equals("for")) {
                if (!parseControlStructure()) {
                    return false;
                }
            } else if (currentToken().equals(";")) {
                consumeToken(); // Skip the semicolon
            } else if (currentToken().equals("{")) {
                if (!parseBlock()) {
                    return false;
                }
            } else {
                // Assume it's an assignment like "x = 10;"
                consumeToken();  // Skip assignment identifier
                if (!currentToken().equals("=")) {
                    return false;
                }
                consumeToken();
                if (!parseExpression()) {
                    return false;
                }
                if (!currentToken().equals(";")) {
                    System.out.println("Error: Missing semicolon.");
                    return false;
                }
                consumeToken();  // Skip semicolon
            }
        }
        return true;
    }

    private boolean parseControlStructure() {
        consumeToken(); // Consume "if", "while", or "for"
        if (!currentToken().equals("(")) {
            System.out.println("Error: Missing opening parenthesis.");
            return false;
        }
        consumeToken(); // Consume "("
        if (!parseExpression()) {
            System.out.println("Error: Invalid condition expression.");
            return false;
        }
        if (!currentToken().equals(")")) {
            System.out.println("Error: Missing closing parenthesis.");
            return false;
        }
        consumeToken(); // Consume ")"
        return parseStatement();
    }

    // Updated parseExpression to handle operators and parentheses
    private boolean parseExpression() {
        // Start by parsing the first term
        if (!parseTerm()) {
            return false;
        }

        // Now, parse any operators and additional terms (e.g., x + y or x + y - z)
        while (currentToken() != null && (currentToken().equals("+") || currentToken().equals("-"))) {
            String operator = currentToken(); // Consume + or -
            consumeToken(); // Consume operator
            if (!parseTerm()) {
                System.out.println("Error: Invalid expression after operator " + operator);
                return false;
            }
        }

        return true;
    }

    // Parse a term, which can be a number, identifier, or parenthesized expression
    private boolean parseTerm() {
        // Handle numbers and identifiers
        if (currentToken().matches("[a-zA-Z_][a-zA-Z0-9_]*") || currentToken().matches("\\d+")) {
            consumeToken(); // Consume identifier or number
            return true;
        }

        // Handle parentheses (e.g., (x + y))
        if (currentToken().equals("(")) {
            consumeToken(); // Consume '('
            if (!parseExpression()) {
                System.out.println("Error: Invalid expression inside parentheses.");
                return false;
            }
            if (!currentToken().equals(")")) {
                System.out.println("Error: Missing closing parenthesis.");
                return false;
            }
            consumeToken(); // Consume ')'
            return true;
        }

        // If we don't match a number, identifier, or parentheses, it's an error
        System.out.println("Error: Invalid term.");
        return false;
    }

    private boolean parseStatement() {
        if (currentToken().equals("{")) {
            return parseBlock();
        } else {
            if (!currentToken().equals(";")) {
                System.out.println("Error: Missing semicolon.");
                return false;
            }
            consumeToken(); // Consume semicolon
        }
        return true;
    }

    private boolean parseBlock() {
        consumeToken(); // Consume "{"
        while (currentToken() != null && !currentToken().equals("}")) {
            if (!parse()) {
                return false;
            }
        }
        if (!currentToken().equals("}")) {
            System.out.println("Error: Missing closing bracket.");
            return false;
        }
        consumeToken(); // Consume "}"
        return true;
    }
}
