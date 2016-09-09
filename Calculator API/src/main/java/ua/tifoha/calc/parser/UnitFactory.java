package ua.tifoha.calc.parser;

import ua.tifoha.calc.parser.units.Unit;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface UnitFactory {
    Parser getParser();

    Unit create(String expression);
}
