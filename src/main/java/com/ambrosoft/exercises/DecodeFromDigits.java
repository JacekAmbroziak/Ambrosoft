package com.ambrosoft.exercises;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created on 1/10/18
 * <p>
 * Amazon Qiegang
 */
public class DecodeFromDigits {

    static Map<Character, String> initMapping() {
        final HashMap<Character, String> map = new HashMap<>();
        map.put(' ', "0");
        int letterCode = 0;
        for (char i = 'A'; i <= 'Z'; i++) {
            map.put(i, Integer.toString(++letterCode));
        }
        return map;
    }

    static Map<String, Character> reverseMapping(final Map<Character, String> mapping) {
        final Map<String, Character> reverse = new HashMap<>(mapping.size(), 1.0f);
        for (Map.Entry<Character, String> entry : mapping.entrySet()) {
            reverse.put(entry.getValue(), entry.getKey());
        }
        return reverse;
    }

    static String encode(final String input, final Map<Character, String> mapping) {
        final StringBuilder sb = new StringBuilder();
        final int len = input.length();
        for (int i = 0; i < len; i++) {
            final String code = mapping.get(input.charAt(i));
            if (code != null) {
                sb.append(code);
            } else {
                throw new IllegalArgumentException("unmappable char in input: " + input.charAt(i));
            }
        }
        return sb.toString();
    }

    static class DecodeState {
        private final String encoded;
        private final int index;
        final String decoded;

        DecodeState(String encoded, int index, String decoded) {
            this.encoded = encoded;
            this.index = index;
            this.decoded = decoded;
        }

        int remaining() {
            return encoded.length() - index;
        }

        String nGram(final int length) {
            return index + length <= encoded.length() ? encoded.substring(index, index + length) : null;
        }

        DecodeState advance(int step, char decodedChar) {
            return new DecodeState(encoded, index + step, decoded + decodedChar);
        }

        @Override
        public String toString() {
            return "\"" + decoded + '"';
        }
    }

    static void tryDecodeStep(DecodeState state, int step, Map<String, Character> reverseMapping, Deque<DecodeState> stack) {
        final String nGram = state.nGram(step);
        if (nGram != null) {
            final Character decodedChar = reverseMapping.get(nGram);
            if (decodedChar != null) {
                stack.push(state.advance(step, decodedChar));
            }
        }
    }

    static void decode(String encoded, Map<Character, String> mapping, Set<String> dictionary) {
        final Map<String, Character> reverseMapping = reverseMapping(mapping);
        final Deque<DecodeState> stack = new LinkedList<>();
        stack.push(new DecodeState(encoded, 0, ""));

        while (!stack.isEmpty()) {
            final DecodeState state = stack.pop();
            switch (state.remaining()) {
                case 0:
                    System.out.println(state);
                    System.out.println(encode(state.decoded, mapping));
                    break;

                case 1:
                    tryDecodeStep(state, 1, reverseMapping, stack);
                    break;

                default:
                    tryDecodeStep(state, 2, reverseMapping, stack);
                    tryDecodeStep(state, 1, reverseMapping, stack);
                    break;
            }
        }
    }

    static void decode2(String encoded, Map<Character, String> mapping, Set<String> dictionary) {
        final Map<String, Character> reverseMapping = reverseMapping(mapping);
        doDecode(encoded, 0, "", reverseMapping);
    }

    static void doDecode(String encoded, int index, String decoded, Map<String, Character> reverseMapping) {
        final int length = encoded.length();
        if (index == length) {
            System.out.println(decoded);
        } else {
            Character c1 = reverseMapping.get(encoded.substring(index, index + 1));
            if (c1 != null) {
                doDecode(encoded, index + 1, decoded + c1, reverseMapping);
            }
            if (length - index > 1) {
                Character c2 = reverseMapping.get(encoded.substring(index, index + 2));
                if (c2 != null) {
                    doDecode(encoded, index + 2, decoded + c2, reverseMapping);
                }
            }
        }
    }

    static void test(String input, Set<String> dictionary) {
        final Map<Character, String> mapping = initMapping();
        final String encoded = encode(input, mapping);
        System.out.println(input + '\t' + encoded);
        decode2(encoded, mapping, dictionary);
    }

    static void readDict(Set<String> words) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("/usr/share/dict/words")));
        String line;
        while ((line = in.readLine()) != null) {
            words.add(line.trim().toUpperCase());
        }
        in.close();
    }

    public static void main(String[] args) throws IOException {
        final HashSet<String> dictionary = new HashSet<>();
        readDict(dictionary);
        test("A TEST", dictionary);
//        test("A TEST FROM AMAZON", dictionary);
    }
}
