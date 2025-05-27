/*
 * Copyright 2023-2025 the original author or authors.
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
import java.util.function.Function;
import java.util.function.Supplier;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;

public class CollectionPropertyValidator<T, TElement>
        extends BasePropertyValidator<T, Collection<TElement>, CollectionPropertyValidator<T, TElement>> {

    CollectionPropertyValidator(Function<T, Collection<TElement>> getter) {
        super(getter);
    }

    // ====== notEmpty =====

    public CollectionPropertyValidator<T, TElement> notEmpty() {
        return notEmpty("The input must not be empty.");
    }

    public CollectionPropertyValidator<T, TElement> notEmpty(String errMsg) {
        return notEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<T, TElement> notEmpty(
            Supplier<E> exceptionCreator) {
        return notEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<T, TElement> notEmpty(
            Function<Collection<TElement>, E> exceptionCreator) {
        withRule(CollectionTools::isNotEmpty, exceptionCreator);
        return this;
    }

    // ====== isEmpty =====

    public CollectionPropertyValidator<T, TElement> isEmpty() {
        return isEmpty("The input must be empty.");
    }

    public CollectionPropertyValidator<T, TElement> isEmpty(String errMsg) {
        return isEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<T, TElement> isEmpty(
            Supplier<E> exceptionCreator) {
        return isEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> CollectionPropertyValidator<T, TElement> isEmpty(
            Function<Collection<TElement>, E> exceptionCreator) {
        withRule(CollectionTools::isEmpty, exceptionCreator);
        return this;
    }

    @Override
    protected CollectionPropertyValidator<T, TElement> thisObject() {
        return this;
    }
}
