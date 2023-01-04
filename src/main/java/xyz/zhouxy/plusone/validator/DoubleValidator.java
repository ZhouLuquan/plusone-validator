package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoubleValidator<DTO> extends PropertyValidator<DTO, Double, DoubleValidator<DTO>> {

    DoubleValidator(Function<DTO, Double> getter) {
        super(getter);
    }

    public DoubleValidator<DTO> between(double min, double max) {
        return between(min, max, String.format("数值不在 %s 和 %s 之间", String.valueOf(min), String.valueOf(max)));
    }

    public DoubleValidator<DTO> between(double min, double max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoubleValidator<DTO> between(double min, double max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoubleValidator<DTO> between(double min, double max,
            Function<Double, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected DoubleValidator<DTO> returnThis() {
        return this;
    }
}
