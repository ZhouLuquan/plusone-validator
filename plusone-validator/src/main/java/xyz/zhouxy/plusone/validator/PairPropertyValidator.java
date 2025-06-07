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

import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 针对二元组的属性校验器
 *
 * @param <T>  被验证对象类型
 * @param <V1> 第一个元素的类型
 * @param <V2> 第二个元素的类型
 * @author ZhouXY
 */
public class PairPropertyValidator<T, V1, V2>
        extends BasePropertyValidator<T, Entry<V1, V2>, PairPropertyValidator<T, V1, V2>> {

    protected PairPropertyValidator(Function<T, ? extends Entry<V1, V2>> getter) {
        super(getter);
    }

    /**
     * 添加一条校验属性的规则，校验二元组是否满足给定的条件
     *
     * @param condition 校验条件
     * @return 属性校验器
     */
    public final PairPropertyValidator<T, V1, V2> must(BiPredicate<V1, V2> condition) {
        return must(pair -> condition.test(pair.getKey(), pair.getValue()));
    }

    /**
     * 添加一条校验属性的规则，校验二元组是否满足给定的条件
     *
     * @param condition 校验条件
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final PairPropertyValidator<T, V1, V2> must(BiPredicate<V1, V2> condition, String errorMessage) {
        return must(pair -> condition.test(pair.getKey(), pair.getValue()), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验二元组是否满足给定的条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> PairPropertyValidator<T, V1, V2> must(
            BiPredicate<V1, V2> condition, Supplier<X> exceptionSupplier) {
        return must(pair -> condition.test(pair.getKey(), pair.getValue()), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验二元组是否满足给定的条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> PairPropertyValidator<T, V1, V2> must(
            BiPredicate<V1, V2> condition, BiFunction<V1, V2, X> exceptionFunction) {
        return must(pair -> condition.test(pair.getKey(), pair.getValue()),
                pair -> exceptionFunction.apply(pair.getKey(), pair.getValue()));
    }

    @Override
    protected PairPropertyValidator<T, V1, V2> thisObject() {
        return this;
    }
}
