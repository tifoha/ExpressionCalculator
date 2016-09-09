package ua.tifoha.calc;

import ua.tifoha.calc.compiler.Compiler;
import ua.tifoha.calc.compiler.ExpressionCompiler;
import ua.tifoha.calc.compiler.tokens.*;
import ua.tifoha.calc.exceptions.CalculatorException;
import ua.tifoha.calc.executor.Executor;
import ua.tifoha.calc.executor.ExpressionExecutor;
import ua.tifoha.calc.parser.ExpressionParser;
import ua.tifoha.calc.parser.Parser;
import ua.tifoha.calc.parser.units.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.tifoha.calc.compiler.tokens.Operator.Precedence.FOUR;
import static ua.tifoha.calc.compiler.tokens.Operator.Precedence.THREE;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class MathContext implements Context {
    private final Map<String, Operator> operators = new HashMap<>();
    private final Map<String, OperandToken> constants = new HashMap<>();
    private final Map<String, FunctionPrototypeToken> functions = new HashMap<>();
    private final Parser parser;
    private final Compiler compiler;
    private final Executor executor;

    public MathContext(Parser parser, Compiler compiler, Executor executor) {
        this.parser = parser;
        parser.setContext(this);
        this.compiler = compiler;
        compiler.setContext(this);
        this.executor = executor;
        executor.setContext(this);

        addOperator_0(THREE.operator("*", getMethod("multiplication")));
        addOperator_0(THREE.operator("/", getMethod("dividing")));
        addOperator_0(THREE.operator("%", getMethod("modulo")));
        addOperator_0(FOUR.operator("+", getMethod("addition")));
        addOperator_0(FOUR.operator("-", getMethod("subtraction")));

        addConstant("e", new BigDecimal("2.1828182845904523536028747135266249775724709369995"));
        addConstant("pi", new BigDecimal("3.14159265358979323846264338327950288419716939937510"));
        addConstant("ten", new BigDecimal("10"));

        addFunction(new FunctionPrototypeToken("pow", getMethod("pow")));
    }


    private void addOperator_0(Operator operator) {
        operators.put(operator.getKey(), operator);
    }

    @Override
    public void addOperator(Operator operator) {
        addOperator_0(operator);
    }

    @Override
    public boolean isOperator(Unit unit){
        if (unit instanceof CharUnit) {
            CharUnit charUnit = (CharUnit) unit;
            String key = charUnit.getExpression();
            return operators.containsKey(key);
        }

        return false;
    }

    @Override
    public Operator getOperator(Unit unit) {
        return operators.get(unit.getExpression());
    }

    // Test associativity of operator
    @Override
    public boolean isAssociative(Operator operator, Operator.Associativity type) {
        return operator.getAssociativity() == type;
    }

    @Override
    public OperandToken getConstant(String key) {
        return constants.get(key);
    }

    @Override
    public boolean isConstant(Unit unit){
        if (unit instanceof StringUnit) {
            StringUnit stringUnit = (StringUnit) unit;
            String key = stringUnit.getExpression();
            return constants.containsKey(key);
        }

        return false;
    }

    @Override
    public void addConstant(String name, BigDecimal value) {
        OperandToken constToken = new OperandToken(value){
            @Override
            public BigDecimal compute(Executor executor) {
                return super.compute(executor, null);
            }

            @Override
            public BigDecimal compute(Executor executor, Object[] args) {
                return super.compute(executor, args);
            }
        };
        constants.put(name, constToken);
    }

    private void addFunction_0(FunctionPrototypeToken function) {
        functions.put(function.getKey(), function);
    }

    @Override
    public void addFunction(FunctionPrototypeToken function) {
        addFunction_0(function);
    }

    @Override
    public boolean isFunction(Unit unit){
        if (unit instanceof FunctionUnit) {
            FunctionUnit functionUnit = (FunctionUnit) unit;
            String key = functionUnit.getExpression();
            return functions.containsKey(key);
        }

        return false;
    }

    public static BigDecimal pow(BigDecimal v1, BigDecimal v2) {
        if (v2.scale() > 0) {
            throw new CalculatorException("second value is not an Integer");
        }

        return v1.pow(v2.intValue());
    }

    public static BigDecimal addition(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }

    public static BigDecimal subtraction(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    public static BigDecimal dividing(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2);
    }

    public static BigDecimal multiplication(BigDecimal v1, BigDecimal v2) {
        return v1.multiply(v2);
    }

    public static BigDecimal modulo(BigDecimal v1, BigDecimal v2) {
        return v1.remainder(v2);
    }

    @Override
    public Method getMethod(String name) {
        Method[] methods = MathContext.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        throw new CalculatorException("Method not found " + name);
    }

    @Override
    public ComputableToken getToken(Unit unit) {
        if (unit instanceof NumberUnit) {
            NumberUnit numberUnit = (NumberUnit) unit;
            return new OperandToken(numberUnit.getValue());
        }else if (unit instanceof CharUnit && isOperator(unit)) {
            return getOperator(unit);
        }else if (unit instanceof BlockUnit) {
            BlockUnit blockUnit = (BlockUnit) unit;
            List<Unit> units = blockUnit.getUnits();
            List<ComputableToken> tokens = compiler.compile(units);
            return new BlockOperandToken(tokens);
        } else if (unit instanceof StringUnit) {
            return getConstant(unit.getExpression());
        } else if (unit instanceof FunctionUnit) {
            FunctionUnit functionUnit = (FunctionUnit) unit;
            FunctionPrototypeToken functionPrototype = functions.get(functionUnit.getExpression());
            List<ComputableToken> args = compiler.compile(functionUnit.getArgs());
            return functionPrototype.withParams(args);
        }
            throw new CalculatorException("Unsupported unit " + unit);
    }

    @Override
    public List<BigDecimal> calculate(String expression) {


        return null;
    }

    @Override
    public Parser getParser() {
        return parser;
    }

    @Override
    public Compiler getCompiler() {
        return compiler;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    public static void main(String[] args) {

        Parser parser = new ExpressionParser();
        Compiler compiler = new ExpressionCompiler();
        Executor executor = new ExpressionExecutor();
        Context context = new MathContext(parser, compiler, executor);

        System.out.println(10.0 + 10.0 + 10.0 * 2.0 + 1.0 / 2.0);
        String exp = "10 + 10 + 10 * 2 + 1 / 2 + (2 + 2 * 2 - (12 - 6))";
//        String exp = "1 + ( 2 + 2 ) * 2 + pow(2,3) + ten ";
//        String exp = "1 + 2 * 3 / 4 - 5 + 6";
//        String exp = "( 1 + 2 ) * ( 3 / 4 ) - ( 5 + 6 )";
//        String exp = "( 1 + (2 + 1) ) * ( 3 / 4 ) - ( 5 + 6 )";
        System.out.println(exp);

        List<Unit> units = parser.parse(exp);
        System.out.println(units);

        List<ComputableToken> prn = compiler.compile(units);
        // Build prn RPN string minus the commas
        System.out.println(prn);

        List<BigDecimal> result = executor.execute(prn);
//
//        // Feed the RPN string to RPNtoDouble to give units
//        Double units = parser.RPNtoDouble( prn );
        System.out.println(result);
    }
}
