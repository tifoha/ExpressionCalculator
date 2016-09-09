package ua.tifoha.calc.util;

import java.util.Objects;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class Utils {
    public static final String VALID_CHARS = "+-/*,";
    public static boolean isValidChar(char c) {
        return VALID_CHARS.indexOf(c) > -1;
    }
    public static void requareNotNull(Object arg, Object... args) {
        Objects.nonNull(arg);
        if (args != null) {
            for (Object o : args) {
                Objects.nonNull(o);
            }

        }
    }
}
