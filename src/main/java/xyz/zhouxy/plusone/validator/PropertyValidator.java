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
        return equalsThat(that, value -> InvalidInputException.of(String.format("(%s) 必须与 (%s) 相等", value, that)));
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

    // ===== isTrue =====

    public THIS isTrue(Predicate<PROPERTY> condition) {
        return isTrue(condition, "无效的用户输入");
    }

    public THIS isTrue(Predicate<PROPERTY> condition, String errMsg) {
        return isTrue(condition, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS isTrue(
            Predicate<PROPERTY> condition,
            Supplier<E> exceptionCreator) {
        return isTrue(condition, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS isTrue(
            Predicate<PROPERTY> condition,
            Function<PROPERTY, E> exceptionCreator) {
        withRule(condition, exceptionCreator);
        return thisObject();
    }

    // ===== isTrue =====

    public THIS isTrue(Collection<Predicate<PROPERTY>> conditions) {
        return isTrue(conditions, "无效的用户输入");
    }

    public THIS isTrue(Collection<Predicate<PROPERTY>> conditions, String errMsg) {
        return isTrue(conditions, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> THIS isTrue(
            Collection<Predicate<PROPERTY>> conditions,
            Supplier<E> exceptionCreator) {
        return isTrue(conditions, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> THIS isTrue(
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
        return value -> InvalidInputException.of(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(
            Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
    
    protected abstract THIS thisObject();
}
