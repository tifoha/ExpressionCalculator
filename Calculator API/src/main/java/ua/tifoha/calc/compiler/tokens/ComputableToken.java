package ua.tifoha.calc.compiler.tokens;

import ua.tifoha.calc.executor.Executor;

import java.math.BigDecimal;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface ComputableToken {
    enum Type{OPERAND,OPERATOR}

    default BigDecimal compute(Executor executor) {
        return compute(executor, null);
    }

    BigDecimal compute(Executor executor, Object[] args);

    default Type getType(){
        return Type.OPERAND;
    }
}
