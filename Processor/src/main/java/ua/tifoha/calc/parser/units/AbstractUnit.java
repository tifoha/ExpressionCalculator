package ua.tifoha.calc.parser.units;

/**
 * Created by Vitaly on 09.09.2016.
 */
public abstract class AbstractUnit implements Unit {
    protected String expressionString;

    public AbstractUnit(String expressionString) {
        this.expressionString = expressionString;
    }

    @Override
    public String toString() {
        return '{' + expressionString + '}';
    }

    @Override
    public String getExpression() {
        return expressionString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUnit)) return false;

        AbstractUnit that = (AbstractUnit) o;

        return expressionString != null ? expressionString.equals(that.expressionString) : that.expressionString == null;

    }

    @Override
    public int hashCode() {
        return expressionString != null ? expressionString.hashCode() : 0;
    }
}
