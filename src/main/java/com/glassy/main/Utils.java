package com.glassy.main;

import java.util.Arrays;

public class Utils {
    public static void doFuzz(String template, int index, HandleType type, CheckFunc func) {
        switch (type) {
            case INSERT:
                insertFuzz(template, index, func);
                break;
            case REPLACE:
                replaceFuzz(template, index, func);
        }
    }

    private static void insertFuzz(String template, int index, CheckFunc func) {
        for (char fuzzC = 0; fuzzC < 65535; fuzzC++) {
            StringBuilder sb = new StringBuilder(template);
            sb.insert(index, fuzzC);
            func.check(template, sb.toString(), fuzzC);
        }
    }

    private static void replaceFuzz(String template, int index, CheckFunc func) {
        for (char fuzzC = 0; fuzzC < 65535; fuzzC++) {
            char[] charArr = template.toCharArray();
            charArr[index] = fuzzC;
            func.check(template, Arrays.toString(charArr), fuzzC);
        }
    }
}

interface CheckFunc {
    void check(String origin, String fuzzData, char fuzzChar);
}

enum HandleType {
    REPLACE, INSERT
}