package org.libra.model.token;

import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

import java.util.Stack;

public class DataTypeToken extends Token {

    public DataTypeToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        Node programNode = new UnaryNode(this);
        nodes.push(programNode);
    }
}
