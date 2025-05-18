package xyz.zhouxy.plusone.validator;

import java.util.function.Function;

public class DefaultComparablePropertyValidator<TObj, TProperty extends Comparable<TProperty>>
        extends ComparablePropertyValidator<TObj, TProperty, DefaultComparablePropertyValidator<TObj, TProperty>> {

    DefaultComparablePropertyValidator(Function<TObj, ? extends TProperty> getter) {
        super(getter);
    }

    @Override
    protected DefaultComparablePropertyValidator<TObj, TProperty> thisObject() {
        return this;
    }
}
