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
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import xyz.zhouxy.plusone.validator.function.*;

/**
 * 校验器基类
 * <p>
 * 子类可通过添加不同的校验规则，构建完整的校验逻辑，用于校验对象。
 *
 * @param <T> 待校验对象的类型
 * @author ZhouXY
 */
public abstract class BaseValidator<T> implements IValidator<T> {

    private final List<Consumer<? super T>> rules = new ArrayList<>();

    /**
     * 添加一条用于校验整个对象的规则
     *
     * @param condition 校验条件
     * @param errorMessage 异常信息
     */
    protected final void withRule(final Predicate<? super T> condition, final String errorMessage) {
        withRule(condition, () -> ValidationException.withMessage(errorMessage));
    }

    /**
     * 添加一条用于校验整个对象的规则
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionSupplier 自定义异常
     */
    protected final <X extends RuntimeException> void withRule(
            final Predicate<? super T> condition, final Supplier<X> exceptionSupplier) {
        withRule(condition, value -> exceptionSupplier.get());
    }

    /**
     * 添加一条用于校验整个对象的规则
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionFunction 自定义异常
     */
    protected final <X extends RuntimeException> void withRule(
            final Predicate<? super T> condition, final Function<T, X> exceptionFunction) {
        withRule(value -> {
            if (!condition.test(value)) {
                throw exceptionFunction.apply(value);
            }
        });
    }

    /**
     * 添加一条用于校验整个对象的规则
     *
     * @param rule 自定义校验规则
     */
    protected final void withRule(Consumer<? super T> rule) {
        this.rules.add(rule);
    }

    /**
     * 添加一个通用的属性校验器
     *
     * @param <R> 属性类型
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code ObjectPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final <R> ObjectPropertyValidator<T, R> ruleFor(Function<T, R> getter) {
        ObjectPropertyValidator<T, R> validator = new ObjectPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Comparable} 类型的属性校验器
     *
     * @param <R> 属性类型
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code ComparablePropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final <R extends Comparable<R>> ComparablePropertyValidator<T, R> ruleForComparable(
            Function<T, R> getter) {
        ComparablePropertyValidator<T, R> validator = new ComparablePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Integer} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code IntPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final IntPropertyValidator<T> ruleForInt(Function<T, Integer> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Integer} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code IntPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final IntPropertyValidator<T> ruleFor(ToIntegerFunction<T> getter) {
        IntPropertyValidator<T> validator = new IntPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Long} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code LongPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final LongPropertyValidator<T> ruleForLong(Function<T, Long> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Long} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code LongPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final LongPropertyValidator<T> ruleFor(ToLongObjectFunction<T> getter) {
        LongPropertyValidator<T> validator = new LongPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Double} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code DoublePropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final DoublePropertyValidator<T> ruleForDouble(Function<T, Double> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Double} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code DoublePropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final DoublePropertyValidator<T> ruleFor(ToDoubleObjectFunction<T> getter) {
        DoublePropertyValidator<T> validator = new DoublePropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Boolean} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code BoolPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final BoolPropertyValidator<T> ruleForBool(Function<T, Boolean> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code Boolean} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code BoolPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final BoolPropertyValidator<T> ruleFor(ToBoolObjectFunction<T> getter) {
        BoolPropertyValidator<T> validator = new BoolPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code String} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code StringPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final StringPropertyValidator<T> ruleForString(Function<T, String> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验 {@code String} 类型的属性校验器
     *
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code StringPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final StringPropertyValidator<T> ruleFor(ToStringFunction<T> getter) {
        StringPropertyValidator<T> validator = new StringPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验集合类型的属性校验器
     *
     * @param <E> 集合元素类型
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code CollectionPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final <E> CollectionPropertyValidator<T, E> ruleForCollection(Function<T, Collection<E>> getter) {
        CollectionPropertyValidator<T, E> validator = new CollectionPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个用于校验数组类型的属性校验器
     *
     * @param <E> 数组元素类型
     * @param getter 用于从目标对象获取属性值的函数式接口。
     *               示例：{@code Person::getName}。
     * @return {@code ArrayPropertyValidator}。用于添加针对该属性的校验规则。
     */
    protected final <E> ArrayPropertyValidator<T, E> ruleForArray(Function<T, E[]> getter) {
        ArrayPropertyValidator<T, E> validator = new ArrayPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对二元组的校验器
     *
     * @param <V1> 第一个元素的类型
     * @param <V2> 第二个元素的类型
     * @param getter 根据对象构造一个二元组，通常是两个属性的值。
     * @return {@code PairPropertyValidator}。用于添加针对该二元组的校验规则。
     */
    protected final <V1, V2> PairPropertyValidator<T, V1, V2> ruleForPair(Function<T, Entry<V1, V2>> getter) {
        PairPropertyValidator<T, V1, V2> validator = new PairPropertyValidator<>(getter);
        this.rules.add(validator::validate);
        return validator;
    }

    /**
     * 添加一个针对二元组的校验器
     *
     * @param <V1> 第一个元素的类型
     * @param <V2> 第二个元素的类型
     * @param v1Getter 用于从目标对象获取第一个元素的函数式接口。示例：{@code Person::getName1}。
     * @param v2Getter 用于从目标对象获取第二个元素的函数式接口。示例：{@code Person::getName2}。
     * @return {@code PairPropertyValidator}。用于添加针对该二元组的校验规则。
     */
    protected final <V1, V2> PairPropertyValidator<T, V1, V2> ruleForPair(
            Function<T, V1> v1Getter,
            Function<T, V2> v2Getter) {
        PairPropertyValidator<T, V1, V2> validator = new PairPropertyValidator<>(
                t -> new SimpleImmutableEntry<>(v1Getter.apply(t), v2Getter.apply(t)));
        this.rules.add(validator::validate);
        return validator;
    }

    /** {@inheritDoc} */
    @Override
    public void validate(T obj) {
        this.rules.forEach(rule -> rule.accept(obj));
    }
}
