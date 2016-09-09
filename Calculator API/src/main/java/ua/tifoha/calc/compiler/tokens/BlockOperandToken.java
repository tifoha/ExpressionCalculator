package ua.tifoha.calc.compiler.tokens;

import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.executor.Executor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class BlockOperandToken implements ComputableToken {
    private final List<ComputableToken> tokens;

    public BlockOperandToken(List<ComputableToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public BigDecimal compute(Executor executor, Object[] args) {
        return executor.execute(tokens).get(0);
    }

    @Override
    public String toString() {
        String tokensRepresentation = tokens.stream().map(Object::toString).collect(Collectors.joining());
        return "(" + tokensRepresentation + ')';
    }
}
