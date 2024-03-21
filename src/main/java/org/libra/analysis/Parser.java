package org.libra.analysis;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.token.Token;

import java.util.List;
import org.libra.model.token.TokenType;

import static org.libra.utils.Constants.ZEROTH_INDEX;

/**
 * The class used for processing the tokens created by the Lexer object.
 * It contains the logic for producing the Abstract Syntax Tree object.
 */
public class Parser {

    /**
     * The method takes as input the list of tokens produced by the Lexer object.
     * The tokens are affiliated with the source code which is to be statically analyzed.
     * As a result, it produced the Abstract Syntax Tree object and return the reference node (the head of the tree).
     * The logic is based on iterating the tokens list and producing a node associated with each individual token.
     * The production of specific nodes takes into consideration the previously produced nodes, so the order in which
     * these tokens are processed is essential.
     *
     * @param tokens a list of tokens.
     * @return the reference node of the Abstract Syntax Tree object.
     */
    public Node parse(List<Token> tokens) {
        ParsingContext parsingContext = new ParsingContext();
        for (Token token : tokens) {
            token.produceNode(parsingContext);
        }

        return parsingContext.getNodeAt(ZEROTH_INDEX);
    }
}
