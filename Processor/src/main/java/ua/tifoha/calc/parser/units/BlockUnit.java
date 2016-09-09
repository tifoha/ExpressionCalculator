package ua.tifoha.calc.parser.units;

import ua.tifoha.calc.exceptions.UnitParseException;
import ua.tifoha.calc.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class BlockUnit extends AbstractUnit {
    private final List<Unit> units;
    private final Bracket bracket;
    private final Parser parser;

    public BlockUnit(String s, Bracket bracket, Parser parser) {
        super(s);
        this.bracket = bracket;
        this.parser = parser;
        units = parseUnits(s);
    }

    public Bracket getBracket() {
        return bracket;
    }

    private List<Unit> parseUnits(String str) {
        List<Unit> result = new ArrayList<>();
        if (str.isEmpty()) {
            return result;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            //perhaps it is a function
            if (parser.isAlphabetic(c)) {
                int startPositionOfFunction = i;
                int endOfNamePos = getEndOfNamePosition(str, i);
                String functionNameString = str.substring(i, endOfNamePos);
                Unit unit = new StringUnit(functionNameString);
                if (endOfNamePos < str.length()) {
                    i = endOfNamePos;
                    c = str.charAt(i);
                    if (Bracket.isOpenBracketChar(c)) {
                        Bracket bkt = Bracket.getBracket(c).orElseThrow(UnitParseException::new);
                        int closeBracketPos = getCloseBracketPosition(str, i, bkt);
                        String blockUnitString = str.substring(i + 1, closeBracketPos);
                        BlockUnit argsBlock = new BlockUnit(blockUnitString, bkt, parser);
                        unit = new FunctionUnit(unit.getExpression(), argsBlock);
                        i = closeBracketPos;
                    } else {
                        i--;
                    }
                } else {
                    i = --endOfNamePos;
                }
                result.add(unit);
            } else if (Bracket.isOpenBracketChar(c)) {
                Bracket bkt = Bracket.getBracket(c).orElseThrow(UnitParseException::new);
                int closeBracketPos = getCloseBracketPosition(str, i, bkt);
                String blockUnitString = str.substring(i + 1, closeBracketPos);
                BlockUnit unit = new BlockUnit(blockUnitString, bkt, parser);
                i = closeBracketPos;
                result.add(unit);
            } else if (parser.isDigit(c)) {
                int endOfNumberPos = getEndOfNumberPosition(str, i);
                String numberUnitString = str.substring(i, endOfNumberPos);
                NumberUnit unit = new NumberUnit(numberUnitString);
                result.add(unit);
                i = --endOfNumberPos;
            }else {
                CharUnit unit = new CharUnit(c);
                result.add(unit);
            }
        }
        return result;
    }

    private int getEndOfNumberPosition(String str, int startPos) {
        char c = str.charAt(startPos);
        if (!parser.isDigit(c)) {
            throw new UnitParseException();
        }

        int i;
        for (i = startPos; i < str.length(); i++) {
            c = str.charAt(i);
            if (!parser.isDigit(c)) {
                return i;
            }
        }
        return i;
    }

    private int getCloseBracketPosition(String str, int startPos, Bracket bracket) {
        char c = str.charAt(startPos);

        if (!bracket.isOpenChar(c)) {
            throw new UnitParseException();
        }

        int count = 0;
        int i = startPos;
        for (; i < str.length(); i++) {
            c = str.charAt(i);
            if (bracket.isOpenChar(c)) {
                count++;
            } else if (bracket.isCloseChar(c)) {
                count--;
            }
            if (count < 0) {
                throw new UnitParseException();
            }

            if (count == 0) {
                break;
            }
        }

        return i;
    }

    private int getEndOfNamePosition(String str, int startPos) {
        char c = str.charAt(startPos);
        if (!parser.isAlphabetic(c)) {
            throw new UnitParseException();
        }

        int i;
        for (i = startPos; i < str.length(); i++) {
            c = str.charAt(i);
            if (!parser.isAlphabetic(c) && !parser.isDigit(c)) {
                return i;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        String unitsRepresentation = units.stream().map(Object::toString).collect(Collectors.joining());
        return bracket.getOpenChar() + unitsRepresentation + bracket.getCloseChar();
    }

    public List<Unit> getUnits() {
        return units;
    }
}
