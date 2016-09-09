package ua.tifoha.calc.parser.units;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class FunctionUnit extends AbstractUnit {
    private List<Unit> args;

    public FunctionUnit(String s, BlockUnit blockUnit) {
        super(s);
        args = new ArrayList<>();
        for (Unit unit :blockUnit.getUnits()) {
            if (!(unit instanceof CharUnit)) {
                args.add(unit);
            }
        }
    }

    public List<Unit> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        String argsString = args.stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));

        StringBuilder sb = new StringBuilder(expressionString)
                .append(argsString);

        return argsString;
    }

}
