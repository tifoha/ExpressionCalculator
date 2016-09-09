package ua.tifoha.calc.parser;

import ua.tifoha.calc.parser.units.BlockUnit;
import ua.tifoha.calc.parser.units.Bracket;
import ua.tifoha.calc.parser.units.Unit;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class SimpleUnitFactory implements UnitFactory {
    private final Parser parser;

    public SimpleUnitFactory(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Parser getParser() {
        return parser;
    }

    @Override
    public Unit create(String expression) {
        return new BlockUnit(expression, Bracket.PARENTHESES, parser);
    }
}
