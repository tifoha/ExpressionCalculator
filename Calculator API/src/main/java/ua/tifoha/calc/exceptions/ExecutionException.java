package ua.tifoha.calc.exceptions;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class ExecutionException extends CalculatorException {
    public ExecutionException() {
    }

    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(Throwable cause) {
        super(cause);
    }
}
