package org.libra.model;

import org.libra.model.node.Node;

import java.util.Iterator;
import java.util.Stack;

import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.utils.Constants.ASSIGNMENT_LITERAL;

public class ParsingContext {

    private final Stack<Node> nodes;

    public ParsingContext() {
        nodes = new Stack<>();
    }

    public Node retrieveAndRemoveLastNode() {
        return nodes.pop();
    }

    public void addNode(Node node) {
        nodes.push(node);
    }

    public Iterator<Node> getNodesIterator() {
        return nodes.iterator();
    }

    public int findAssignmentNodeIndex() {
        for (int i = 0; i < nodes.size(); ++i) {
            if (nodes.get(i).getToken().getValue().equals(ASSIGNMENT_LITERAL)) {
                return i;
            }
        }

        return -1;
    }

    public int getNumberOfNodes() {
        return nodes.size();
    }

    public Node getNodeAt(int index) {
        return nodes.get(index);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public boolean isAssignmentNodePresent() {
        for (Node node : nodes) {
            if (node.getToken().getTokenType().equals(ASSIGNMENT_OPERATOR)) {
                return true;
            }
        }

        return false;
    }
}
