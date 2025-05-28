/*
 * Copyright 2025 the original author or authors.
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

import java.util.function.Function;

/**
 * 针对 {@code Comparable} 类型的默认属性校验器
 *
 * <p>
 * 继承自 {@link BaseComparablePropertyValidator}，内置了判断属性是否在给定区间内的校验规则。
 *
 * @param <T> 待校验对象类型
 * @param <TProperty> 属性类型
 * @param <TPropertyValidator> 当前属性校验器类型，用于链式调用
 * @see Range
 * @author ZhouXY
 */
public class ComparablePropertyValidator<T, TProperty extends Comparable<TProperty>>
        extends BaseComparablePropertyValidator<T, TProperty, ComparablePropertyValidator<T, TProperty>> {

    ComparablePropertyValidator(Function<T, ? extends TProperty> getter) {
        super(getter);
    }

    @Override
    protected ComparablePropertyValidator<T, TProperty> thisObject() {
        return this;
    }
}
