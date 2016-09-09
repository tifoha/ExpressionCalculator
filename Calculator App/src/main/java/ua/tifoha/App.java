package ua.tifoha;

import ua.tifoha.calc.Context;
import ua.tifoha.calc.MathContext;
import ua.tifoha.calc.compiler.Compiler;
import ua.tifoha.calc.compiler.ExpressionCompiler;
import ua.tifoha.calc.compiler.tokens.ComputableToken;
import ua.tifoha.calc.executor.Executor;
import ua.tifoha.calc.executor.ExpressionExecutor;
import ua.tifoha.calc.parser.ExpressionParser;
import ua.tifoha.calc.parser.Parser;
import ua.tifoha.calc.parser.units.Unit;

import java.io.Console;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private Context context;
    private Parser parser;
    private Compiler compiler;
    private Executor executor;

    public App(Context context, Parser parser, Compiler compiler, Executor executor) {
        this.context = context;
        this.parser = parser;
        this.compiler = compiler;
        this.executor = executor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public List<BigDecimal> calculate(String expression) {
        List<Unit> units = parser.parse(expression);
        // Build prn RPN string
        List<ComputableToken> prn = compiler.compile(units);
        List<BigDecimal> result = executor.execute(prn);

        return result;
    }

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            return;
        }

        Parser parser = new ExpressionParser();
        Compiler compiler = new ExpressionCompiler();
        Executor executor = new ExpressionExecutor();
        Context context = new MathContext(parser, compiler, executor);
        App calc = new App(context, parser, compiler, executor);
        PrintWriter printWriter = console.writer();
        printWriter.println("Welcome to \"Expression calculator\"!");
        printWriter.println("For exit type \"quit\" or \"exit\"");

        while (true) {
            String expression = console.readLine("Input expression: ");
            expression = expression.trim();
            String command = expression.toLowerCase();

            if ("quit".equals(command) || "exit".equals(command)) {
                return;
            }

            try {
                List<BigDecimal> results = calc.calculate(expression);
                printWriter.printf("Results: %s%n", results);
            } catch (RuntimeException e) {
                printWriter.println("Invalid expression!");
                printWriter.println(e);
            }
        }
    }
}
