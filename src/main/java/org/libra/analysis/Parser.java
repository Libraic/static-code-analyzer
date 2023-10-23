package org.libra.analysis;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.token.Token;

import java.util.List;

import static org.libra.utils.Constants.FIRST_ELEMENT;

/**
 * The class used for parsing the source code for analysis.
 */
public class Parser {

    public Node parse(List<Token> tokens) {
        ParsingContext parsingContext = new ParsingContext();

       for (Token token : tokens) {
           token.produceNode(parsingContext);
       }

       return parsingContext.getNodeAt(FIRST_ELEMENT);
    }
}
