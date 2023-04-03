package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;

public class CollectionValidator<DTO, T> extends PropertyValidator<DTO, Collection<T>, CollectionValidator<DTO, T>> {

    CollectionValidator(Function<DTO, Collection<T>> getter) {
        super(getter);
    }

    // ====== notEmpty =====

    public CollectionValidator<DTO, T> notEmpty(String errMsg) {
        return notEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionValidator<DTO, T> notEmpty(Supplier<E> exceptionCreator) {
        return notEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionValidator<DTO, T> notEmpty(
            Function<Collection<T>, E> exceptionCreator) {
        withRule(CollectionUtils::isNotEmpty, exceptionCreator);
        return this;
    }

    // ====== isEmpty =====

    public CollectionValidator<DTO, T> isEmpty(String errMsg) {
        return isEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionValidator<DTO, T> isEmpty(Supplier<E> exceptionCreator) {
        return isEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionValidator<DTO, T> isEmpty(
            Function<Collection<T>, E> exceptionCreator) {
        withRule(CollectionUtils::isEmpty, exceptionCreator);
        return this;
    }

    @Override
    protected CollectionValidator<DTO, T> thisObject() {
        return this;
    }
}
