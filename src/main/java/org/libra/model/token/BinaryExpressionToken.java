package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import static org.libra.model.token.TokenType.*;
import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESIS_LITERAL;

public class BinaryExpressionToken extends Token {

    public BinaryExpressionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        if (tokenType.equals(ASSIGNMENT_OPERATOR)) {
            Node binaryExpressionNode = new BinaryExpressionNode(this);
            Node previousNode = parsingContext.retrieveAndRemoveLastNode();
            while (isParenthesis(previousNode.getToken().getValue())) {
                previousNode = parsingContext.retrieveAndRemoveLastNode();
            }

            binaryExpressionNode.addNode(previousNode);
            parsingContext.addNode(binaryExpressionNode);
            return;
        }

        Node binaryExpressionNode = new BinaryExpressionNode(this);
        parsingContext.addNode(binaryExpressionNode);
    }

    private boolean isParenthesis(Object value) {
        return value.equals(OPEN_PARENTHESIS_LITERAL) ||
            value.equals(CLOSED_PARENTHESIS_LITERAL);
    }

}
