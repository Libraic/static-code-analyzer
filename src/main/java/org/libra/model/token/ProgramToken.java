package org.libra.model.token;

import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;

import java.util.Stack;

public class ProgramToken extends Token {

    public ProgramToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        Node programNode = new ProgramNode(this);
        nodes.push(programNode);
    }
}
