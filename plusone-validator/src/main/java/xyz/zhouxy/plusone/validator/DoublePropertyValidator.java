package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoublePropertyValidator<DTO> extends ComparablePropertyValidator<DTO, Double, DoublePropertyValidator<DTO>> {

    DoublePropertyValidator(Function<DTO, Double> getter) {
        super(getter);
    }

    public DoublePropertyValidator<DTO> between(double min, double max) {
        return between(min, max, String.format("数值不在 %s 和 %s 之间", String.valueOf(min), String.valueOf(max)));
    }

    public DoublePropertyValidator<DTO> between(double min, double max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> between(double min, double max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> between(double min, double max,
            Function<Double, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected DoublePropertyValidator<DTO> thisObject() {
        return this;
    }
}
