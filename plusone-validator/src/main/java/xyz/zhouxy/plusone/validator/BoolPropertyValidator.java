package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class BoolPropertyValidator<DTO> extends BasePropertyValidator<DTO, Boolean, BoolPropertyValidator<DTO>> {

    BoolPropertyValidator(Function<DTO, Boolean> getter) {
        super(getter);
    }

    // ====== isTrue ======

    public BoolPropertyValidator<DTO> isTrue() {
        return isTrue("The value must be true.");
    }

    public BoolPropertyValidator<DTO> isTrue(String errMsg) {
        return isTrue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrue(Supplier<E> exceptionCreator) {
        return isTrue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.TRUE::equals, exceptionCreator);
        return this;
    }

    // ====== isFalse ======

    public BoolPropertyValidator<DTO> isFalse() {
        return isFalse("The value must be false.");
    }

    public BoolPropertyValidator<DTO> isFalse(String errMsg) {
        return isFalse(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalse(Supplier<E> exceptionCreator) {
        return isFalse(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalse(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.FALSE::equals, exceptionCreator);
        return this;
    }

    @Override
    protected BoolPropertyValidator<DTO> thisObject() {
        return this;
    }
}
