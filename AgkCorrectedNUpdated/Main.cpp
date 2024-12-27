#include <iostream>
#include <string>
#include <vector>
#include "Lexer.h"
#include "Parser.h"

using namespace std;

int main() {
    // Sample code input (test cases)
    string code1 = "int x = 10; int y=10; if (x > 5) { x = x + 1;} ";  // Valid
    string code2 = "int x = 10 ;if  { x = x + 1; }";   // Missing semicolon

    // Tokenize the input
    Lexer lexer;
    vector<string> tokens1 = lexer.tokenize(code1);
    vector<string> tokens2 = lexer.tokenize(code2);

    // Parse the tokens
    Parser parser1(tokens1);
    bool isValid1 = parser1.parse();
    cout<<"Code 1:-"<<endl;
    cout<<code1<<endl;
    cout << "Code1 is " << (isValid1 ? "valid" : "invalid") << ".\n";
    cout<<"Code 2:-"<<endl;
    cout<<code2<<endl;
    Parser parser2(tokens2);
    bool isValid2 = parser2.parse();
    cout << "Code2 is " << (isValid2 ? "valid" : "invalid") << ".\n";

    return 0;
}