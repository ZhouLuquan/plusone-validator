/*
 * Copyright 2022-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.zhouxy.plusone.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import xyz.zhouxy.plusone.validator.function.*;

/**
 * BaseValidator
 *
 * <p>
 * 校验器的基类
 * </p>
 *
 * <p>
 * <b>NOTE: content.</b>
 * </p>
 * @author ZhouXY
 * @since 0.0.1
 */
public abstract class BaseValidator<T> implements IValidator<T> {
    private final List<Consumer<? super T>> rules = new ArrayList<>();

    protected final void withRule(final Predicate<? super T> rule, final String errorMessage) {
        withRule(rule, () -> new IllegalArgumentException(errorMessage));
    }

    protected final <E extends RuntimeException> void withRule(Predicate<? super T> rule, Supplier<E> exceptionBuilder) {
        withRule(rule, value -> exceptionBuilder.get());
    }

    protected final <E extends RuntimeException> void withRule(
            Predicate<? super T> condition, Function<T, E> exceptionBuilder) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw exceptionBuilder.apply(value);
            }
        });
    }

    protected final void withRule(Consumer<? super T> rule) {
        this.rules.add(rule);
    }

    protected final <R> ObjectPropertyValidator<T, R> ruleFor(Function<T, R> getter) {
        ObjectPropertyValidator<T, R> validator = new ObjectPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final <R extends Comparable<R>> ComparablePropertyValidator<T, R> ruleForComparable(Function<T, R> getter) {
        ComparablePropertyValidator<T, R> validator = new ComparablePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final IntPropertyValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final IntPropertyValidator<T> ruleFor(ToIntegerFunction<T> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final LongPropertyValidator<T> ruleForLong(Function<T, Long> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final LongPropertyValidator<T> ruleFor(ToLongObjectFunction<T> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final DoublePropertyValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final DoublePropertyValidator<T> ruleFor(ToDoubleObjectFunction<T> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final BoolPropertyValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final BoolPropertyValidator<T> ruleFor(ToBoolObjectFunction<T> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final StringPropertyValidator<T> ruleForString(Function<T, String> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final StringPropertyValidator<T> ruleFor(ToStringFunction<T> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    protected final <E> CollectionPropertyValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionPropertyValidator<T, E> validator = new CollectionPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    @Override
    public void validate(T obj) {
        this.rules.forEach(rule -> rule.accept(obj));
    }
}
