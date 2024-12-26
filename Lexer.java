import java.util.*;
import java.util.regex.*;

public class Lexer {
    private static final String WHITESPACE = "[ \t\r\n]+";
    private static final String KEYWORDS = "(if|else|while|for|int|void|return)";
    private static final String OPERATORS = "(\\+|\\-|\\*|\\/|=|==|!=|<|>|<=|>=)";
    private static final String IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String NUMBER = "\\d+";
    private static final String SYMBOLS = "[;(){}]";
    private static final String ALL_TOKENS = WHITESPACE + "|" + KEYWORDS + "|" + OPERATORS + "|" + IDENTIFIER + "|" + NUMBER + "|" + SYMBOLS;

    private static final Pattern pattern = Pattern.compile(ALL_TOKENS);

    public List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (!matcher.group().matches(WHITESPACE)) {
                tokens.add(matcher.group());
            }
        }

        return tokens;
    }
}
