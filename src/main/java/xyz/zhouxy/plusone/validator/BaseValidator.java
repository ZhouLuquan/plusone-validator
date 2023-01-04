package xyz.zhouxy.plusone.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BaseValidator<T> {
    private final List<Consumer<T>> rules = new ArrayList<>();
    private final List<PropertyValidator<T, ?, ?>> propertyValidators = new ArrayList<>();

    protected void withRule(final Predicate<T> rule, final String errorMessage) {
        withRule(rule, value -> new InvalidInputException(errorMessage));
    }

    protected <E extends RuntimeException> void withRule(Predicate<T> rule, Supplier<E> exceptionBuilder) {
        withRule(rule, value -> exceptionBuilder.get());
    }

    protected <E extends RuntimeException> void withRule(Predicate<T> condition,
            Function<T, E> exceptionBuilder) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw exceptionBuilder.apply(value);
            }
        });
    }

    protected void withRule(Consumer<T> rule) {
        this.rules.add(rule);
    }

    protected final <R> ObjectValidator<T, R> ruleFor(Function<T, R> getter) {
        ObjectValidator<T, R> validValueHolder = new ObjectValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    protected final IntValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntValidator<T> validValueHolder = new IntValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    protected final DoubleValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoubleValidator<T> validValueHolder = new DoubleValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    protected final BoolValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolValidator<T> validValueHolder = new BoolValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    protected final StringValidator<T> ruleForString(Function<T, String> getter) {
        StringValidator<T> validValueHolder = new StringValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    protected final <E> CollectionValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionValidator<T, E> validValueHolder = new CollectionValidator<>(getter);
        propertyValidators.add(validValueHolder);
        return validValueHolder;
    }

    public void validate(T obj) {
        for (Consumer<T> rule : this.rules) {
            rule.accept(obj);
        }
        for (PropertyValidator<T, ?, ?> valueValidator : this.propertyValidators) {
            valueValidator.validate(obj);
        }
    }
}
