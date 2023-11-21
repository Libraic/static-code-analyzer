package org.libra.analysis;

import org.libra.model.node.Node;
import org.libra.model.token.Token;

import java.util.List;
import java.util.Map;

import static org.libra.utils.Constants.FILES_RELATIVE_PATH;

public class AstProducer {

    private final FileParser fileParser;
    private final Lexer lexer;
    private final Parser parser;

    public AstProducer() {
        fileParser = new FileParser();
        lexer = new Lexer();
        parser = new Parser();
    }

    /**
     * This method represents the entry point of the application. It takes as input the name of the file that contains
     * the source code. It parses the input file and extract all the keywords, which inputs them to the Lexer object.
     * This is responsible for producing the relevant tokens. Finally, these tokens are fed to the Parser object,
     * which is responsible for producing the Abstract Syntax Tree object.
     *
     * @param fileName the name of the file that contains the source code which is to be converted into the
     *                 Abstract Syntax Tree structure.
     * @return the produced Abstract Syntax Tree.
     */
    public Node produceAst(String fileName) {
        String filePath = FILES_RELATIVE_PATH + fileName;
        Map<Integer, List<String>> keywordsFromEachRow = fileParser.parseFile(filePath);
        List<Token> tokens = lexer.tokenize(keywordsFromEachRow);
        return parser.parse(tokens);
    }
}
