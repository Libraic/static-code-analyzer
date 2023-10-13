package org.libra;

import lombok.var;
import org.libra.analysis.FileParser;
import org.libra.analysis.Lexer;
import org.libra.analysis.Parser;
import org.libra.model.token.Token;
import java.util.List;

import static org.libra.utils.Constants.FILES_RELATIVE_PATH;

public class Main {
    public static void main(String[] args) {
        String fileName = "input.txt";
        String filePath = FILES_RELATIVE_PATH + fileName;
        Lexer lexer = new Lexer();
        FileParser fileParser = new FileParser(lexer);
        List<Token> tokens = fileParser.parseFile(filePath);
        Parser parser = new Parser();
        var t = parser.parse(tokens);
        t.stringify(0);
    }
}