package org.libra.analysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.libra.model.token.Token;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.libra.utils.TestObjects.TEST_FILE_1_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_2_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_3_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_4_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_5_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_6_TOKENS;
import static org.libra.utils.TestObjects.TEST_FILE_7_TOKENS;
import static org.libra.utils.TestObjects.getTestFile1Keywords;
import static org.libra.utils.TestObjects.getTestFile2Keywords;
import static org.libra.utils.TestObjects.getTestFile3Keywords;
import static org.libra.utils.TestObjects.getTestFile4Keywords;
import static org.libra.utils.TestObjects.getTestFile5Keywords;
import static org.libra.utils.TestObjects.getTestFile6Keywords;
import static org.libra.utils.TestObjects.getTestFile7Keywords;

@ExtendWith(MockitoExtension.class)
class LexerTest {

    Lexer lexer;

    @BeforeEach
    void setUp() {
        lexer = new Lexer();
    }

    @ParameterizedTest
    @MethodSource("getInputKeywordsFromEachRowAndExpectedTokens")
    void shouldGetTheExpectedTokens_whenProcessingTheKeywordsFromEachRow(
        Map<Integer, List<String>> keywordsFromEachRow,
        List<Token> expected
    ) {
        List<Token> actual = lexer.tokenize(keywordsFromEachRow);
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    static Stream<Arguments> getInputKeywordsFromEachRowAndExpectedTokens() {
        return Stream.of(
            Arguments.of(getTestFile1Keywords(), TEST_FILE_1_TOKENS),
            Arguments.of(getTestFile2Keywords(), TEST_FILE_2_TOKENS),
            Arguments.of(getTestFile3Keywords(), TEST_FILE_3_TOKENS),
            Arguments.of(getTestFile4Keywords(), TEST_FILE_4_TOKENS),
            Arguments.of(getTestFile5Keywords(), TEST_FILE_5_TOKENS),
            Arguments.of(getTestFile6Keywords(), TEST_FILE_6_TOKENS),
            Arguments.of(getTestFile7Keywords(), TEST_FILE_7_TOKENS)
        );
    }
}