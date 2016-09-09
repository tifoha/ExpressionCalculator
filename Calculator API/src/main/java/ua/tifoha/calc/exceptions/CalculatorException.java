package ua.tifoha.calc.exceptions;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class CalculatorException extends RuntimeException {
    public CalculatorException() {
        super();
    }

    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculatorException(Throwable cause) {
        super(cause);
    }
}
