package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

abstract class PropertyValidator<DTO, PROPERTY, THIS> {
    Function<DTO, PROPERTY> getter;
    Validator<PROPERTY> validator = new Validator<>();

    PropertyValidator(Function<DTO, PROPERTY> getter) {
        this.getter = getter;
    }

    <E extends RuntimeException> void withRule(Predicate<PROPERTY> condition,
            Function<PROPERTY, E> exceptionCreator) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw exceptionCreator.apply(value);
            }
        });
    }

    private void withRule(Consumer<PROPERTY> rule) {
        this.validator.addRule(rule);
    }

    // ====================
    // ====== Object ======
    // ====================

    // ====== notNull =====

    public THIS notNull() {
        return notNull("Value could not be null.");
    }

    public THIS notNull(String errMsg) {
        return notNull(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS notNull(Supplier<E> exceptionCreator) {
        return notNull(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS notNull(Function<PROPERTY, E> exceptionCreator) {
        withRule(Objects::nonNull, exceptionCreator);
        return thisObject();
    }

    // ====== isNull =====

    public THIS isNull(String errMsg) {
        return isNull(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS isNull(Supplier<E> exceptionCreator) {
        return isNull(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS isNull(Function<PROPERTY, E> exceptionCreator) {
        withRule(Objects::isNull, exceptionCreator);
        return thisObject();
    }

    // ===== equals =====

    public THIS equalsThat(Object that) {
        return equalsThat(that, value -> new InvalidInputException(String.format("(%s) 必须与 (%s) 相等", value, that)));
    }

    public THIS equalsThat(Object that, String errMsg) {
        return equalsThat(that, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS equalsThat(
            Object that, Supplier<E> exceptionCreator) {
        return equalsThat(that, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS equalsThat(
            Object that, Function<PROPERTY, E> exceptionCreator) {
        withRule(value -> Objects.equals(value, that), exceptionCreator);
        return thisObject();
    }

    // ===== state =====

    public THIS state(Predicate<PROPERTY> condition) {
        return state(condition, "无效的用户输入");
    }

    public THIS state(Predicate<PROPERTY> condition, String errMsg) {
        return state(condition, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS state(
            Predicate<PROPERTY> condition,
            Supplier<E> exceptionCreator) {
        return state(condition, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS state(
            Predicate<PROPERTY> condition,
            Function<PROPERTY, E> exceptionCreator) {
        withRule(condition, exceptionCreator);
        return thisObject();
    }

    // ===== state =====

    public THIS state(Collection<Predicate<PROPERTY>> conditions) {
        return state(conditions, "无效的用户输入");
    }

    public THIS state(Collection<Predicate<PROPERTY>> conditions, String errMsg) {
        return state(conditions, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS state(
            Collection<Predicate<PROPERTY>> conditions,
            Supplier<E> exceptionCreator) {
        return state(conditions, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS state(
            Collection<Predicate<PROPERTY>> conditions,
            Function<PROPERTY, E> exceptionCreator) {
        for (Predicate<PROPERTY> condition : conditions) {
            withRule(condition, exceptionCreator);
        }
        return thisObject();
    }

    // ========================================================================

    void validate(DTO obj) {
        PROPERTY value = this.getter.apply(obj);
        this.validator.validate(value);
    }

    static <V> Function<V, InvalidInputException> convertExceptionCreator(String errMsg) {
        return value -> new InvalidInputException(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(
            Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
    
    protected abstract THIS thisObject();
}
