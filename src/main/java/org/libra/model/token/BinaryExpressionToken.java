package org.libra.model.token;

import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import java.util.Stack;

import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;

public class BinaryExpressionToken extends Token {

    public BinaryExpressionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        if (tokenType.equals(TokenType.ASSIGNMENT_OPERATOR)) {
            Node binaryExpressionNode = new BinaryExpressionNode(this);
            Node previousNode = nodes.pop();
            while (isParenthesis(previousNode.getToken().getTokenType())) {
                previousNode = nodes.pop();
            }

            binaryExpressionNode.addNode(previousNode);
            nodes.push(binaryExpressionNode);
            return;
        }

        Node binaryExpressionNode = new BinaryExpressionNode(this);
        nodes.push(binaryExpressionNode);
    }

    private boolean isParenthesis(TokenType tokenType) {
        return tokenType.equals(OPEN_PARENTHESIS) ||
            tokenType.equals(CLOSED_PARENTHESIS);
    }

}
