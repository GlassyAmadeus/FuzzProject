package com.glassy.main;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class FastJsonDemo {
    private static final Map<Integer, List<String>> dict = StringFuzz.getParseIntFuzzMap();

    public static void main(String[] args) {
        String jsonData = "{\"content\":\"this is glassy\",\"num\":1}";
        System.out.println("************************************");
        System.out.println("json数据：" + jsonData);
        System.out.println("json反序列化为的Object：" + JSONObject.parseObject(jsonData));
        System.out.println("************************************");

        String encodeJson = "{\"" + getUnicodeStr("content") + "\":\"" + getHexStr("this is glassy") + "\",\"" + getHexStr("num") + "\":1}";
        System.out.println("************************************");
        System.out.println("json数据：" + encodeJson);
        System.out.println("json反序列化为的Object：" + JSONObject.parseObject(encodeJson));
        System.out.println("************************************");

        int index = 2; //决定使用那个异形字集合，当然也可以混合使用，为方便观察，测试选取一套，可以是0-33
        Map<Integer, List<String>> dict = StringFuzz.getParseIntFuzzMap();
        String otherEncodeJson = "{\"" + specialEncode(getUnicodeStr("content")) + "\":\"" + specialEncode(getUnicodeStr("this is glassy")) + "\",\"" + specialEncode(getUnicodeStr("num"))
                + "\":1}";
        System.out.println("************************************");
        System.out.println("json数据：" + otherEncodeJson);
        System.out.println("json反序列化为的Object：" + JSONObject.parseObject(otherEncodeJson));
        System.out.println("************************************");

    }

    private static String getUnicodeStr(String data) {
        char[] utfBytes = data.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    private static String getHexStr(String data) {
        String str = "";
        for (int i = 0; i < data.length(); i++) {
            int ch = (int) data.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + "\\x" + s4;
        }
        return str;
    }

    private static String specialEncode(String data) {
        String str = "";
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            String s4 = getSpecialNumChar(ch);
            str = str + s4;
        }
        return str;
    }

    private static String getSpecialNumChar(char ch) {
        if (Character.isDigit(ch)) {
            List<String> result = dict.get(Integer.parseInt(String.valueOf(ch)));
            return result.get(getRandom(33));
        } else {
            return String.valueOf(ch);
        }
    }

    private static int getRandom(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }
}
