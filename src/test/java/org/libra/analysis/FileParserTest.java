package org.libra.analysis;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.libra.utils.TestConstants.TEST_FILE_1;
import static org.libra.utils.TestConstants.TEST_FILE_2;
import static org.libra.utils.TestConstants.TEST_FILE_3;
import static org.libra.utils.TestObjects.getTestFile1Keywords;
import static org.libra.utils.TestObjects.getTestFile2Keywords;
import static org.libra.utils.TestObjects.getTestFile3Keywords;

@ExtendWith(MockitoExtension.class)
class FileParserTest {

    @InjectMocks
    FileParser fileParser;

    @ParameterizedTest
    @MethodSource("getInputFileAndExpectedOutput")
    public void shouldGetExpectedKeywordsFromEachRow_whenParsingTheInputFile(
        String filePath, Map<Integer,
        List<String>> expected
    ) {
        Map<Integer, List<String>> actual = fileParser.parseFile(filePath);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> getInputFileAndExpectedOutput() {
        return Stream.of(
            Arguments.of(TEST_FILE_1, getTestFile1Keywords()),
            Arguments.of(TEST_FILE_2, getTestFile2Keywords()),
            Arguments.of(TEST_FILE_3, getTestFile3Keywords())
        );
    }
}