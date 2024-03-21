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
        // If we have an assignment node, it is probable that we have some arithmetic operations.
        // In this case, we delegate this task, namely to search for arithmetic operations to the arithmeticService.
        int start = parsingContext.findAssignmentNodeIndex();
        if (start != -1) {
            arithmeticService.handleMultiplicationAndAddition(parsingContext, start);
        }

        // Then, we proceed to create the instruction node. This node acts as an aggregator for the previous nodes.
        // The previous nodes are the ones created from the last instruction.
        // "Instruction" word can be a little confusing, since, in this application, we consider Classes/Methods declaration
        // instructions as well.
        Node instructionNode = new UnaryNode(this);
        Iterator<Node> nodeIterator = parsingContext.getNodesIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();

            // If we find some redundant instructions, we delete them, since they do not present any interest for deconstructing the program.
            if (isRedundantToken(currentNode.getToken().getValue())) {
                nodeIterator.remove();
                continue;
            }

            // If the instruction node is of type PROGRAM, we have to be careful what node is about to be added, since some nodes have a dedicated
            // place and have to be added only there.
            // If the current node is a CLASS_DECLARATION, we have to develop a dedicated
            // logic to add the node, since it has to be added precisely in the classes list of the program node. Therefore, we have to omit
            // these types of nodes.
            if (instructionNode.getToken().getTokenType().equals(PROGRAM) &&
                !currentNode.getToken().getTokenType().equals(CLASS_DECLARATION)
            ) {
                assert instructionNode instanceof ProgramNode;
                Node lastClassNode = ((ProgramNode) instructionNode).getLastClass();
                assert lastClassNode instanceof ClassNode;
                // If we have a method declaration, we have to add it in the ClassNode, since all methods, in Java, are inside a class.
                if (currentNode.getToken().getTokenType().equals(METHOD_DECLARATION)) {
                    lastClassNode.addNode(currentNode);
                } else {
                    // If we have any other type of instructions, they are to be added in the SubprogramNode, since all the instructions in Java
                    // have to reside inside a method.
                    Node lastSubprogramNode = ((ClassNode) lastClassNode).getLastSubprogram();
                    lastSubprogramNode.addNode(currentNode);
                }
            } else {
                // In any other situation, we just add the node, letting each particular type of Node decide the way the nodes will be added.
                instructionNode.addNode(currentNode);
            }

            // The adding of nodes is done in a recursive-like manner. We take the instruction node, we "enrich" it with the current node
            // that was extracted from the ParsingContext and the instruction node becomes the currentNode.
            // The instructionNode is a general reference to the current instruction that is being built. However, all the nodes that are to be added
            // somehow depend on the current node. Therefore, we have to aggregate these nodes together. So, we assign the currentNode to the
            // instructionNode.
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
