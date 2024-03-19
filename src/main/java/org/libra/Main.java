package org.libra;

import org.libra.analysis.AstProducer;
import org.libra.exception.StaticCodeAnalyzerException;
import org.libra.model.node.Node;


public class Main {
    public static void main(String[] args) {
        String fileName = "input.txt";
        AstProducer astProducer = new AstProducer();
        try {
            Node abstractSyntaxTree = astProducer.produceAst(fileName);
            abstractSyntaxTree.stringify(0);
        } catch (StaticCodeAnalyzerException e) {
            e.printDetails();
        }
    }
}