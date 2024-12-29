import java.nio.file.*;
import java.io.*;
import java.util.*;
public class CompilerTool {

    public static void main(String[] args) {
        try {
            // Read the C code from a file
            String fileName = "test.c";
            String sourceCode = new String(Files.readAllBytes(Paths.get(fileName)));
            //String sourceCode = "int x = 0; if (x > 0) { x = x + 1; }";
            // Step 1: Tokenize the source code
            List<Lexer.Token> tokens = Lexer.tokenize(sourceCode);
            System.out.println("Tokens:");
            for (Lexer.Token token : tokens) {
                System.out.println(token);
            }

            // Step 2: Analyze syntax
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);
            System.out.println("\nSyntax Analysis:");
            syntaxAnalyzer.parse();


            // Step 3: Fix missing semicolons
//            System.out.println("\nCorrected Code:");
//            String correctedCode = Debugger.fixSemicolons(tokens);
//            System.out.println(correctedCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
