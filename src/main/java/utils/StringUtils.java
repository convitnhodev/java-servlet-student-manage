package utils;

import java.util.List;


public class StringUtils {
    public static boolean isBlankOrNull(String str) {
        return str == null || str.isEmpty();
    }

    public static String join(List<Integer> ids, String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : ids) {
            stringBuilder.append(id).append(s);
        }
        return stringBuilder.toString();
    }
}
