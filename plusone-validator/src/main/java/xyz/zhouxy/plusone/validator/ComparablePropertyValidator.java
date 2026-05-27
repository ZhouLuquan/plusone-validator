/*
 * Copyright 2025-present ZhouXY
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
 * {@code Comparable} 类型属性的校验器
 *
 * @param <T> 待校验对象的类型
 * @param <TProperty> 待校验属性的类型，必须实现 {@code Comparable} 接口
 * @see com.google.common.collect.Range
 * @author ZhouXY108 <luquanlion@outlook.com>
 */
public class ComparablePropertyValidator<T, TProperty extends Comparable<? super TProperty>>
        extends BaseComparablePropertyValidator<T, TProperty, ComparablePropertyValidator<T, TProperty>> {

    ComparablePropertyValidator(Function<T, ? extends TProperty> getter) {
        super(getter);
    }

    @Override
    protected ComparablePropertyValidator<T, TProperty> thisObject() {
        return this;
    }
}
