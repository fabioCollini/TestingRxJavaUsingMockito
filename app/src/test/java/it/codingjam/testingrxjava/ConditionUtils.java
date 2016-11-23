package it.codingjam.testingrxjava;

import android.support.annotation.NonNull;
import org.assertj.core.api.Condition;
import rx.functions.Func1;

public class ConditionUtils {
    @NonNull public static <T> Condition<T> condition(Func1<T, Boolean> cond) {
        return new Condition<T>() {
            @Override public boolean matches(T value) {
                return cond.call(value);
            }
        };
    }
}
