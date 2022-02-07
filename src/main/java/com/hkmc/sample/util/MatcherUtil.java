package com.hkmc.sample.util;

import java.util.regex.Pattern;

public class MatcherUtil {

    public static String replaceAll(Pattern pattern, String source, String replacement) {
        return pattern.matcher(source).replaceAll(replacement);
    }

    public static String replaceAll(String source, String regex, String replacement) {
        return replaceAll(getPattern(regex), source, replacement);
    }

    public static Pattern getPattern(String regex) {
        return Pattern.compile(regex);
    }

}
