package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class BoolPropertyValidator<DTO> extends BasePropertyValidator<DTO, Boolean, BoolPropertyValidator<DTO>> {

    BoolPropertyValidator(Function<DTO, Boolean> getter) {
        super(getter);
    }

    // ====== isTrueValue ======

    public BoolPropertyValidator<DTO> isTrueValue() {
        return isTrueValue("The value must be true.");
    }

    public BoolPropertyValidator<DTO> isTrueValue(String errMsg) {
        return isTrueValue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrueValue(
            Supplier<E> exceptionCreator) {
        return isTrueValue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrueValue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.TRUE::equals, exceptionCreator);
        return this;
    }

    // ====== isFalseValue ======

    public BoolPropertyValidator<DTO> isFalseValue() {
        return isFalseValue("The value must be false.");
    }

    public BoolPropertyValidator<DTO> isFalseValue(String errMsg) {
        return isFalseValue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalseValue(
            Supplier<E> exceptionCreator) {
        return isFalseValue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalseValue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.FALSE::equals, exceptionCreator);
        return this;
    }

    @Override
    protected BoolPropertyValidator<DTO> thisObject() {
        return this;
    }
}
