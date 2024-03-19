package org.libra.model.token;

import org.libra.exception.ExceptionGenerator;
import org.libra.model.TokenPremises;

import static org.libra.exception.ExceptionType.UNEXPECTED_TOKEN_EXCEPTION;
import static org.libra.model.token.TokenType.*;
import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.utils.Constants.*;
import static org.libra.utils.TokenPattern.*;

public class TokenFactory {

    private boolean wasProgramTokenCreated;

    /**
     * We take the keyword we have received as parameter and walk it through some conditions to figure out what kind
     * of token it represents, taking into consideration its position in the code relative to other keywords as well.
     *
     * @param keyword the current keyword that is processed.
     * @param keywordIndex the index of the current keyword.
     * @param tokenPremises a list of premises that is essential to avoid ambiguity for certain keywords that may
     *                      represent more than 1 token. Hence, we adjust the premises on every token we process to
     *                      know at what point of the code we are - method declaration, method signature, variable
     *                      declaration, reassignment instruction etc.
     * @return the corresponding Token to the processed keyword.
     */
    public Token produceToken(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
        if (isAccessModifier(keyword)) {
            return new StandaloneToken(ACCESS_MODIFIER, keyword);
        } else if (isStaticAccess(keyword)) {
            return new StandaloneToken(STATIC_ACCESS, keyword);
        } else if (isFinalKeyword(keyword)) {
            return new StandaloneToken(STATE, keyword);
        } else if (isDataType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(DATA_TYPE, keyword);
        } else if (isReturnType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(RETURN_TYPE, keyword);
        } else if (isEntityName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(VARIABLE_NAME, keyword);
        } else if (isMethodName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(METHOD_DECLARATION, keyword);
        } else if (isAssignmentOperator(keyword, tokenPremises)) {
            return new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keyword);
        } else if (isParentheses(keyword)) {
            return new SpecialSymbolToken(PARENTHESES, keyword);
        } else if (isNumber(keyword)) {
            return new StandaloneToken(NUMERIC, keyword);
        } else if (isArithmeticOperation(keyword)) {
            return new BinaryExpressionToken(ARITHMETIC_OPERATOR, keyword);
        } else if (isString(keyword)) {
            return new StandaloneToken(STRING, keyword);
        } else if (isSeparator(keyword)) {
            return new SpecialSymbolToken(COMMA_SEPARATOR, keyword);
        } else if (isInstructionOrSubprogram(keyword)) {
            return new InstructionToken(
                keyword.equals(SEMICOLON_LITERAL) ? INSTRUCTION : METHOD,
                keyword
            );
        } else {
            throw ExceptionGenerator.of(
                UNEXPECTED_TOKEN_EXCEPTION,
                String.format("The processed keyword [{%s}] is not recognized by the system.", keyword)
            );
        }
    }

    /**
     * This method is responsible for creating the Program Token, which represents the entry point of the Java program
     * that is currently being analyzed. This particular Token gets created only once, acting like a singleton Token.
     *
     * @return the PROGRAM Token.
     */
    public Token createProgramToken() {
        if (!wasProgramTokenCreated) {
            wasProgramTokenCreated = true;
            return new ProgramToken(PROGRAM, PROGRAM_TOKEN_VALUE);
        }

        return null;
    }

    private boolean isParentheses(String keyword) {
        return keyword.matches(PARENTHESES_PATTERN.getRegex());
    }

    private boolean isNumber(String keyword) {
        return keyword.matches(NUMBER_PATTERN.getRegex());
    }

    private boolean isArithmeticOperation(String keyword) {
        return keyword.matches(ARITHMETIC_OPERATOR_PATTERN.getRegex());
    }

    private boolean isEntityName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return isVariableName(keywordIndex, tokenPremises) && keyword.matches(ENTITY_NAME_PATTERN.getRegex());
    }

    private boolean isMethodName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (keywordIndex == FIRST_INDEX || keywordIndex == SECOND_INDEX || keywordIndex == THIRD_INDEX || keywordIndex == FOURTH_INDEX)
            && keyword.matches(ENTITY_NAME_PATTERN.getRegex())
            && tokenPremises.isMethodDeclaration()
            && !tokenPremises.isInsideMethodSignature()
            && tokenPremises.isNextKeywordOpenParenthesis();
    }

    private boolean isDataType(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (isKeywordAtTheStartOfInstruction(keywordIndex, tokenPremises) || isKeywordInTheMethodSignature(tokenPremises))
            && keyword.matches(ENTITY_NAME_PATTERN.getRegex());
    }

    private boolean isAccessModifier(String keyword) {
        return keyword.matches(ACCESS_MODIFIER_PATTERN.getRegex());
    }

    private boolean isString(String keyword) {
        return keyword.matches(STRING_PATTERN.getRegex());
    }

    private boolean isInstructionOrSubprogram(String keyword) {
        return keyword.matches(INSTRUCTION_SUBPROGRAM_PATTERN.getRegex());
    }

    private boolean isStaticAccess(String keyword) {
        return keyword.matches(STATIC_ACCESS_PATTERN.getRegex());
    }

    private boolean isFinalKeyword(String keyword) {
        return keyword.matches(FINAL_PATTERN.getRegex());
    }

    private boolean isAssignmentOperator(String keyword, TokenPremises tokenPremises) {
        tokenPremises.setPostAssignmentKeyword(true);
        return keyword.matches(ASSIGNMENT_OPERATOR_PATTERN.getRegex());
    }

    private boolean isReturnType(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
         return (keywordIndex == ZEROTH_INDEX || keywordIndex == FIRST_INDEX || keywordIndex == SECOND_INDEX || keywordIndex == THIRD_INDEX)
             && keyword.matches(ENTITY_NAME_PATTERN.getRegex())
             && tokenPremises.isMethodDeclaration()
             && !tokenPremises.isInsideMethodSignature()
             && !tokenPremises.isNextKeywordOpenParenthesis();
    }

    private boolean isSeparator(String keyword) {
        return keyword.matches(SEPARATOR_PATTERN.getRegex());
    }

    private boolean isVariableName(int keywordIndex, TokenPremises tokenPremises) {
        return isVariableNameBeforeAssignment(keywordIndex, tokenPremises)
            || tokenPremises.isPostAssignmentKeyword()
            || isVariableNameInsideMethodSignature(tokenPremises);
    }

    private boolean isKeywordAtTheStartOfInstruction(
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
        return keywordIndex == ZEROTH_INDEX
            && !tokenPremises.isMethodDeclaration()
            && !tokenPremises.isAssignmentNextKeyword();
    }

    private boolean isKeywordInTheMethodSignature(TokenPremises tokenPremises) {
        return tokenPremises.isMethodDeclaration()
            && tokenPremises.isInsideMethodSignature()
            && tokenPremises.getMethodSignatureCurrentPosition() % PARITY_CHECKER_NUMBER == EVEN_PARITY_INDICATOR;
    }

    /**
     * If we are before assignment, then we either have [DATA_TYPE] [VARIABLE_NAME],
     * in which case, the variable name represent the second element in the declaration,
     * either we have [VARIABLE_NAME], representing a reassignment, in which case it is the first element from the
     * declaration.
     *
     * @param keywordIndex representing the index of the keyword in the declaration, 0-indexed.
     * @param tokenPremises the object containing the premises for different instances.
     * @return the boolean depicting if the keyword is a variable or not.
     */
    private boolean isVariableNameBeforeAssignment(int keywordIndex, TokenPremises tokenPremises) {
        return (keywordIndex == ZEROTH_INDEX || keywordIndex == FIRST_INDEX)
            && !tokenPremises.isMethodDeclaration()
            && tokenPremises.isAssignmentNextKeyword();
    }

    /**
     * If we have reached the method signature, the parity of the index will tell us if it is a variable name
     * or data type. Starting from the parentheses, depicting the starting point of a method signature, it will
     * have the index 1. Then the index will be incremented, leading to an even index, which corresponds to DATA_TYPE
     * keyword. Then, the position will be incremented again, leading to an odd index, depicting a VARIABLE_NAME.
     * Once we find a comma, we set the index to 1 again. And the process continues till we find a closed parentheses.
     *
     * @param tokenPremises the object containing the premises for different instances.
     * @return the boolean depicting if the keyword is a variable or not.
     */
    private boolean isVariableNameInsideMethodSignature(TokenPremises tokenPremises) {
        return tokenPremises.isInsideMethodSignature()
            && tokenPremises.getMethodSignatureCurrentPosition() % 2 != 0;
    }
}
