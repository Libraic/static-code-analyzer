package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.model.TokenPremises;
import org.libra.model.token.Token;
import org.libra.model.token.TokenFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.libra.model.token.TokenType;

import static org.libra.model.token.TokenType.COMMA_SEPARATOR;
import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.model.token.TokenType.UNDEFINED;
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
            .isClassDeclaration(keywords.contains(CLASS_LITERAL))
            .isMethodDeclaration(!keywords.contains(CLASS_LITERAL) &&
                keywords.contains(OPEN_CURLY_BRACE_LITERAL) &&
                !keywords.contains(SEMICOLON_LITERAL)
            ).build();
        Token programToken = tokenFactory.createProgramToken();
        if (programToken != null) {
            tokens.add(programToken);
        }

        for (int i = 0; i < keywords.size(); ++i) {
            String keyword = keywords.get(i);
            adjustTokenPremises(tokenPremises, keywords, i, tokens);
            Token token = tokenFactory.produceToken(keyword, i, tokenPremises);
            if (token != null) {
                tokens.add(token);
            }
        }

        return tokens;
    }

    private void adjustTokenPremises(
        TokenPremises tokenPremises,
        List<String> keywords,
        int currentIndex,
        List<Token> tokens
    ) {
        String nextKeyword = currentIndex + 1 < keywords.size() ? keywords.get(currentIndex + 1) : EMPTY_STRING;
        TokenType tokenType = tokens.isEmpty() ? UNDEFINED : tokens.get(tokens.size() - 1).getTokenType();

        // Check if next keyword is open parenthesis. This will be used to establish if the current keyword is method declaration
        tokenPremises.setNextKeywordOpenParenthesis(nextKeyword.equals(OPEN_PARENTHESES_LITERAL));

        // Check if assignment is next keyword and adjust token premises
        tokenPremises.setAssignmentNextKeyword(nextKeyword.equals(ASSIGNMENT_LITERAL));

        // If the previous token is of METHOD_DECLARATION type, we have to mark that we are about to analyze the signature of the method.
        // Also, we start indexing each keyword that represents the method signature to determine which keyword is the data type and which is
        // the name of the variable (since we have DATA_TYPE VARIABLE_NAME, DATA_TYPE VARIABLE_NAME...).
        // If the previous token is a comma, we have to reset the index that points to the current keyword from the method
        // declaration. Resetting it means to initialize it as being 1.
        if (tokenType.equals(METHOD_DECLARATION)) {
            tokenPremises.setInsideMethodSignature(true);
            tokenPremises.setMethodSignatureCurrentPosition(ZEROTH_INDEX);
        } else if (tokenType.equals(COMMA_SEPARATOR)) {
            tokenPremises.setMethodSignatureCurrentPosition(FIRST_INDEX);
        }

        tokenPremises.incrementMethodSignatureCurrentPosition();
    }
}
