package ua.tifoha.calc.compiler.tokens;

import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.executor.Executor;

import java.math.BigDecimal;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class OperandToken implements ComputableToken {
    private final BigDecimal value;

    public OperandToken(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal compute(Executor executor, Object[] args) {
        return value;
    }

    @Override
    public Type getType() {
        return Type.OPERAND;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
