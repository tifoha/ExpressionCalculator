package ua.tifoha.calc.exceptions;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class UnitParseException extends CalculatorException {
    public UnitParseException() {
    }

    public UnitParseException(String message) {
        super(message);
    }

    public UnitParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnitParseException(Throwable cause) {
        super(cause);
    }
}
