package ua.tifoha.calc;

import ua.tifoha.calc.compiler.Compiler;
import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.compiler.tokens.FunctionPrototypeToken;
import ua.tifoha.calc.compiler.tokens.OperandToken;
import ua.tifoha.calc.compiler.tokens.Operator;
import ua.tifoha.calc.executor.Executor;
import ua.tifoha.calc.parser.Parser;
import ua.tifoha.calc.parser.units.Unit;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface Context{
    void addOperator(Operator operator);

    boolean isOperator(Unit unit);

    Operator getOperator(Unit unit);

    // Test associativity of operator
    boolean isAssociative(Operator operator, Operator.Associativity type);

    OperandToken getConstant(String key);

    boolean isConstant(Unit unit);

    void addConstant(String name, BigDecimal value);

    void addFunction(FunctionPrototypeToken function);

    boolean isFunction(Unit unit);

    Method getMethod(String name);

    ComputableToken getToken(Unit unit);

    List<BigDecimal> calculate(String expression);

    Parser getParser();

    Compiler getCompiler();

    Executor getExecutor();
}
