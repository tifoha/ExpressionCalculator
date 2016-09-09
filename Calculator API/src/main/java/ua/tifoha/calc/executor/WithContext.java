package ua.tifoha.calc.executor;

import ua.tifoha.calc.Context;

/**
 * Created by Vitaly on 09.09.2016.
 */
public interface WithContext {
    void setContext(Context context);

    Context getContext();
}
