package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.model.TokenPremises;
import org.libra.model.token.Token;
import org.libra.model.token.TokenFactory;

import java.util.ArrayList;
import java.util.List;

import static org.libra.model.token.TokenType.METHOD_NAME;
import static org.libra.utils.Constants.CURLY_BRACE_LITERAL;
import static org.libra.utils.Constants.SEMICOLON_LITERAL;

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
        TokenPremises tokenPremises = TokenPremises.builder()
            .isMethodDeclaration(keywords.contains(CURLY_BRACE_LITERAL) && !keywords.contains(SEMICOLON_LITERAL))
            .build();
        Token programToken = tokenFactory.createProgramToken();
        if (programToken != null) {
            tokens.add(programToken);
        }

        for (int i = 0; i < keywords.size(); ++i) {
            String keyword = keywords.get(i);
            Token token = tokenFactory.produceToken(keyword, i, tokenPremises);
            if (token.getTokenType().equals(METHOD_NAME)) {
                tokenPremises.setInsideMethodSignature(true);
                tokenPremises.setMethodSignatureCurrentPosition(0);
            }

            tokens.add(token);
            tokenPremises.incrementMethodSignatureCurrentPosition();
        }

        return tokens;
    }
}
