package ua.tifoha.calc.parser.units;


/**
 * Created by Vitaly on 09.09.2016.
 */
public class CharUnit extends AbstractUnit {
    private char c;

    public CharUnit(char c) {
        super(String.valueOf(c));
        this.c = c;
    }

    public char getChar() {
        return c;
    }
}
