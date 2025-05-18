package xyz.zhouxy.plusone.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * BaseValidator
 *
 * <p>
 * 校验器的基类
 * </p>
 *
 * <p>
 * <b>NOTE: content.</b>
 * </p>
 * @author <a href="http://zhouxy.xyz:3000/ZhouXY108">ZhouXY</a>
 * @since 0.0.1
 */
public abstract class BaseValidator<T> {
    private final List<Consumer<? super T>> rules = new ArrayList<>();

    protected void withRule(final Predicate<? super T> rule, final String errorMessage) {
        withRule(rule, () -> new IllegalArgumentException(errorMessage));
    }

    protected <E extends RuntimeException> void withRule(Predicate<? super T> rule, Supplier<E> exceptionBuilder) {
        withRule(rule, value -> exceptionBuilder.get());
    }

    protected <E extends RuntimeException> void withRule(
            Predicate<? super T> condition, Function<T, E> exceptionBuilder) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw exceptionBuilder.apply(value);
            }
        });
    }

    protected void withRule(Consumer<? super T> rule) {
        this.rules.add(rule);
    }

    protected final <R> ObjectPropertyValidator<T, R> ruleFor(Function<T, R> getter) {
        ObjectPropertyValidator<T, R> validator = new ObjectPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final <R extends Comparable<R>> DefaultComparablePropertyValidator<T, R> ruleForComparable(Function<T, R> getter) {
        DefaultComparablePropertyValidator<T, R> validator = new DefaultComparablePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final IntPropertyValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final LongPropertyValidator<T> ruleForLong(Function<T, Long> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final DoublePropertyValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final BoolPropertyValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final StringPropertyValidator<T> ruleForString(Function<T, String> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final <E> CollectionPropertyValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionPropertyValidator<T, E> validator = new CollectionPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    public void validate(T obj) {
        this.rules.forEach(rule -> rule.accept(obj));
    }
}
