package ua.tifoha.calc.compiler;

import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.executor.WithContext;
import ua.tifoha.calc.parser.units.Unit;

import java.util.List;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface Compiler extends WithContext {
    List<ComputableToken> compile(List<Unit> inputTokens);
}
