package ua.tifoha.calc.parser.units;

import ua.tifoha.calc.exceptions.UnitParseException;

import java.math.BigDecimal;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class NumberUnit extends AbstractUnit {
    protected BigDecimal value;
    public NumberUnit(String s) {
        super(s);
        value = new BigDecimal(s);
    }

    public NumberUnit(BigDecimal value) {
        super(value.toString());
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public NumberUnit addFraction(NumberUnit unit) {

        if (value.scale() > 0 || unit.value.scale() > 0) {
            throw new UnitParseException();
        }

        BigDecimal tmp = unit.value.stripTrailingZeros();
        int powValue = tmp.precision() - tmp.scale();
        BigDecimal divisor = BigDecimal.TEN.pow(powValue);
        tmp = tmp.divide(divisor);
        BigDecimal newValue = value.add(tmp);

        return new NumberUnit(newValue);
    }
}
