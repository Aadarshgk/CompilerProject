import java.nio.file.*;
import java.io.*;
import java.util.*;

public class CompilerTool {

    public static void main(String[] args) {
        try {
            // Read the C code from a file (you can also use a string input)
            String fileName = "src/test.c";  // Replace with your file name
            String sourceCode = new String(Files.readAllBytes(Paths.get(fileName)));

            // Step 1: Tokenize the source code
            List<Lexer.Token> tokens = Lexer.tokenize(sourceCode);
            System.out.println("Tokens:");
            for (Lexer.Token token : tokens) {
                System.out.println(token);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}