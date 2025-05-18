package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;

public class CollectionPropertyValidator<DTO, T>
        extends BasePropertyValidator<DTO, Collection<T>, CollectionPropertyValidator<DTO, T>> {

    CollectionPropertyValidator(Function<DTO, Collection<T>> getter) {
        super(getter);
    }

    // ====== notEmpty =====

    public CollectionPropertyValidator<DTO, T> notEmpty(String errMsg) {
        return notEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<DTO, T> notEmpty(Supplier<E> exceptionCreator) {
        return notEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<DTO, T> notEmpty(
            Function<Collection<T>, E> exceptionCreator) {
        withRule(CollectionTools::isNotEmpty, exceptionCreator);
        return this;
    }

    // ====== isEmpty =====

    public CollectionPropertyValidator<DTO, T> isEmpty(String errMsg) {
        return isEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<DTO, T> isEmpty(Supplier<E> exceptionCreator) {
        return isEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<DTO, T> isEmpty(
            Function<Collection<T>, E> exceptionCreator) {
        withRule(CollectionTools::isEmpty, exceptionCreator);
        return this;
    }

    @Override
    protected CollectionPropertyValidator<DTO, T> thisObject() {
        return this;
    }
}
