package org.libra.model.token;

import lombok.EqualsAndHashCode;
import org.libra.model.ParsingContext;
import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.utils.Constants.CLOSED_PARENTHESES_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESES_LITERAL;

/**
 * Binary Expression Token is represented by Arithmetic Operators and Assignment Operator.
 */
@EqualsAndHashCode(callSuper = true)
public class BinaryExpressionToken extends Token {

    public BinaryExpressionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    /**
     * If we have an assignment operator, the production of the BinaryExpressionNode will differ a little.
     * In case of assignment nodes, we can assign its left-hand operand upon creation, because we know for sure
     * no changes will be related to the left, since it is either a variable declaration or a variable reassignment.
     * In case of arithmetic operators, we are only creating the node, because the assignment will be done later on by
     * the Arithmetic Service.
     *
     * @param parsingContext the parsing context, containing the necessary nodes.
     */
    @Override
    public void produceNode(ParsingContext parsingContext) {
        if (tokenType.equals(ASSIGNMENT_OPERATOR)) {
            Node binaryExpressionNode = new BinaryExpressionNode(this);
            Node previousNode = parsingContext.retrieveAndRemoveLastNode();
            while (isParentheses(previousNode.getToken().getValue())) {
                previousNode = parsingContext.retrieveAndRemoveLastNode();
            }

            binaryExpressionNode.addNode(previousNode);
            parsingContext.addNode(binaryExpressionNode);
            return;
        }

        Node binaryExpressionNode = new BinaryExpressionNode(this);
        parsingContext.addNode(binaryExpressionNode);
    }

    private boolean isParentheses(Object value) {
        return value.equals(OPEN_PARENTHESES_LITERAL) ||
            value.equals(CLOSED_PARENTHESES_LITERAL);
    }

}
