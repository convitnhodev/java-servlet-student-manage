package utils;

import java.util.Collection;
import java.util.Collections;


public class CollectionUtils {
    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }
}
