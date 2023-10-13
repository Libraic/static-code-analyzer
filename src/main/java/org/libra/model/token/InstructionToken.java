package org.libra.model.token;

import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

import java.util.Stack;

import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;
import static org.libra.utils.Constants.ASSIGNMENT_LITERAL;
import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.DIVISION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MINUS_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MULTIPLICATION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.PLUS_OPERATOR_LITERAL;

public class InstructionToken extends Token {

    public InstructionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(Stack<Node> nodes) {
        int start = findAssignmentNodeIndex(nodes);
        handleMultiplicationAndAddition(nodes, start);

        Node currentNode = new UnaryNode(this);
        for (Node node : nodes) {
            if (isParenthesis(node.getToken().getTokenType())) {
                continue;
            }
            currentNode.addNode(node);
            currentNode = node;
        }
    }

    private void handleMultiplicationAndAddition(Stack<Node> nodes, int start) {
        handleMultiplications(nodes, start);
        handleAdditions(nodes, start);
    }

    private void handleMultiplications(Stack<Node> nodes, int start) {
        for (int i = start; i < nodes.size(); ++i) {
            Node currentNode = nodes.get(i);
            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
                return;
            }

            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESIS_LITERAL)) {
                handleMultiplicationAndAddition(nodes, i + 1);
                nodes.remove(currentNode);
            } else if (isCurrentNodeMultiplicationOrDivision(currentNode.getToken().getValue()) &&
                    isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(nodes, i);
                --i;
            }
        }
    }

    private void handleAdditions(Stack<Node> nodes, int start) {
        for (int i = start; i < nodes.size(); ++i) {
            Node currentNode = nodes.get(i);
            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESIS_LITERAL)) {
                nodes.remove(currentNode);
                return;
            }

            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
                handleMultiplicationAndAddition(nodes, i + 1);
            } else if (isCurrentNodeAdditionOrSubtraction(currentNode.getToken().getValue()) &&
                isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(nodes, i);
                --i;
            }
        }
    }

    private void handleArithmeticOperations(Stack<Node> nodes, int index) {
        if (index == nodes.size() - 1) {
            return;
        }

        Node currentNode = nodes.get(index);
        if (nodes.get(index + 1).getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
            handleMultiplicationAndAddition(nodes, index + 1);
        }

        Node previousNode = nodes.get(index - 1);
        Node nextNode = nodes.get(index + 1);
        currentNode.addNode(previousNode);
        currentNode.addNode(nextNode);
        nodes.remove(previousNode);
        nodes.remove(nextNode);
    }

    private boolean isCurrentNodeMultiplicationOrDivision(Object value) {
        return value.equals(MULTIPLICATION_OPERATOR_LITERAL)
            || value.equals(DIVISION_OPERATOR_LITERAL);
    }

    private boolean isCurrentNodeAdditionOrSubtraction(Object value) {
        return value.equals(PLUS_OPERATOR_LITERAL) || value.equals(MINUS_OPERATOR_LITERAL);
    }

    private boolean isParenthesis(TokenType tokenType) {
        return tokenType.equals(OPEN_PARENTHESIS) ||
            tokenType.equals(CLOSED_PARENTHESIS);
    }

    private boolean isBinaryExpressionUnassigned(Node node) {
        return ((BinaryExpressionNode) node).getLeftHandOperand() == null &&
            ((BinaryExpressionNode) node).getRightHandOperand() == null;
    }

    private int findAssignmentNodeIndex(Stack<Node> nodes) {
        for (int i = 0; i < nodes.size(); ++i) {
            if (nodes.get(i).getToken().getValue().equals(ASSIGNMENT_LITERAL)) {
                return i;
            }
        }

        return -1;
    }
}
