package ua.tifoha.calc.compiler.tokens;

import ua.tifoha.calc.exceptions.ExecutionException;
import ua.tifoha.calc.executor.Executor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class FunctionPrototypeToken implements ComputableToken {
    private final String key;
    private final Method method;
    private List<ComputableToken> args;

    public FunctionPrototypeToken(String key, Method method) {
        this.key = key;
        this.method = method;
    }

    private FunctionPrototypeToken(FunctionPrototypeToken prototype, List<ComputableToken> args) {
        this.key = prototype.key;
        this.method = prototype.method;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public ComputableToken withParams(List<ComputableToken> args) {
        return new FunctionPrototypeToken(this, args);
    }

    @Override
    public BigDecimal compute(Executor executor, Object[] ignore) {
        Object[] arguments = new Object[this.args.size()];

        int i = 0;
        for (ComputableToken argument : args) {
            arguments[i++] = argument.compute(executor);
        }

        try {
            BigDecimal value = (BigDecimal) method.invoke(null, arguments);
            return value;
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public String toString() {
        String tokensRepresentation = args.stream().map(Object::toString).collect(Collectors.joining(", "));

        StringBuilder sb = new StringBuilder()
                .append(key)
                .append('(')
                .append(tokensRepresentation)
                .append(')');

        return sb.toString();
    }
}
