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
 * 校验器的基类
 *
 * <p>
 * 通过继承 {@code BaseValidator}，可以自定义一个针对特定类型的校验器，包含对该类型的校验逻辑。
 *
 * @author ZhouXY
 */
public abstract class BaseValidator<T> implements IValidator<T> {

    /**
     * 规则集合
     */
    private final List<Consumer<? super T>> rules = new ArrayList<>();

    /**
     * 添加一个校验规则
     *
     * @param rule 校验规则
     * @param errorMessage 错误信息
     */
    protected final void withRule(final Predicate<? super T> rule, final String errorMessage) {
        withRule(rule, () -> new IllegalArgumentException(errorMessage));
    }

    /**
     * 添加一个校验规则
     *
     * @param <E> 自定义异常类型
     * @param rule 校验规则
     * @param e 自定义异常
     */
    protected final <E extends RuntimeException> void withRule(
            final Predicate<? super T> rule, final Supplier<E> e) {
        withRule(rule, value -> e.get());
    }

    /**
     * 添加一个校验规则
     *
     * @param <E> 自定义异常类型
     * @param condition 校验条件
     * @param e 自定义异常
     */
    protected final <E extends RuntimeException> void withRule(
            final Predicate<? super T> condition, final Function<T, E> e) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw e.apply(value);
            }
        });
    }

    /**
     * 添加一个校验规则
     *
     * @param rule 校验规则。内部包含断言条件，如果条件不满足，则抛出异常。
     */
    protected final void withRule(Consumer<? super T> rule) {
        this.rules.add(rule);
    }

    /**
     * 添加一个属性校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final <R> ObjectPropertyValidator<T, R> ruleFor(Function<T, R> getter) {
        ObjectPropertyValidator<T, R> validator = new ObjectPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Comparable} 属性的校验器
     *
     * @param <R> 属性类型
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final <R extends Comparable<R>> ComparablePropertyValidator<T, R> ruleForComparable(
            Function<T, R> getter) {
        ComparablePropertyValidator<T, R> validator = new ComparablePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Integer} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final IntPropertyValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Integer} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final IntPropertyValidator<T> ruleFor(ToIntegerFunction<T> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Long} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final LongPropertyValidator<T> ruleForLong(Function<T, Long> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Long} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final LongPropertyValidator<T> ruleFor(ToLongObjectFunction<T> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Double} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final DoublePropertyValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Double} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final DoublePropertyValidator<T> ruleFor(ToDoubleObjectFunction<T> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Boolean} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final BoolPropertyValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Boolean} 属性的校验器
     *
     * @param getter 属性获取函数
     * @return 属性校验器
     */
    protected final BoolPropertyValidator<T> ruleFor(ToBoolObjectFunction<T> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code String} 属性的校验器
     *
     * @param getter 获取属性值的函数
     * @return 属性校验器
     */
    protected final StringPropertyValidator<T> ruleForString(Function<T, String> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code String} 属性的校验器
     *
     * @param getter 获取属性值的函数
     * @return 属性校验器
     */
    protected final StringPropertyValidator<T> ruleFor(ToStringFunction<T> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对 {@code Collection} 属性的校验器
     *
     * @param getter 获取属性值的函数
     * @return 集合属性校验器
     */
    protected final <E> CollectionPropertyValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionPropertyValidator<T, E> validator = new CollectionPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /** {@inheritDoc} */
    @Override
    public void validate(T obj) {
        this.rules.forEach(rule -> rule.accept(obj));
    }
}
