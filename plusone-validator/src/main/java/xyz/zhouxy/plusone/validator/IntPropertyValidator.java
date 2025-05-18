package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class IntPropertyValidator<DTO> extends ComparablePropertyValidator<DTO, Integer, IntPropertyValidator<DTO>> {

    IntPropertyValidator(Function<DTO, Integer> getter) {
        super(getter);
    }

    public IntPropertyValidator<DTO> between(int min, int max) {
        return between(min, max, String.format("数值不在 %d 和 %d 之间", min, max));
    }

    public IntPropertyValidator<DTO> between(int min, int max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<DTO> between(int min, int max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<DTO> between(int min, int max,
            Function<Integer, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected IntPropertyValidator<DTO> thisObject() {
        return this;
    }
}
