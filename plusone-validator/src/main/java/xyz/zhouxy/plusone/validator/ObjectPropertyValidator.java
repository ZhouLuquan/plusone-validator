package xyz.zhouxy.plusone.validator;

import java.util.function.Function;

public class ObjectPropertyValidator<DTO, T> extends BasePropertyValidator<DTO, T, ObjectPropertyValidator<DTO, T>> {

    ObjectPropertyValidator(Function<DTO, T> getter) {
        super(getter);
    }

    @Override
    protected ObjectPropertyValidator<DTO, T> thisObject() {
        return this;
    }
}
