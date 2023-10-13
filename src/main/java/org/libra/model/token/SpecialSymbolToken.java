package org.libra.model.token;

import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

import java.util.Stack;

public class SpecialSymbolToken extends Token {

    public SpecialSymbolToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        UnaryNode constantNode = new UnaryNode(this);
        nodes.push(constantNode);
    }
}
