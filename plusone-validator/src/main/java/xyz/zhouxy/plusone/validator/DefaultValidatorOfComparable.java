package xyz.zhouxy.plusone.validator;

import java.util.function.Function;

public class DefaultValidatorOfComparable<
        TObj,
        TProperty extends Comparable<TProperty>
    > extends ValidatorOfComparable<TObj, TProperty, DefaultValidatorOfComparable<TObj, TProperty>> {

    DefaultValidatorOfComparable(Function<TObj, ? extends TProperty> getter) {
        super(getter);
    }

    @Override
    protected DefaultValidatorOfComparable<TObj, TProperty> thisObject() {
        return this;
    }
}
