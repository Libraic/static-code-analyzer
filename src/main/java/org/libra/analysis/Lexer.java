package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.model.TokenPremises;
import org.libra.model.token.Token;
import org.libra.model.token.TokenFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.libra.model.token.TokenType.COMMA_SEPARATOR;
import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.utils.Constants.*;

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
     * @param keywordsFromEachRow the list of keywords from each row.
     * @return the list of tokens.
     */
    public List<Token> tokenize(Map<Integer, List<String>> keywordsFromEachRow) {
        List<Token> tokens = new ArrayList<>();
        for (List<String> keywords : keywordsFromEachRow.values()) {
            tokens.addAll(tokenize(keywords));
        }

        return tokens;
    }


    private List<Token> tokenize(List<String> keywords) {
        List<Token> tokens = new ArrayList<>();
        TokenPremises tokenPremises = TokenPremises.builder()
            .isMethodDeclaration(keywords.contains(OPEN_CURLY_BRACE_LITERAL) && !keywords.contains(SEMICOLON_LITERAL))
            .build();
        Token programToken = tokenFactory.createProgramToken();
        if (programToken != null) {
            tokens.add(programToken);
        }

        for (int i = 0; i < keywords.size(); ++i) {
            String keyword = keywords.get(i);
            checkIfAssignmentIsNextKeywordAndAdjustTokenPremises(tokenPremises, keywords, i);
            Token token = tokenFactory.produceToken(keyword, i, tokenPremises);
            adjustMethodRelatedTokenPremises(token, tokenPremises);
            tokens.add(token);
        }

        return tokens;
    }

    private void checkIfAssignmentIsNextKeywordAndAdjustTokenPremises(
        TokenPremises tokenPremises,
        List<String> keywords,
        int currentIndex
    ) {
        tokenPremises.setAssignmentNextKeyword(currentIndex + 1 < keywords.size()
            && keywords.get(currentIndex + 1).equals(ASSIGNMENT_LITERAL)
        );
    }

    private void adjustMethodRelatedTokenPremises(Token token, TokenPremises tokenPremises) {
        if (token.getTokenType().equals(METHOD_DECLARATION)) {
            tokenPremises.setInsideMethodSignature(true);
            tokenPremises.setMethodSignatureCurrentPosition(ZEROTH_INDEX);
        } else if (token.getTokenType().equals(COMMA_SEPARATOR)) {
            tokenPremises.setMethodSignatureCurrentPosition(FIRST_INDEX);
        }

        tokenPremises.incrementMethodSignatureCurrentPosition();
    }
}
