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

import java.util.function.Function;

/**
 * 通用类型属性校验器。继承自 {@link BasePropertyValidator}，包含针对属性的校验规则。
 *
 * @param <T> 待校验对象的类型
 * @param <TProperty> 待校验属性的类型
 * @author ZhouXY
 */
public class ObjectPropertyValidator<T, TProperty>
        extends BasePropertyValidator<T, TProperty, ObjectPropertyValidator<T, TProperty>> {

    ObjectPropertyValidator(Function<T, TProperty> getter) {
        super(getter);
    }

    @Override
    protected ObjectPropertyValidator<T, TProperty> thisObject() {
        return this;
    }
}
