package org.libra.service;

import org.libra.model.ParsingContext;
import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.DIVISION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MINUS_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MULTIPLICATION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.PLUS_OPERATOR_LITERAL;

public class ArithmeticService {

    public void handleMultiplicationAndAddition(ParsingContext parsingContext, int start) {
        if (!parsingContext.isAssignmentNodePresent()) {
            return;
        }

        handleMultiplications(parsingContext, start);
        handleAdditions(parsingContext, start);
    }

    private void handleMultiplications(ParsingContext parsingContext, int start) {
        for (int i = start; i < parsingContext.getNumberOfNodes(); ++i) {
            Node currentNode = parsingContext.getNodeAt(i);
            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
                handleMultiplicationAndAddition(parsingContext, i + 1);
                parsingContext.removeNode(currentNode);
            }

            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESIS_LITERAL)) {
                return;
            } else if (isCurrentNodeMultiplicationOrDivision(currentNode.getToken().getValue()) &&
                    isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(parsingContext, i);
                --i;
            }
        }
    }

    private void handleAdditions(ParsingContext parsingContext, int start) {
        for (int i = start; i < parsingContext.getNumberOfNodes(); ++i) {
            Node currentNode = parsingContext.getNodeAt(i);
            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESIS_LITERAL)) {
                parsingContext.removeNode(currentNode);
                return;
            }

            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
                handleMultiplicationAndAddition(parsingContext, i + 1);
            } else if (isCurrentNodeAdditionOrSubtraction(currentNode.getToken().getValue()) &&
                    isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(parsingContext, i);
                --i;
            }
        }
    }

    private void handleArithmeticOperations(ParsingContext parsingContext, int index) {
        if (index == parsingContext.getNumberOfNodes() - 1) {
            return;
        }

        Node currentNode = parsingContext.getNodeAt(index);
        if (parsingContext.getNodeAt(index + 1).getToken().getValue().equals(OPEN_PARENTHESIS_LITERAL)) {
            parsingContext.removeNode(parsingContext.getNodeAt(index + 1));
            handleMultiplicationAndAddition(parsingContext, index + 1);
        }

        Node previousNode = parsingContext.getNodeAt(index - 1);
        Node nextNode = parsingContext.getNodeAt(index + 1);
        currentNode.addNode(previousNode);
        currentNode.addNode(nextNode);
        parsingContext.removeNode(previousNode);
        parsingContext.removeNode(nextNode);
    }

    private boolean isCurrentNodeMultiplicationOrDivision(Object value) {
        return value.equals(MULTIPLICATION_OPERATOR_LITERAL)
            || value.equals(DIVISION_OPERATOR_LITERAL);
    }

    private boolean isCurrentNodeAdditionOrSubtraction(Object value) {
        return value.equals(PLUS_OPERATOR_LITERAL) || value.equals(MINUS_OPERATOR_LITERAL);
    }

    private boolean isBinaryExpressionUnassigned(Node node) {
        return ((BinaryExpressionNode) node).getLeftHandOperand() == null &&
            ((BinaryExpressionNode) node).getRightHandOperand() == null;
    }
}
