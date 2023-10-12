package org.libra;

import lombok.var;
import org.libra.analysis.Lexer;
import org.libra.model.Token;
import java.util.List;

import static org.libra.utils.Constants.FILES_RELATIVE_PATH;

public class Main {
    public static void main(String[] args) {
        String fileName = "input.txt";
        String filePath = FILES_RELATIVE_PATH + fileName;
        Lexer lexer = new Lexer();
        FileParser fileParser = new FileParser(lexer);
        List<Token> tokens = fileParser.parseFile(filePath);
        for (var token : tokens) {
            System.out.println("--------------------------------------------");
            System.out.println(token);
            System.out.println("--------------------------------------------\n");
        }
    }
}