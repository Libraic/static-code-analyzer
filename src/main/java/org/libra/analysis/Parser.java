package org.libra.analysis;

import org.libra.model.node.Node;
import org.libra.model.token.Token;

import java.util.List;
import java.util.Stack;

import static org.libra.utils.Constants.FIRST_ELEMENT;

/**
 * The class used for parsing the source code for analysis.
 */
public class Parser {

    public Node parse(List<Token> tokens) {
       Stack<Node> nodes = new Stack<>();

       for (Token token : tokens) {
           token.produceNode(nodes);
       }

       return nodes.get(FIRST_ELEMENT);
    }
}
