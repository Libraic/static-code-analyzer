package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;

public class BinaryExpressionToken extends Token {

    public BinaryExpressionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        if (tokenType.equals(TokenType.ASSIGNMENT_OPERATOR)) {
            Node binaryExpressionNode = new BinaryExpressionNode(this);
            Node previousNode = parsingContext.retrieveAndRemoveLastNode();
            while (isParenthesis(previousNode.getToken().getTokenType())) {
                previousNode = parsingContext.retrieveAndRemoveLastNode();
            }

            binaryExpressionNode.addNode(previousNode);
            parsingContext.addNode(binaryExpressionNode);
            return;
        }

        Node binaryExpressionNode = new BinaryExpressionNode(this);
        parsingContext.addNode(binaryExpressionNode);
    }

    private boolean isParenthesis(TokenType tokenType) {
        return tokenType.equals(OPEN_PARENTHESIS) ||
            tokenType.equals(CLOSED_PARENTHESIS);
    }

}
