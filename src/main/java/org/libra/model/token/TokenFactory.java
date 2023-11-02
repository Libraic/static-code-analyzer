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

    public Token produceToken(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
        if (isAccessModifier(keyword)) {
            return new StandaloneToken(ACCESS_MODIFIER, keyword);
        } else if (isStaticAccess(keyword)) {
            return new StandaloneToken(STATIC_ACCESS, keyword);
        } else if (isDataType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(DATA_TYPE, keyword);
        } else if (isReturnType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(RETURN_TYPE, keyword);
        } else if (isEntityName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(VARIABLE_NAME, keyword);
        } else if (isMethodName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(METHOD_DECLARATION, keyword);
        } else if (isAssignmentOperator(keyword)) {
            return new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keyword);
        } else if (isParenthesis(keyword)) {
            return new SpecialSymbolToken(PARENTHESIS, keyword);
        } else if (isNumber(keyword)) {
            return new StandaloneToken(NUMERIC, keyword);
        } else if (isArithmeticOperation(keyword)) {
            return new BinaryExpressionToken(ARITHMETIC_OPERATOR, keyword);
        } else if (isString(keyword)) {
            return new StandaloneToken(STRING, keyword);
        } else if (isSeparator(keyword)) {
            return new SpecialSymbolToken(COMMA_SEPARATOR, keyword);
        }
        else if (isInstructionOrSubprogram(keyword)) {
            return new InstructionToken(
                keyword.equals(SEMICOLON_LITERAL) ? INSTRUCTION : METHOD,
                keyword
            );
        }
        else {
            throw ExceptionGenerator.of(UNEXPECTED_TOKEN_EXCEPTION);
        }
    }

    public Token createProgramToken() {
        if (!wasProgramTokenCreated) {
            wasProgramTokenCreated = true;
            return new ProgramToken(PROGRAM, PROGRAM_TOKEN_VALUE);
        }

        return null;
    }

    private boolean isParenthesis(String keyword) {
        return keyword.matches(PARENTHESIS_PATTERN.getRegex());
    }

    private boolean isNumber(String keyword) {
        return keyword.matches(NUMBER_PATTERN.getRegex());
    }

    private boolean isArithmeticOperation(String keyword) {
        return keyword.matches(ARITHMETIC_OPERATOR_PATTERN.getRegex());
    }

    private boolean isEntityName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (((keywordIndex == FIRST_ELEMENT || keywordIndex == SECOND_ELEMENT)
                    && !tokenPremises.isMethodDeclaration())
                || (tokenPremises.isInsideMethodSignature()
                    && tokenPremises.getMethodSignatureCurrentPosition() % 2 != 0))
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isMethodName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (keywordIndex == SECOND_ELEMENT || keywordIndex == THIRD_ELEMENT || keywordIndex == FOURTH_ELEMENT)
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex())
            && tokenPremises.isMethodDeclaration()
            && !tokenPremises.isInsideMethodSignature();
    }

    private boolean isDataType(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return ((keywordIndex == FIRST_ELEMENT && !tokenPremises.isMethodDeclaration())
                || (tokenPremises.isMethodDeclaration()
                    && tokenPremises.isInsideMethodSignature()
                    && tokenPremises.getMethodSignatureCurrentPosition() % 2 == 0))
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
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

    private boolean isAssignmentOperator(String keyword) {
        return keyword.matches(ASSIGNMENT_OPERATOR_PATTERN.getRegex());
    }

    private boolean isReturnType(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
         return (keywordIndex == FIRST_ELEMENT || keywordIndex == SECOND_ELEMENT || keywordIndex == THIRD_ELEMENT)
             && keyword.matches(VARIABLE_NAME_PATTERN.getRegex())
             && tokenPremises.isMethodDeclaration()
             && !tokenPremises.isInsideMethodSignature();
    }

    private boolean isSeparator(String keyword) {
        return keyword.matches(SEPARATOR_PATTERN.getRegex());
    }
}
