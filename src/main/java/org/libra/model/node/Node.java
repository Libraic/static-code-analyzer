package org.libra.model.node;


import lombok.Getter;
import org.libra.model.Color;
import org.libra.model.token.Token;

import static org.libra.model.Color.PURPLE;
import static org.libra.model.Color.RESET;
import static org.libra.utils.Constants.NEW_LINE_DELIMITER;

@Getter
public abstract class Node {
    protected final Token token;

    public Node(Token token) {
        this.token = token;
    }

    /**
     * This method takes a node and adds it to an existing node.
     * The adding is done in concordance to the type of concrete nodes.
     * <br><br>
     * @param node the node to be added.
     */
    public abstract void addNode(Node node);

    /**
     * The method outputs to the console the JSON format of a specific node.
     * <br><br>
     * @param spacing the spacing printed to the console relative to the left hand side of the console.
     */
    public abstract void stringify(int spacing);

    protected String getSpacing(int spacing) {
        StringBuilder spacingSb = new StringBuilder();
        for (int i = 0; i < spacing; ++i) {
            spacingSb.append("\t");
        }

        return spacingSb.toString();
    }

    protected void printTokenWithSpacing(Color color, String spacing) {
        for (String tokenString : token.toString().split(NEW_LINE_DELIMITER)) {
            System.out.println(color.getValue() + spacing + tokenString + RESET.getValue());
        }
    }

    protected void printStringLiteralWithColor(Color color, String literal) {
        System.out.println(color.getValue() + literal + RESET.getValue());
    }
}
