package org.libra.model.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.libra.model.token.Token;

import java.util.ArrayList;
import java.util.List;

import static org.libra.model.Color.GREEN;
import static org.libra.utils.Constants.CLASS_INDICATOR;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.PROGRAM_BODY_KEYWORD;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProgramNode extends Node {

    private final List<Node> subprograms;
    private final List<Node> classes;

    public ProgramNode(Token token) {
        super(token);
        subprograms = new ArrayList<>();
        classes = new ArrayList<>();
    }

    @Override
    public void addNode(Node node) {
        classes.add(node);
    }

    @Override
    public void stringify(int spacing) {
        printStringLiteralWithColor(GREEN, token.toString());
        printStringLiteralWithColor(GREEN, PROGRAM_BODY_KEYWORD + JSON_OBJECT_START);
        for (int i = 0; i < classes.size(); ++i) {
            Node classNode = classes.get(i);
            printStringLiteralWithColor(GREEN, CLASS_INDICATOR + (i + 1) + ": {");
            classNode.stringify(spacing + 2);
            printStringLiteralWithColor(GREEN, "\t}");
        }

        printStringLiteralWithColor(GREEN, JSON_OBJECT_END);
    }

    public Node getLastClass() {
        return classes.get(classes.size() - 1);
    }

}
