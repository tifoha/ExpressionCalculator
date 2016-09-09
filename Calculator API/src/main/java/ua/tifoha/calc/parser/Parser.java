package ua.tifoha.calc.parser;

import ua.tifoha.calc.executor.WithContext;
import ua.tifoha.calc.parser.units.Unit;

import java.util.List;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface Parser extends WithContext{
    boolean isRootSeparators(char c);

    boolean isExcludedChar(int c);

    List<Unit> parse(String str);

    boolean isAlphabetic(char c);

    boolean isDigit(char c);
}
