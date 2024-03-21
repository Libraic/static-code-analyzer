package org.libra.model.token;

import lombok.EqualsAndHashCode;
import org.libra.model.ParsingContext;
import org.libra.model.node.ClassNode;
import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;
import org.libra.model.node.UnaryNode;
import org.libra.service.ArithmeticService;

import java.util.Iterator;

import static org.libra.model.token.TokenType.CLASS_DECLARATION;
import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.model.token.TokenType.PROGRAM;
import static org.libra.utils.Constants.CLOSED_PARENTHESES_LITERAL;
import static org.libra.utils.Constants.COMMA_LITERAL;
import static org.libra.utils.Constants.OPEN_CURLY_BRACE_LITERAL;
import static org.libra.utils.Constants.OPEN_PARENTHESES_LITERAL;

/**
 * The instruction token which will create a dedicated Node to be added to a specific list of instruction inside
 * a subprogram, since all the instructions in Java occur inside a method.
 */
@EqualsAndHashCode(callSuper = true)
public class InstructionToken extends Token {

    private final ArithmeticService arithmeticService;

    public InstructionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
        arithmeticService = new ArithmeticService();
    }

    /**
     * Before assigning an instruction to a subprogram, we have to create it.
     * We firstly check if we have some arithmetic operations, in which case we delegate the creation of dedicated
     * arithmetic nodes to the Arithmetic Service. After the arithmetic analysis is performed, we create the instruction
     * node. The instruction will contain all the nodes in the parsing context that have been created so far (or from
     * the last instruction, since we remove the nodes from the context once we assign them to instruction). We
     * basically create a subtree and hold the reference of this tree in the instructions list inside a certain
     * subprogram. If we find some redundant nodes, we simply remove them from the context, since they are not relevant
     * anymore.
     *
     * @param parsingContext the parsing context, containing the necessary nodes.
     */
    @Override
    public void produceNode(ParsingContext parsingContext) {
        int start = parsingContext.findAssignmentNodeIndex();
        if (start != -1) {
            arithmeticService.handleMultiplicationAndAddition(parsingContext, start);
        }

        Node instructionNode = new UnaryNode(this);
        Iterator<Node> nodeIterator = parsingContext.getNodesIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            if (isRedundantToken(currentNode.getToken().getValue())) {
                nodeIterator.remove();
                continue;
            }

            if (instructionNode.getToken().getTokenType().equals(PROGRAM) &&
                !currentNode.getToken().getTokenType().equals(CLASS_DECLARATION)
            ) {
                assert instructionNode instanceof ProgramNode;
                Node lastClassNode = ((ProgramNode) instructionNode).getLastClass();
                assert lastClassNode instanceof ClassNode;
                if (currentNode.getToken().getTokenType().equals(METHOD_DECLARATION)) {
                    lastClassNode.addNode(currentNode);
                } else {
                    Node lastSubprogramNode = ((ClassNode) lastClassNode).getLastSubprogram();
                    lastSubprogramNode.addNode(currentNode);
                }
            } else {
                instructionNode.addNode(currentNode);
            }

            instructionNode = currentNode;
            if (!currentNode.getToken().getTokenType().equals(PROGRAM)) {
                nodeIterator.remove();
            }
        }
    }

    private boolean isRedundantToken(Object value) {
        return value.equals(OPEN_PARENTHESES_LITERAL) ||
            value.equals(CLOSED_PARENTHESES_LITERAL) ||
            value.equals(OPEN_CURLY_BRACE_LITERAL) ||
            value.equals(COMMA_LITERAL);
    }
}
