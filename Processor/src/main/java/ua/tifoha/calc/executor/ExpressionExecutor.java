package ua.tifoha.calc.executor;

import ua.tifoha.calc.Context;
import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.compiler.tokens.OperandToken;
import ua.tifoha.calc.parser.units.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static ua.tifoha.calc.compiler.tokens.ComputableToken.Type.OPERATOR;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class ExpressionExecutor implements Executor, WithContext {
    private Context context;


    @Override
    public BigDecimal getDecimal(Unit unit) {
        return null;
    }

    @Override
    public List<BigDecimal> execute(List<ComputableToken> tokens) {
        Deque<ComputableToken> stack = new LinkedList<>();
        List<BigDecimal> results = new ArrayList<>();
        for (ComputableToken token : tokens) {
            // If the token is a expressionString push it onto the stack
            if (token.getType() != OPERATOR) {
                stack.push(token);
            } else {
                // Token is an operator: pop top two entries
                ComputableToken t2 = stack.poll();
                ComputableToken t1 = stack.poll();

                BigDecimal v1 = t1.compute(this);
                BigDecimal v2 = t2.compute(this);

                Object[] args = {v1, v2};

                //Get the result
                BigDecimal result = token.compute(this, args);
                // Push result onto stack
                stack.push(new OperandToken(result));
            }
        }

        while (!stack.isEmpty()) {
            results.add(stack.pollLast().compute(this));
        }

        return results;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public BigDecimal execute(ComputableToken argument) {
        return null;
    }
}
