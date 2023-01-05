package xyz.zhouxy.plusone.validator;

import java.util.function.Function;

public class ObjectValidator<DTO, T> extends PropertyValidator<DTO, T, ObjectValidator<DTO, T>> {

    ObjectValidator(Function<DTO, T> getter) {
        super(getter);
    }

    @Override
    protected ObjectValidator<DTO, T> thisObject() {
        return this;
    }
}
