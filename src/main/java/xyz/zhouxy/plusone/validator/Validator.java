package xyz.zhouxy.plusone.validator;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 校验器
 *
 * <p>
 * 可以使用以下方式初始化一个校验器：
 * </p>
 *
 * <pre>
 * var validator = new Validator&lt;Integer&gt;()
 *         .addRule(value -> Objects.nonNull(value), "value 不能为空")
 *         .addRule(value -> (value >= 0 && value <= 500), "value 应在 [0, 500] 内");
 * </pre>
 *
 * <p>
 * 然后通过校验器的 {@link #validate} 方法，或
 * {@link ValidateUtil#validate(Object, Validator)} 对指定对象进行校验。
 * </p>
 *
 * <pre>
 * validator.validate(666);
 * </pre>
 *
 * <pre>
 * ValidateUtil.validate(255, validator);
 * </pre>
 * </p>
 *
 * @author <a href="https://gitee.com/zhouxy108">ZhouXY</a>
 * @see IValidateRequired
 * @see ValidateUtil
 * @see BaseValidator
 */
public final class Validator<T> extends BaseValidator<T> {
    public final Validator<T> addRule(final Predicate<T> rule, final String errorMessage) {
        withRule(rule, errorMessage);
        return this;
    }

    public final <E extends RuntimeException> Validator<T> addRule(Predicate<T> rule, Supplier<E> exceptionCreator) {
        withRule(rule, exceptionCreator);
        return this;
    }
    
    public final <E extends RuntimeException> Validator<T> addRule(Predicate<T> rule, Function<T, E> exceptionCreator) {
        withRule(rule, exceptionCreator);
        return this;
    }

    public final Validator<T> addRule(Consumer<T> rule) {
        withRule(rule);
        return this;
    }
}
