package org.libra.service;

import org.libra.model.ParsingContext;
import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;

import static org.libra.utils.Constants.CLOSED_PARENTHESES_LITERAL;
import static org.libra.utils.Constants.DIVISION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MINUS_OPERATOR_LITERAL;
import static org.libra.utils.Constants.MULTIPLICATION_OPERATOR_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESES_LITERAL;
import static org.libra.utils.Constants.PLUS_OPERATOR_LITERAL;

/**
 * A dedicated service to help with operators precedence during arithmetic instructions' evaluation.
 * The rule is as follows: The priority is given to the expressions in parentheses, followed by multiplication
 * or division operators, followed by addition or subtraction operators.
 * <br><br>
 * The approach used by the service is quite simple - it does exactly as in the math classes. We take the raw expression
 * and handle the multiplication/division, followed by addition/subtraction. If we find an open parentheses during the
 * iteration, then we know for sure we have a sub-expression that needs to be evaluated the same way we are evaluating
 * the current one, so we call the <strong>handleMultiplicationAndAddition</strong> method again. This is performed
 * until we reach the end of the expression, by which time we know for sure we have evaluated every single arithmetic
 * operator.
 */
public class ArithmeticService {

    /**
     * The starting point of arithmetic evaluation. It receives the parsing context which contains the nodes of interest
     * and a starting index. The starting index is relevant for later recursive calls, to know exactly where the
     * current expression that needs to be evaluated starts. The first call of the method will have <i>start</i> equal
     * to 0.
     * @param parsingContext the parsing context containing the nodes of interest.
     * @param start the starting position of the first node in the current arithmetic expression.
     */
    public synchronized void handleMultiplicationAndAddition(ParsingContext parsingContext, int start) {
        if (!parsingContext.isAssignmentNodePresent()) {
            return;
        }

        handleMultiplications(parsingContext, start);
        handleAdditions(parsingContext, start);
    }

    /**
     * A dedicated method to handle only multiplications/divisions. The division by a number is basically multiplying
     * the first factor to 1 over the second factor, therefore the naming of the method was chosen to reflect the
     * multiplication and division as a multiplication operation. If we find multiplication or division signs, then we
     * handle the operations; otherwise, we keep iterating through nodes to check if we have such operations. In case
     * we find an open parentheses, we call the entry-point of the service with the starting index of the new arithmetic
     * expression.
     * @param parsingContext the parsing context containing the nodes of interest.
     * @param start the starting position of the first node in the current arithmetic expression.
     */
    private void handleMultiplications(ParsingContext parsingContext, int start) {
        for (int i = start; i < parsingContext.getNumberOfNodes(); ++i) {
            Node currentNode = parsingContext.getNodeAt(i);
            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESES_LITERAL)) {
                handleMultiplicationAndAddition(parsingContext, i + 1);
                parsingContext.removeNode(currentNode);
            }

            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESES_LITERAL)) {
                return;
            } else if (isCurrentNodeMultiplicationOrDivision(currentNode.getToken().getValue()) &&
                    isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(parsingContext, i);
                --i;
            }
        }
    }

    /**
     * A dedicated method to handle only additions/subtractions. The subtraction from a number is basically adding the
     * negative version of the initial term, therefore the naming of the method was chosen to reflect the
     * addition and subtraction as an addition operation. If we find addition or subtraction signs, then we
     * handle the operations; otherwise, we keep iterating through nodes to check if we have such operations. In case
     * we find an open parentheses, we call the entry-point of the service with the starting index of the new arithmetic
     * expression. Once we find a closed parenthesis, we know we have hit the end of the sub-instruction, because the
     * multiplication and division operations were already handled; therefore, we can safely remove the parentheses from
     * the parsing context, since it will be of no use anymore.
     * @param parsingContext the parsing context containing the nodes of interest.
     * @param start the starting position of the first node in the current arithmetic expression.
     */
    private synchronized void handleAdditions(ParsingContext parsingContext, int start) {
        for (int i = start; i < parsingContext.getNumberOfNodes(); ++i) {
            Node currentNode = parsingContext.getNodeAt(i);
            if (currentNode.getToken().getValue().equals(CLOSED_PARENTHESES_LITERAL)) {
                parsingContext.removeNode(currentNode);
                return;
            }

            if (currentNode.getToken().getValue().equals(OPEN_PARENTHESES_LITERAL)) {
                handleMultiplicationAndAddition(parsingContext, i + 1);
            } else if (isCurrentNodeAdditionOrSubtraction(currentNode.getToken().getValue()) &&
                    isBinaryExpressionUnassigned(currentNode)
            ) {
                handleArithmeticOperations(parsingContext, i);
                --i;
            }
        }
    }

    /**
     * If we have found an arithmetic operator, we basically take the numbers from the left, respectively from the right
     * of the operator and create a BinaryExpression. Also, we remove the numbers depicted as nodes from the parsing
     * context.
     * @param parsingContext the parsing context containing the nodes of interest.
     * @param index the index of the current arithmetic operator.
     */
    private synchronized void handleArithmeticOperations(ParsingContext parsingContext, int index) {
        // Even tough a certain operator was already handled and has left and right hand operands assigned, we may
        // interact with this particular node again during recursive calls. Therefore, we have to make sure that, if
        // the node is last in the stack, it will not get processed once again. So we interrupt the current call.
        if (index == parsingContext.getNumberOfNodes() - 1) {
            return;
        }

        Node currentNode = parsingContext.getNodeAt(index);
        if (parsingContext.getNodeAt(index + 1).getToken().getValue().equals(OPEN_PARENTHESES_LITERAL)) {
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
        return value.equals(MULTIPLICATION_OPERATOR_LITERAL) || value.equals(DIVISION_OPERATOR_LITERAL);
    }

    private boolean isCurrentNodeAdditionOrSubtraction(Object value) {
        return value.equals(PLUS_OPERATOR_LITERAL) || value.equals(MINUS_OPERATOR_LITERAL);
    }

    private boolean isBinaryExpressionUnassigned(Node node) {
        return ((BinaryExpressionNode) node).getLeftHandOperand() == null &&
            ((BinaryExpressionNode) node).getRightHandOperand() == null;
    }
}
