import java.nio.file.*;
import java.io.*;
import java.util.*;
public class CompilerTool {

    public static void main(String[] args) {
        try {
            // Read the C code from a file
            String fileName = "input.txt";
            String sourceCode = new String(Files.readAllBytes(Paths.get(fileName)));

            List<Lexer.Token> tokens = Lexer.tokenize(sourceCode);
            try (BufferedWriter lexerWriter = new BufferedWriter(new FileWriter("lexer.txt"))) {
                for (Lexer.Token token : tokens) {
                    lexerWriter.write(token.toString());
                    lexerWriter.newLine();
                }
            }

            // Step 2: Analyze syntax
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);

            try {
                // Redirect System.out to a common file
                PrintStream fileOut = new PrintStream(new File("errors.txt"));
                System.setOut(fileOut);

                syntaxAnalyzer.parse();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            // Step 3: Fix missing semicolons
//            System.out.println("\nCorrected Code:");
//            String correctedCode = Debugger.fixSemicolons(tokens);
//            System.out.println(correctedCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
