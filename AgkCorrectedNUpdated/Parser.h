#ifndef PARSER_H
#define PARSER_H

#include <vector>
#include <string>
#include <iostream>

using namespace std;

class Parser {
private:
    vector<string> tokens;
    size_t currentTokenIndex;

    // Get the current token
    string currentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens[currentTokenIndex];
        }
        return "";
    }

    // Move to the next token
    void consumeToken() {
        if (currentTokenIndex < tokens.size()) {
            currentTokenIndex++;
        }
    }

    // Parse a statement
    bool parseStatement() {
        string token = currentToken();
        if (token == "if") {
            return parseIfStatement();
        } else if (token == "{") {
            return parseBlock();
        } else {
            // Parse an expression statement
            while (currentToken() != ";" && currentToken() != "") {
                consumeToken();
            }
            if (currentToken() != ";") {
                cout << "Error: Missing semicolon " << currentToken() << "'.\n";
                return false;
            }
            consumeToken(); // Consume the semicolon
            return true;
        }
    }

    // Parse a block of statements
    bool parseBlock() {
        if (currentToken() != "{") {
            cout << "Error: Expected '{' at the start of a block.\n";
            return false;
        }
        consumeToken(); // Consume "{"

        while (currentToken() != "}" && currentToken() != "") {
            if (!parseStatement()) {
                return false;
            }
        }

        if (currentToken() != "}") {
            cout << "Error: Missing '}' at the end of a block.\n";
            return false;
        }
        consumeToken(); // Consume "}"
        return true;
    }

    // Parse an if statement
    bool parseIfStatement() {
        if (currentToken() != "if") {
            return false;
        }
        consumeToken(); // Consume "if"

        if (currentToken() != "(") {
            cout << "Error: Expected '(' after 'if'.\n";
            return false;
        }
        consumeToken(); // Consume "("

        // Parse the condition
        while (currentToken() != ")" && currentToken() != "") {
            consumeToken();
        }

        if (currentToken() != ")") {
            cout << "Error: Expected ')' after condition.\n";
            return false;
        }
        consumeToken(); // Consume ")"

        // Parse the statement or block
        if (currentToken() == "{") {
            return parseBlock();
        } else {
            return parseStatement();
        }
    }

public:
    Parser(const vector<string>& tokens) : tokens(tokens), currentTokenIndex(0) {}

    bool parse() {
        while (currentTokenIndex < tokens.size()) {
            if (!parseStatement()) {
                return false;
            }
        }
        return true;
    }
};

#endif