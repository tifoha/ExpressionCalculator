package ua.tifoha.calc.parser;

import ua.tifoha.calc.Context;
import ua.tifoha.calc.executor.WithContext;
import ua.tifoha.calc.parser.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaly on 09.09.2016.
 */
public class ExpressionParser implements Parser, WithContext {
    private Context context;
    private String rootSeparators = ";";
    private String excludedChars = " \r\n";
    private String digits = "0123456789.";
    private UnitFactory rootFactory = new SimpleUnitFactory(this);


    @Override
    public boolean isRootSeparators(char c) {
        return rootSeparators.indexOf(c) > -1;
    }

//    public boolean isDecimalSeparator(char c) {
//        return digits == c;
//    }

    @Override
    public boolean isExcludedChar(int c) {
        return excludedChars.indexOf(c) > -1;
    }

    protected String excludeRedundantChars(String str) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (!isExcludedChar(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Override
    public List<Unit> parse(String str) {
        str = excludeRedundantChars(str);

        List<String> blocks = splitRootStrings(str);
        return getRootUnits(blocks);
    }

    private List<Unit> getRootUnits(List<String> blocks) {
        List<Unit> result = new ArrayList<>();

        for (String expression : blocks) {
            Unit root = rootFactory.create(expression);
            result.add(root);
        }

        return result;
    }

    private List<String> splitRootStrings(String str) {
        List<String> result = new ArrayList<>();
        if (str.isEmpty()) {
            return result;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, start = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (isRootSeparators(c)) {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0) {
            result.add(sb.toString());
        }

        return result;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public boolean isAlphabetic(char c) {
        return Character.isAlphabetic(c);
    }

    @Override
    public boolean isDigit(char c) {
        return digits.indexOf(c) > -1;
    }
}
