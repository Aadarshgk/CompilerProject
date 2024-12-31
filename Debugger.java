import java.util.*;
import java.io.*;
public class Debugger {

    public static void fixSemicolons(List<Lexer.Token> tokens) {
        List<Integer> numbers = new ArrayList<>();

        // Try with resources to ensure the file is closed after reading
        try (BufferedReader br = new BufferedReader(new FileReader("errors.txt"))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                if (line.contains("Semi colon missing at :")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        // Parse the number and add it to the list
                        numbers.add(Integer.parseInt(parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("correctedcode.txt"))) {
            int j = 0;
            for (int i = 0; i < tokens.size(); i++) {
                Lexer.Token token = tokens.get(i);
                //System.out.println("Token is :"+token +"  Token Number is :"+i);
                writer.write(token.value + " ");
                if(token.value.equals("}")||token.value.equals("{")){
                    writer.write("\n");
                }

                if (j<numbers.size() && numbers.get(j) == i ) {
                    writer.write(" ; ");
                    writer.write("\n");
                    j++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
