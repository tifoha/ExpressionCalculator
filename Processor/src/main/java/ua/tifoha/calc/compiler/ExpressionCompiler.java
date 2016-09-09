package ua.tifoha.calc.compiler;

import ua.tifoha.calc.Context;
import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.compiler.tokens.Operator;
import ua.tifoha.calc.executor.WithContext;
import ua.tifoha.calc.parser.units.Unit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static ua.tifoha.calc.compiler.tokens.Operator.Associativity.LEFT;
import static ua.tifoha.calc.compiler.tokens.Operator.Associativity.RIGHT;

//import java.math.MathContext;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class ExpressionCompiler implements Compiler, WithContext {
    private Context context;

    @Override
    public List<ComputableToken> compile(List<Unit> inputTokens){
        List<ComputableToken> out = new ArrayList<>();
        Deque<Unit> stack = new LinkedList<>();

        // For each token
        for (Unit unit : inputTokens)
        {
            // If unit is an operator
            if (context.isOperator(unit)){
                // While stack not empty AND stack top element
                // is an operator
                while (!stack.isEmpty() && context.isOperator(stack.peek())){
                    Operator currentOperator = context.getOperator(unit);
                    Operator lastOperator = context.getOperator(stack.peek());

                    if ((context.isAssociative(currentOperator, LEFT)         &&
                            currentOperator.compareTo(lastOperator) <= 0) ||
                            (context.isAssociative(currentOperator, RIGHT)        &&
                                    currentOperator.compareTo(lastOperator) < 0)){
                        stack.pop();
                        out.add(lastOperator);
                        continue;
                    }
                    break;
                }

                // Push the new operator on the stack
                stack.push(unit);
            }else{ // If unit is a number
                ComputableToken token = context.getToken(unit);
                out.add(token);
            }
        }

        while (!stack.isEmpty()){
            ComputableToken token = context.getToken(stack.pop());
            out.add(token);
        }

        return out;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}