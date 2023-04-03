package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.BooleanUtils;

public class BoolValidator<DTO> extends PropertyValidator<DTO, Boolean, BoolValidator<DTO>> {

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
        withRule(BooleanUtils::isTrue, exceptionCreator);
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
        withRule(BooleanUtils::isFalse, exceptionCreator);
        return this;
    }

    @Override
    protected BoolValidator<DTO> thisObject() {
        return this;
    }
}
