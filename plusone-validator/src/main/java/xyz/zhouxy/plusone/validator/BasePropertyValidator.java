/*
 * Copyright 2024-2025 the original author or authors.
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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class BasePropertyValidator< //
        TObj, //
        TProperty, //
        TPropertyValidator extends BasePropertyValidator<TObj, TProperty, TPropertyValidator>> {

    private final Function<TObj, ? extends TProperty> getter;

    private final List<Consumer<? super TProperty>> consumers = new LinkedList<>();

    protected BasePropertyValidator(Function<TObj, ? extends TProperty> getter) {
        this.getter = getter;
    }

    public final TPropertyValidator withRule(Predicate<? super TProperty> rule) {
        return withRule(rule, v -> new IllegalArgumentException());
    }

    public final TPropertyValidator withRule(
            Predicate<? super TProperty> rule, String errMsg) {
        return withRule(rule, convertExceptionCreator(errMsg));
    }

    public final <E extends RuntimeException> TPropertyValidator withRule(
            Predicate<? super TProperty> rule, Supplier<E> e) {
        return withRule(rule, convertExceptionCreator(e));
    }

    public final <E extends RuntimeException> TPropertyValidator withRule(
            Predicate<? super TProperty> rule, Function<TProperty, E> e) {
        this.consumers.add(v -> {
            if (!rule.test(v)) {
                throw e.apply(v);
            }
        });
        return thisObject();
    }

    public final <T extends TObj> void validate(T obj) {
        for (Consumer<? super TProperty> consumer : consumers) {
            consumer.accept(getter.apply(obj));
        }
    }

    protected abstract TPropertyValidator thisObject();

    // ====================
    // ====== Object ======
    // ====================

    // ====== notNull =====

    public TPropertyValidator notNull() {
        return notNull("Value could not be null.");
    }

    public TPropertyValidator notNull(String errMsg) {
        return notNull(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> TPropertyValidator notNull(Supplier<E> exceptionCreator) {
        return notNull(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> TPropertyValidator notNull(Function<TProperty, E> exceptionCreator) {
        withRule(Objects::nonNull, exceptionCreator);
        return thisObject();
    }

    // ====== isNull =====

    public TPropertyValidator isNull(String errMsg) {
        return isNull(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> TPropertyValidator isNull(Supplier<E> exceptionCreator) {
        return isNull(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> TPropertyValidator isNull(Function<TProperty, E> exceptionCreator) {
        withRule(Objects::isNull, exceptionCreator);
        return thisObject();
    }

    // ===== equals =====

    public TPropertyValidator equalsThat(Object that) {
        return equalsThat(that, value -> new IllegalArgumentException(String.format("(%s) 必须与 (%s) 相等", value, that)));
    }

    public TPropertyValidator equalsThat(Object that, String errMsg) {
        return equalsThat(that, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> TPropertyValidator equalsThat(
            Object that, Supplier<E> exceptionCreator) {
        return equalsThat(that, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> TPropertyValidator equalsThat(
            Object that, Function<TProperty, E> exceptionCreator) {
        withRule(value -> Objects.equals(value, that), exceptionCreator);
        return thisObject();
    }

    // ===== must =====

    public TPropertyValidator must(Predicate<? super TProperty> condition) {
        return must(condition, "无效的用户输入");
    }

    public TPropertyValidator must(Predicate<? super TProperty> condition, String errMsg) {
        return must(condition, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> TPropertyValidator must(
            Predicate<? super TProperty> condition,
            Supplier<E> exceptionCreator) {
        return must(condition, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> TPropertyValidator must(
            Predicate<? super TProperty> condition,
            Function<TProperty, E> exceptionCreator) {
        withRule(condition, exceptionCreator);
        return thisObject();
    }

    // ===== must =====

    public TPropertyValidator must(Collection<Predicate<? super TProperty>> conditions) {
        return must(conditions, "无效的用户输入");
    }

    public TPropertyValidator must(Collection<Predicate<? super TProperty>> conditions, String errMsg) {
        return must(conditions, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> TPropertyValidator must(
            Collection<Predicate<? super TProperty>> conditions,
            Supplier<E> exceptionCreator) {
        return must(conditions, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> TPropertyValidator must(
            Collection<Predicate<? super TProperty>> conditions,
            Function<TProperty, E> exceptionCreator) {
        for (Predicate<? super TProperty> condition : conditions) {
            withRule(condition, exceptionCreator);
        }
        return thisObject();
    }

    static <V> Function<V, IllegalArgumentException> convertExceptionCreator(String errMsg) {
        return value -> new IllegalArgumentException(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
}
