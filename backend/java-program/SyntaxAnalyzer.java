
//        S -> ForLoop
//        ForLoop -> 'for' '(' Initialization ';' Condition ';' Increment ')' Block
//        Initialization -> Declaration | Assignment
//        Condition -> Expression
//        Increment -> Expression
//        Block -> '{' Statements '}'
//        Statements -> Statement | Statements Statement
//        Statement -> ExpressionStatement | IfElseStatement | ForLoop | WhileLoop
//        ExpressionStatement -> Expression ';'
//        Expression -> ID | ID '=' Expression | Expression '+' Expression | Expression '-' Expression | ...

//        S -> WhileLoop
//        WhileLoop -> 'while' '(' Condition ')' Block
//        Condition -> Expression
//        Block -> '{' Statements '}'
//        Statements -> Statement | Statements Statement
//        Statement -> ExpressionStatement | IfElseStatement | ForLoop | WhileLoop

//        S -> IfElseStatement
//        IfElseStatement -> 'if' '(' Condition ')' Block 'else' Block
//        Condition -> Expression
//        Block -> '{' Statements '}'
//        Statements -> Statement | Statements Statement
//        Statement -> ExpressionStatement | IfElseStatement | ForLoop | WhileLoop | Assignment

//        S -> IfStatement
//        IfStatement -> 'if' '(' Condition ')' Block
//        Condition -> Expression
//        Block -> '{' Statements '}'
//        Statements -> Statement | Statements Statement

//        S -> Expression
//        Expression -> Term | Expression '+' Term | Expression '-' Term
//        Term -> Factor | Term '*' Factor | Term '/' Factor | Term '>' Factor | Term '<' Factor
//        Factor -> ID | Number | '(' Expression ')'

//        S -> T L F';'           // Declaration of variables
//        T -> 'int' | 'float'    // Data type can be either int or float
//        L -> L ',' ID | ID          // List of variables, separated by commas |  A single variable
//        F -> '=' ID | EPSILON;
//        ID -> [a-zA-Z_][a-zA-Z0-9_]* // Identifier rule, starts with a letter or underscore, followed by letters, digits, or underscores

import java.util.*;

public class SyntaxAnalyzer {
    private List<Lexer.Token> tokens;
    private int currentPosition;

    public SyntaxAnalyzer(List<Lexer.Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
    }

    // Return the current token
    private Lexer.Token currentToken() {
        if (currentPosition < tokens.size()) {
            return tokens.get(currentPosition);
        }
        return null;
    }
    private Lexer.Token nextToken(){
        int next = currentPosition +1;
        if(next <tokens.size()){
            return tokens.get(next);
        }
        return null;
    }

    // Consume the current token and move to the next one
    private void consume() {
        if (currentPosition < tokens.size()) {
            currentPosition++;
        }
    }

    // Match a token of a specific type and value
    private boolean match(Lexer.TokenType type, String value) {
        Lexer.Token token = currentToken();
        if (token != null && token.type == type && token.value.equals(value)) {
            consume();
            return true;
        }
        return false;
    }

    // Match a token of a specific type
    private boolean match(Lexer.TokenType type) {
        Lexer.Token token = currentToken();
        if (token != null && token.type == type) {
            consume();
            return true;
        }
        return false;
    }
    private boolean matchNext(Lexer.TokenType type,String value){
        Lexer.Token token = nextToken();
        if(token !=null && token.type ==type && token.value.equals(value)){
            consume();
            return true;
        }
        return false;
    }

    // Start parsing the program
    public void parse() {
        program();
    }

    // Program -> Statement*
    private void program() {
        while (currentToken() != null) {
            statement();
        }
    }

    // Statement -> ExpressionStatement | IfElseStatement | ForLoop | WhileLoop | Assignment
    private void statement() {
        if (match(Lexer.TokenType.KEYWORD, "if")) {
            ifElseStatement();//verified
        } else if (match(Lexer.TokenType.KEYWORD, "for")) {
            forLoop();
        } else if (match(Lexer.TokenType.KEYWORD, "while")) {
            whileLoop();
        } else if(match(Lexer.TokenType.KEYWORD, "int")||match(Lexer.TokenType.KEYWORD, "float")){
            declaration(); //verified
        }
        else {//verified
            if (matchNext(Lexer.TokenType.PUNCTUATION, "=")) {
                assignment();
            } else {
                expressionStatement(); // Handles standalone expressions
            }
        }


    }

    // ExpressionStatement -> Expression ';'
    private void expressionStatement() {
        expression();
        if(!match(Lexer.TokenType.PUNCTUATION, ";")){
            System.out.println("Semi colon missing at :"+(currentPosition-1));
        }
    }
    //this if for handling for loop
    private void expressionStatement1() {
        expression();
    }

    // Expression -> Term | Expression '+' Term | Expression '-' Term
    private void expression() {
        term();
        while (currentToken() != null && (match(Lexer.TokenType.OPERATOR, "+") || match(Lexer.TokenType.OPERATOR, "-"))) {
            term();
        }
    }

    // Term -> Factor | Term '*' Factor | Term '/' Factor| Term '>' Factor | Term '<' Factor
    private void term() {
        factor();
        while (currentToken() != null && (match(Lexer.TokenType.OPERATOR, "*") ||
                match(Lexer.TokenType.OPERATOR, "/")||
                match(Lexer.TokenType.OPERATOR, ">") ||
                match(Lexer.TokenType.OPERATOR, "<") ||
                match(Lexer.TokenType.OPERATOR, "<=")||
                match(Lexer.TokenType.OPERATOR, ">=")
        )) {
            factor();
        }
    }

    // Factor -> ID | Number | '(' Expression ')'
    private void factor() {
        if (match(Lexer.TokenType.IDENTIFIER)) {
            // Matched identifier
        } else if (match(Lexer.TokenType.NUMBER)) {
            // Matched number
        } else if (match(Lexer.TokenType.PUNCTUATION, "(")) {
            expression();
            match(Lexer.TokenType.PUNCTUATION, ")");
        } else {
            throw new RuntimeException("Syntax error: Expected identifier, number or '('");
        }
    }

    // IfElseStatement -> 'if' '(' Condition ')' Block 'else' Block
    private void ifElseStatement() {
        match(Lexer.TokenType.KEYWORD, "if");
        match(Lexer.TokenType.PUNCTUATION, "(");
        condition();
        match(Lexer.TokenType.PUNCTUATION, ")");
        block();
        match(Lexer.TokenType.KEYWORD, "else");
        block();
    }

    // Condition -> Expression
    private void condition() {
        expression();
    }

    // Block -> '{' Statements '}'
    private void block() {
        match(Lexer.TokenType.PUNCTUATION, "{");
        statements();
        match(Lexer.TokenType.PUNCTUATION, "}");

    }

    // Statements -> Statement | Statements Statement
    private void statements() {
        while (currentToken() != null && currentToken().type != Lexer.TokenType.PUNCTUATION) {
            statement();
        }
    }

    // ForLoop -> 'for' '(' Initialization ';' Condition ';' Increment ')' Block
    private void forLoop() {
        match(Lexer.TokenType.KEYWORD, "for");
        match(Lexer.TokenType.PUNCTUATION, "(");
        declaration();
//        match(Lexer.TokenType.PUNCTUATION, ";");
        condition();
        if(!match(Lexer.TokenType.PUNCTUATION, ";")){
            System.out.println("Semi colon missing at :"+(currentPosition-1));
        }
        increment();
        match(Lexer.TokenType.PUNCTUATION, ")");
        block();
    }

    // Initialization -> Declaration | Assignment
    private void initialization() {
        if (match(Lexer.TokenType.KEYWORD, "int") || match(Lexer.TokenType.KEYWORD, "float")) {
            declaration();
        } else {
            assignment();
        }
    }

    // Declaration -> 'int' | 'float' L F ';'
    private void declaration() {
        // Match the type ('int' or 'float')
        if (match(Lexer.TokenType.KEYWORD, "int") || match(Lexer.TokenType.KEYWORD, "float")) {
            // Process the list of variables
            listOfVariables();

            // Optional assignment: check for '='
            if (match(Lexer.TokenType.PUNCTUATION, "=")) {
                // Match the assignment expression
                expression();
            }

            // Ensure the statement ends with a semicolon
            if(!match(Lexer.TokenType.PUNCTUATION, ";")){
                System.out.println("Semi colon missing at :"+(currentPosition-1));
            }
        } else {
            throw new RuntimeException("Syntax error: Expected 'int' or 'float'");
        }
    }

//     List of variables -> L ',' ID | ID
    private void listOfVariables() {
        // Start by matching one identifier
        match(Lexer.TokenType.IDENTIFIER);

        // Check if there are more identifiers separated by commas
        while (match(Lexer.TokenType.PUNCTUATION, ",")) {
            match(Lexer.TokenType.IDENTIFIER);
        }
    }

//    // Assignment -> ID '=' Expression
    private void assignment() {
        match(Lexer.TokenType.IDENTIFIER);
        match(Lexer.TokenType.PUNCTUATION, "=");
        expressionStatement();
    }
    //this is exclusive for handling for loop
    private void assignment1() {
        match(Lexer.TokenType.IDENTIFIER);
        match(Lexer.TokenType.PUNCTUATION, "=");
        expressionStatement1();
    }
//
//    // Increment -> Expression
    private void increment() {
        assignment1();
    }

    // WhileLoop -> 'while' '(' Condition ')' Block
    private void whileLoop() {
        match(Lexer.TokenType.KEYWORD, "while");
        match(Lexer.TokenType.PUNCTUATION, "(");
        condition();
        match(Lexer.TokenType.PUNCTUATION, ")");
        block();
    }
}