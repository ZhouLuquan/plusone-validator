package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class LongValidator<DTO> extends ValidatorOfComparable<DTO, Long, LongValidator<DTO>> {

    LongValidator(Function<DTO, Long> getter) {
        super(getter);
    }

    public LongValidator<DTO> between(long min, long max) {
        return between(min, max, String.format("数值不在 %d 和 %d 之间", min, max));
    }

    public LongValidator<DTO> between(long min, long max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongValidator<DTO> between(long min, long max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongValidator<DTO> between(long min, long max,
            Function<Long, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected LongValidator<DTO> thisObject() {
        return this;
    }
}
