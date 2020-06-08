package com.example.portal.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: RegexUtil
 * @description: TODO
 * @author: Chonzi.xiao
 * @create: 2019-07-16 17:26
 */
public class RegexUtils {
    private static Set<Character> SPECIAL_WORDS = new HashSet<>(Arrays.asList('\\', '$', '(', ')', '*', '+', '.', '[', ']', '?', '^', '{', '}', '|'));
    private static final String PHONE_REGEX = "^(1)\\d{10}$";

    public static String escape(String regex) {
        if (regex == null || regex.isEmpty()) {
            return regex;
        }
        char[] chars = regex.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char aChar : chars) {
            if (SPECIAL_WORDS.contains(aChar)) {
                builder.append('\\');
            }
            builder.append(aChar);
        }
        return builder.toString();
    }

    /**
     * 手机号校验
     *
     * @param mobile
     * @return
     */
    public static boolean validatePhone(String mobile) {
        Pattern p = Pattern.compile(PHONE_REGEX);
        Matcher m = p.matcher(mobile);
        boolean isMatch = m.matches();
        if (isMatch) {
            return true;
        }
        return false;
    }

}
