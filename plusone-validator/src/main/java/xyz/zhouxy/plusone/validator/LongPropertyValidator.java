package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

public class LongPropertyValidator<DTO> extends ComparablePropertyValidator<DTO, Long, LongPropertyValidator<DTO>> {

    LongPropertyValidator(Function<DTO, Long> getter) {
        super(getter);
    }

    public LongPropertyValidator<DTO> between(long min, long max) {
        return between(min, max, String.format("数值不在 %d 和 %d 之间", min, max));
    }

    public LongPropertyValidator<DTO> between(long min, long max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<DTO> between(long min, long max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<DTO> between(long min, long max,
            Function<Long, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected LongPropertyValidator<DTO> thisObject() {
        return this;
    }
}
