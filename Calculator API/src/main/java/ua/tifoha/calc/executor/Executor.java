package ua.tifoha.calc.executor;

import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.parser.units.Unit;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface Executor extends WithContext {
    BigDecimal getDecimal(Unit unit);

    List<BigDecimal> execute(List<ComputableToken> tokens);

    BigDecimal execute(ComputableToken argument);
}
