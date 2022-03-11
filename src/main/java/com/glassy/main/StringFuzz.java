package com.glassy.main;

import java.util.*;

public class StringFuzz {
    private static final List<Character> upperList = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ':', '.', '/');
    private static final List<Character> lowerList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ':', '.', '/');
    private static final List<Integer> numList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static void main(String[] args) {
//        getUpperFuzzResult();
//        getLowerFuzzResult();
        getParseIntFuzzResult();
    }

    public static void getUpperFuzzResult() {

        for (char c = 0; c < 65535; c++) {
            for (char upperChar : upperList) {
                if (upperChar != c && Character.toUpperCase(c) == upperChar && !lowerList.contains(c)) {
                    System.out.println(c + ":->charNum: " + (int) c + "|char: " + upperChar);
                }
            }
        }
    }

    public static void getLowerFuzzResult() {
        for (char c = 0; c < 65535; c++) {
            for (char lowerChar : lowerList) {
                if (lowerChar != c && Character.toLowerCase(c) == lowerChar && !upperList.contains(c)) {
                    System.out.println(c + ":->charNum: " + (int) c + "|char: " + lowerChar);
                }
            }
        }
    }

    public static void getParseIntFuzzResult() {
        for (char c = 0; c < 65535; c++) {
            String str = Character.toString(c);
            try {
                for (int num : numList) {
                    if (Integer.parseInt(str) == num && !String.valueOf(num).equals(str)) {
                        System.out.println(num + ":->charNum: " + (int) c + "|char: " + str);
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    public static Map<Integer, List<String>> getParseIntFuzzMap() {
        HashMap<Integer, List<String>> result = new HashMap<>();
        for (char c = 0; c < 65535; c++) {
            String str = Character.toString(c);
            try {
                for (int num : numList) {
                    List<String> set = result.get(num) == null ? new ArrayList<>() : result.get(num);
                    if (Integer.parseInt(str) == num && !String.valueOf(num).equals(str)) {
                        set.add(str);
                    }
                    result.put(num, set);
                }
            } catch (Exception ignored) {

            }
        }
        return result;
    }
}
