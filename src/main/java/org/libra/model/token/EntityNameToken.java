package org.libra.model.token;

import lombok.EqualsAndHashCode;
import org.libra.model.AccessModifier;
import org.libra.model.Membership;
import org.libra.model.ParsingContext;
import org.libra.model.State;
import org.libra.model.node.ClassNode;
import org.libra.model.node.Node;
import org.libra.model.node.SubprogramNode;
import org.libra.model.node.UnaryNode;

import java.util.Iterator;

import static org.libra.model.AccessModifier.PACKAGE_PRIVATE;
import static org.libra.model.Membership.CLASS;
import static org.libra.model.Membership.OBJECT;
import static org.libra.model.State.INHERITABLE;
import static org.libra.model.State.OVERRIDABLE;
import static org.libra.model.token.TokenType.ACCESS_MODIFIER;
import static org.libra.model.token.TokenType.CLASS_DECLARATION;
import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.model.token.TokenType.PROGRAM;
import static org.libra.model.token.TokenType.RETURN_TYPE;
import static org.libra.model.token.TokenType.STATE;
import static org.libra.model.token.TokenType.STATIC_ACCESS;

@EqualsAndHashCode(callSuper = true)
public class EntityNameToken extends Token {

    public EntityNameToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        if (tokenType.equals(CLASS_DECLARATION)) {
            addClassNodeInParsingContext(parsingContext);
        } else if (tokenType.equals(METHOD_DECLARATION)) {
            addSubprogramNodeInParsingContext(parsingContext);
        } else {
            addVariableNodeInParsingContext(parsingContext);
        }
    }

    private void addClassNodeInParsingContext(ParsingContext parsingContext) {
        AccessModifier accessModifier = PACKAGE_PRIVATE;
        State state = INHERITABLE;
        String className = (String) value;
        Iterator<Node> nodeIterator = parsingContext.getNodesIterator();

        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            Object value = currentNode.getToken().getValue();
            TokenType tokenType = currentNode.getToken().getTokenType();
            if (tokenType.equals(PROGRAM)) {
                continue;
            } else if (tokenType.equals(ACCESS_MODIFIER)) {
                accessModifier = AccessModifier.createAccessModifier((String) value);
            } else if (tokenType.equals(STATE)) {
                state = State.createState((String) value);
            } else if (tokenType.equals(TokenType.CLASS)) {
                break;
            }

            nodeIterator.remove();
        }

        Node classNode = new ClassNode(this, className, accessModifier, state);
        parsingContext.addNode(classNode);
    }

    private void addSubprogramNodeInParsingContext(ParsingContext parsingContext) {
        AccessModifier accessModifier = PACKAGE_PRIVATE;
        State state = OVERRIDABLE;
        String methodName = (String) value;
        Membership membership = OBJECT;
        String returnType = "";
        Iterator<Node> nodeIterator = parsingContext.getNodesIterator();

        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            Object value = currentNode.getToken().getValue();
            TokenType tokenType = currentNode.getToken().getTokenType();
            if (tokenType.equals(PROGRAM) || tokenType.equals(TokenType.CLASS)) {
                continue;
            } else if (tokenType.equals(ACCESS_MODIFIER)) {
                accessModifier = AccessModifier.createAccessModifier((String) value);
            } else if (tokenType.equals(STATE)) {
                state = State.createState((String) value);
            } else if (tokenType.equals(STATIC_ACCESS)) {
                membership = CLASS;
            } else if (tokenType.equals(RETURN_TYPE)) {
                returnType = (String) value;
                break;
            }

            nodeIterator.remove();
        }

        nodeIterator.remove();
        Node subprogramNode = new SubprogramNode(this, methodName, accessModifier, state, returnType, membership);
        parsingContext.addNode(subprogramNode);
    }

    private void addVariableNodeInParsingContext(ParsingContext parsingContext) {
        Node variableNode = new UnaryNode(this);
        if (!parsingContext.isAssignmentNodePresent() && parsingContext.isLastNodeDataTypeNode()) {
            Node previousNode = parsingContext.retrieveAndRemoveLastNode();
            variableNode.addNode(previousNode);
        }

        Node methodDeclarationNode = parsingContext.retrieveMethodDeclarationNode();
        if (methodDeclarationNode != null) {
            methodDeclarationNode.addNode(variableNode);
        } else {
            parsingContext.addNode(variableNode);
        }
    }
}
