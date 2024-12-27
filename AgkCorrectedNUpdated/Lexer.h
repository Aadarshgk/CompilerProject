#ifndef LEXER_H
#define LEXER_H

#include <string>
#include <vector>
#include <regex>

using namespace std;

class Lexer {
public:
    vector<string> tokenize(const string& input) {
        vector<string> tokens;
        regex tokenRegex(R"(\b(if|else|while|for|int|void|return)\b|[a-zA-Z_][a-zA-Z0-9_]*|\d+|[\+\-\*/=<>!;(){}])");
        auto words_begin = sregex_iterator(input.begin(), input.end(), tokenRegex);
        auto words_end = sregex_iterator();

        for (sregex_iterator it = words_begin; it != words_end; ++it) {
            string token = it->str();
            tokens.push_back(token);
        }

        return tokens;
    }
};

#endif