package ua.tifoha.calc.compiler.tokens;

import ua.tifoha.calc.exceptions.ExecutionException;
import ua.tifoha.calc.executor.Executor;
import ua.tifoha.calc.util.Utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class Operator implements Comparable<Operator>, ComputableToken {
    public static final Associativity DEFAULT_ASSOCIATIVITY = Associativity.LEFT;

    private final Associativity associativity;
    private final String key;
    private final Precedence precedence;
    private final Method method;

    @Override
    public BigDecimal compute(Executor executor, Object[] args) {
        try {
            BigDecimal value = (BigDecimal) method.invoke(null, args);
            return value;
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public Type getType() {
        return Type.OPERATOR;
    }

    public enum Associativity {
        LEFT, RIGHT,
    }

    public enum Precedence{
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN, LOW;

        public Operator operator(String key, Method method, Associativity associativity) {
            return new Operator(key, method, this, associativity);
        }

        public Operator operator(String key, Method method) {
            return new Operator(key, method, this, DEFAULT_ASSOCIATIVITY);
        }
    }

    public Operator(String key, Method method, Precedence precedence, Associativity associativity) {
        Utils.requareNotNull(key, method, precedence, associativity);
        this.key = key;
        this.precedence = precedence;
        this.associativity = associativity;
        this.method = method;
    }

    public Operator(String key, Method method, Precedence precedence) {
        this(key, method, precedence, DEFAULT_ASSOCIATIVITY);
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public String getKey() {
        return key;
    }

    public Precedence getPrecedence() {
        return precedence;
    }

    @Override
    public int compareTo(Operator o) {
        return o.precedence.compareTo(precedence);
    }

    @Override
    public String toString() {
        return key;
    }
}
