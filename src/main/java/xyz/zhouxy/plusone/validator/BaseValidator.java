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

    protected void withRule(final Predicate<T> rule, final String errorMessage) {
        withRule(rule, () -> new IllegalArgumentException(errorMessage));
    }

    protected <E extends RuntimeException> void withRule(Predicate<T> rule, Supplier<E> exceptionBuilder) {
        withRule(rule, value -> exceptionBuilder.get());
    }

    protected <E extends RuntimeException> void withRule(
            Predicate<T> condition, Function<T, E> exceptionBuilder) {
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
        ObjectValidator<T, R> validator = new ObjectValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final IntValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntValidator<T> validator = new IntValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final DoubleValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoubleValidator<T> validator = new DoubleValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final BoolValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolValidator<T> validator = new BoolValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final StringValidator<T> ruleForString(Function<T, String> getter) {
        StringValidator<T> validator = new StringValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final <E> CollectionValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionValidator<T, E> validator = new CollectionValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    public void validate(T obj) {
        this.rules.forEach(rule -> rule.accept(obj));
    }
}
