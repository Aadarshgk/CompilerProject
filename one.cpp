#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cctype>

using namespace std;

// Function to trim whitespace from a string
string trim(const string& str) {
    size_t first = str.find_first_not_of(" \t\n\r");
    size_t last = str.find_last_not_of(" \t\n\r");
    return (first == string::npos || last == string::npos) ? "" : str.substr(first, last - first + 1);
}

// Function to check if a token suggests a statement needing a semicolon
bool needsSemicolon(const string& token) {
    static vector<string> keywords = {"int", "float", "double", "char", "bool", "long", "short", "unsigned", "void", "return"};
    for (const auto& keyword : keywords) {
        if (token.find(keyword) == 0) {
            return true;
        }
    }
    return false;
}

int main() {
    string inputFileName;
    cout << "Enter the name of the input file: ";
    cin >> inputFileName;

    ifstream inputFile(inputFileName);
    if (!inputFile) {
        cerr << "Error: Unable to open file " << inputFileName << endl;
        return 1;
    }

    string token;
    int lineNumber = 0;
    bool missingSemicolonFound = false;

    while (getline(inputFile, token)) {
        lineNumber++;
        token = trim(token);

        // Skip empty lines or non-relevant tokens
        if (token.empty() || token == "{" || token == "}") {
            continue;
        }

        // Check if the token needs a semicolon and if it's missing one
        if (needsSemicolon(token) && token.back() != ';') {
            cout << "Missing semicolon after token at line " << lineNumber << ": " << token << endl;
            missingSemicolonFound = true;
        }
    }

    if (!missingSemicolonFound) {
        cout << "All semicolons are properly placed." << endl;
    }

    return 0;
}
