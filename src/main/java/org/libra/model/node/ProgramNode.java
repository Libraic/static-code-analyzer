package org.libra.model.node;

import org.libra.model.token.Token;

import java.util.ArrayList;
import java.util.List;

import static org.libra.model.Color.GREEN;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.PROGRAM_BODY_KEYWORD;

public class ProgramNode extends Node {

    private final List<Node> nodes;

    public ProgramNode(Token token) {
        super(token);
        nodes = new ArrayList<>();
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public void stringify(int spacing) {
        printStringLiteralWithColor(GREEN, token.toString());
        printStringLiteralWithColor(GREEN, PROGRAM_BODY_KEYWORD + JSON_OBJECT_START);
        for (Node node : nodes) {
            node.stringify(spacing + 1);
        }
        printStringLiteralWithColor(GREEN, JSON_OBJECT_END);
    }
}
