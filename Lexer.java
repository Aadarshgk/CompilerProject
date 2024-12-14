import java.util.*;
import java.util.regex.*;
public class Lexer {

    private static final Set<String> KEYWORDS = new HashSet<>(
            Arrays.asList("int","float","return","if","else","while","for")
    );


    private static final Set<Character> PUNCTUATION = new HashSet<>(
            Arrays.asList(';', '{', '}', '(', ')', ',', '.', '=')
    );

    public enum TokenType {
        KEYWORD, IDENTIFIER, OPERATOR, PUNCTUATION, NUMBER
    }
    public static class Token {
        TokenType type;
        String value;

        Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%s->%s", type, value);
        }
    }

    public static List<Token> tokenize(String sourceCode){
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        int length = sourceCode.length();

        while(i<length){
            char currentChar = sourceCode.charAt(i);
            System.out.println("The character at index "+i+" is :"+currentChar);
            //Skip the character if its a whiteSpace
            if(Character.isWhitespace(currentChar)){
                i++;
                continue;
            }

            //Match the keywords or identifiers
            if(Character.isLetter(currentChar)||currentChar == '_'){
                int start = i;
                while(i<length && Character.isLetterOrDigit(sourceCode.charAt(i)) || sourceCode.charAt(i) == '_') i++;

                String word = sourceCode.substring(start, i);
                if (KEYWORDS.contains(word)) {
                    tokens.add(new Token(TokenType.KEYWORD, word));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
                continue;
            }


            // Match numbers (integers)
            if (Character.isDigit(currentChar)) {
                int start = i;
                while (i < length && Character.isDigit(sourceCode.charAt(i))) {
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, sourceCode.substring(start, i)));
                continue;
            }

            // Match punctuation characters (semicolons, braces, etc.)
            if (PUNCTUATION.contains(currentChar)) {
                tokens.add(new Token(TokenType.PUNCTUATION, String.valueOf(currentChar)));
                i++;
                continue;
            }

            // Match operators (+, -, *, etc.)
            if ("+-*/=<>(){}".contains(String.valueOf(currentChar))) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(currentChar)));
                i++;
                continue;
            }

            // If we get here, the character is not valid (error case)
            System.err.println("Unrecognized character: " + currentChar);
            i++;
        }

        return tokens;

    }


}