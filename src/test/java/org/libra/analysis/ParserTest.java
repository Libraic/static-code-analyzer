package org.libra.analysis;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.libra.model.node.Node;
import org.libra.model.token.Token;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.libra.utils.TestObjects.TEST_FILE_1_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_2_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_3_TOKENS;
import static org.libra.utils.TestObjects.getTestFile1AbstractSyntaxTree;
import static org.libra.utils.TestObjects.getTestFile2AbstractSyntaxTree;
import static org.libra.utils.TestObjects.getTestFile3AbstractSyntaxTree;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    @InjectMocks
    Parser parser;

    @ParameterizedTest
    @MethodSource("getInputListOfTokensAndExpectedAbstractSyntaxTree")
    void whenParsingTheListOfTokens_shouldReturnTheExpectedAbstractSyntaxTree(
        List<Token> tokens,
        Node expected
    ) {
        Node actual = parser.parse(tokens);
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(Integer.class)
            .isEqualTo(expected);
    }

    static Stream<Arguments> getInputListOfTokensAndExpectedAbstractSyntaxTree() {
        return Stream.of(
            Arguments.of(TEST_FILE_1_TOKENS, getTestFile1AbstractSyntaxTree()),
            Arguments.of(TEST_FILE_2_TOKENS, getTestFile2AbstractSyntaxTree()),
            Arguments.of(TEST_FILE_3_TOKENS, getTestFile3AbstractSyntaxTree())
        );
    }
}