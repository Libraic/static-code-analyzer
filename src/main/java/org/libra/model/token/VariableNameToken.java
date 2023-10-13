package org.libra.model.token;

import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

import java.util.Stack;

public class VariableNameToken extends Token {

    public VariableNameToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        Node variableNode = new UnaryNode(this);
        if (!nodes.empty()) {
            variableNode.addNode(nodes.pop());
        }

        nodes.push(variableNode);
    }
}
