package ua.tifoha.calc.parser.units;

import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/**
 * Created by Vitaly on 09.09.2016.
 */
public enum Bracket {
    PARENTHESES('(', ')'), BRACES('{', '}'), SQUARE_BRACKETS('[', ']');
    private final char openChar;
    private final char closeChar;

    Bracket(char openChar, char closeChar) {
        this.openChar = openChar;
        this.closeChar = closeChar;
    }

    public char getOpenChar() {
        return openChar;
    }

    public char getCloseChar() {
        return closeChar;
    }

    public boolean belongTo(char c) {
        return openChar == c || closeChar == c;
    }

    public static boolean isOpenBracketChar(char c) {
        return isBracketChar_0(Bracket::getOpenChar, c);
    }

    public static boolean isCloseBracketChar(char c) {
        return isBracketChar_0(Bracket::getCloseChar, c);
    }

    public static boolean isBracketChar(char c) {
        return isOpenBracketChar(c) || isCloseBracketChar(c);
    }

    private static boolean isBracketChar_0(ToIntFunction<Bracket> charExtractor, char c) {
        return getValues().anyMatch(b -> b.getOpenChar() == c);
    }

    private static Stream<Bracket> getValues() {
        return Stream.of(values());
    }

    public static Optional<Bracket> getBracket(char c) {
        return getValues().filter(b->b.belongTo(c)).findFirst();
    }

    public boolean isOpenChar(char c) {
        return openChar == c;
    }
    public boolean isCloseChar(char c) {
        return closeChar == c;
    }
}
