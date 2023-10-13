package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.model.token.Token;
import org.libra.model.token.TokenFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The class used for tokenizing each input sent by the Parser class.
 */
@AllArgsConstructor
public class Lexer {

    private final TokenFactory tokenFactory;

    public Lexer() {
        tokenFactory = new TokenFactory();
    }

    /**
     * The method takes a list of keywords and converts each keyword to a specific token.
     * <br><br>
     * @param keywords the list of keywords.
     * @return the list of tokens.
     */
    public List<Token> tokenize(List<String> keywords) {
        List<Token> tokens = new ArrayList<>();
        tokens.add(tokenFactory.createProgramToken());
        int startingIndex = tokenFactory.handleAssignmentInstructionsIfPresent(keywords, tokens);
        for (int i = startingIndex; i < keywords.size(); ++i) {
            String keyword = keywords.get(i);
            Token token = tokenFactory.produceToken(keyword);
            tokens.add(token);
        }

        return tokens;
    }
}
