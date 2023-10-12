package org.libra;

import lombok.AllArgsConstructor;
import org.libra.analysis.Lexer;
import org.libra.exception.ExceptionGenerator;
import org.libra.model.Token;
import org.libra.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.libra.exception.ExceptionType.FILE_PROCESSING_EXCEPTION;
import static org.libra.utils.Constants.*;

/**
 * This class acts as parser for input files, which contains the code be analyzed.
 */

@AllArgsConstructor
public class FileParser {

    private final Lexer lexer;

    /**
     * The method take the input file and reads it line by line.
     * <br><br>
     * @param fileName the input file name.
     */
    public List<Token> parseFile(String fileName) {
        List<Token> tokens = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        try (
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader)
        ) {
            int asciiCode;
            StringBuilder keyword = new StringBuilder();
            while ((asciiCode = reader.read()) != -1) {
                char character = (char)asciiCode;
                if (isNewLineOrCarriageReturn(character)) {
                    continue;
                }

                if (isKeywordComplete(character, keyword)) {
                    keywords.add(keyword.toString());
                    keyword.delete(0, keyword.length());
                }

                if (character == SEMICOLON) {
                    tokens.addAll(lexer.tokenize(keywords));
                    keywords = new ArrayList<>();
                    continue;
                }

                appendCharacterToKeyword(character, keyword);
            }
        } catch (Exception e) {
            throw ExceptionGenerator.of(FILE_PROCESSING_EXCEPTION);
        }

        return tokens;
    }

    private boolean isKeywordComplete(char character, StringBuilder keyword) {
        if (keyword.length() == 0) {
            return false;
        }

        char lastCharacterFromKeyword = keyword.charAt(keyword.length() - 1);
        return isSpaceOrSemicolon(character) ||
            isAssignmentOperator(character) ||
            isAssignmentOperator(lastCharacterFromKeyword) ||
            isArithmeticOperator(character) ||
            isArithmeticOperator(lastCharacterFromKeyword) ||
            isParenthesis(character) ||
            isParenthesis(lastCharacterFromKeyword);
    }

    private boolean isArithmeticOperator(char character) {
        return character == Constants.PLUS_OPERATOR ||
            character == Constants.MINUS_OPERATOR ||
            character == Constants.MULTIPLICATION_OPERATOR ||
            character == Constants.DIVISION_OPERATOR;
    }

    private boolean isAssignmentOperator(char character) {
        return character == ASSIGNMENT_OPERATOR;
    }

    private boolean isSpaceOrSemicolon(char character) {
        return character == SPACE || character == SEMICOLON;
    }

    private boolean isParenthesis(char character) {
        return character == OPEN_PARENTHESIS || character == CLOSED_PARENTHESIS;
    }

    private boolean isNewLineOrCarriageReturn(char character) {
        return character == CARRIAGE_RETURN || character == NEW_LINE;
    }

    private void appendCharacterToKeyword(char character, StringBuilder keyword) {
        if (character != SPACE && character != SEMICOLON) {
            keyword.append(character);
        }
    }
}
