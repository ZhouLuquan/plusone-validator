package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

class BoolValidator<DTO> extends PropertyValidator<DTO, Boolean, BoolValidator<DTO>> {

    BoolValidator(Function<DTO, Boolean> getter) {
        super(getter);
    }

    // ====== isTrue ======

    public BoolValidator<DTO> isTrue() {
        return isTrue("The value must be true.");
    }

    public BoolValidator<DTO> isTrue(String errMsg) {
        return isTrue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolValidator<DTO> isTrue(Supplier<E> exceptionCreator) {
        return isTrue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolValidator<DTO> isTrue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.TRUE::equals, exceptionCreator);
        return this;
    }

    // ====== isFalse ======

    public BoolValidator<DTO> isFalse() {
        return isFalse("The value must be false.");
    }

    public BoolValidator<DTO> isFalse(String errMsg) {
        return isFalse(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolValidator<DTO> isFalse(Supplier<E> exceptionCreator) {
        return isFalse(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolValidator<DTO> isFalse(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.FALSE::equals, exceptionCreator);
        return this;
    }
    
    @Override
    protected BoolValidator<DTO> returnThis() {
        return this;
    }
}
