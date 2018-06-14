package com.ambrosoft.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jacek R. Ambroziak
 */
final class FunctionalCounting {
    // single regex to take care of separation by whitespace and not including leading/trailing punctuation
    // punctuation can start a token at beginning of line or end a token at end of line
    private final static Pattern WS_PUNCT_SPLITTER = Pattern.compile("^\\p{Punct}+|\\p{Punct}*\\s+\\p{Punct}*|\\p{Punct}+$");

    static Stream<String> tokensInPath(final Path path) throws IOException {
        return Files.lines(path)
                .flatMap(WS_PUNCT_SPLITTER::splitAsStream)
                .filter(token -> !token.isEmpty());
    }

    static Map<String, Long> countTokensInPath(final Path path) {
        try (final Stream<String> tokens = tokensInPath(path)) {
            return tokens
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    // https://stackoverflow.com/questions/23038673/merging-two-mapstring-integer-with-java-8-stream-api

    static Map<String, Long> countTokensInPathStream(final Stream<Path> pathStream) {
        return pathStream
                .map(FunctionalCounting::countTokensInPath)
                .map(Map::entrySet)          // converts each map into an entry set
                .flatMap(Collection::stream) // converts each set into an entry stream, then
                .collect(
                        Collectors.toMap(               // collects into a map
                                Map.Entry::getKey,      // where each entry is based
                                Map.Entry::getValue,    // on the entries in the stream
                                Long::sum               // merge function
                        ));
    }
}
