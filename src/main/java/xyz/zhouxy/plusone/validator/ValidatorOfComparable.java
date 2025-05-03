package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Range;

public abstract
class ValidatorOfComparable<TObj,
                            TProperty extends Comparable<TProperty>,
                            TPropertyValidator extends ValidatorOfComparable<TObj, TProperty, TPropertyValidator>>
    extends BasePropertyValidator<TObj, TProperty, TPropertyValidator> {

    ValidatorOfComparable(Function<TObj, ? extends TProperty> getter) {
        super(getter);
    }

    public TPropertyValidator between(Range<TProperty> range) {
        withRule(range::contains, convertExceptionCreator("The value is not in " + range.toString()));
        return thisObject();
    }

    public TPropertyValidator between(Range<TProperty> range, String errMsg) {
        withRule(range::contains, convertExceptionCreator(errMsg));
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator between(
            Range<TProperty> range,
            Supplier<E> exceptionCreator) {
        withRule(range::contains, exceptionCreator);
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator between(
            Range<TProperty> range,
            Function<TProperty, E> exceptionCreator) {
        withRule(range::contains, exceptionCreator);
        return thisObject();
    }

    static <V> Function<V, IllegalArgumentException> convertExceptionCreator(String errMsg) {
        return value -> new IllegalArgumentException(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
}
