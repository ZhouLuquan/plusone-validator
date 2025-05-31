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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对 Map 进行校验的校验器
 *
 * <p>
 * 校验后拷贝出一个新的 Map 对象，仅保留指定的 key。
 *
 * @author ZhouXY
 */
public abstract class MapValidator<K, V> extends BaseValidator<Map<K, V>> {

    private final Set<K> keys;

    /**
     * 构造函数
     *
     * @param keys 要保留的 key 的集合
     */
    protected MapValidator(K[] keys) {
        this(Arrays.asList(keys));
    }

    /**
     * 构造函数
     * @param keys 要保留的 key 的集合
     */
    protected MapValidator(Collection<K> keys) {
        this.keys = keys.stream().collect(Collectors.toSet());
    }

    // ================================
    // #region - validateAndCopy
    // ================================

    /**
     * 校验并拷贝，仅保留指定 key 的属性。
     *
     * @param obj 待校验的 map
     * @return 拷贝后的 map
     */
    public final Map<K, V> validateAndCopy(Map<K, V> obj) {
        return validateAndCopyInternal(obj, this.keys);
    }

    /**
     * 校验并拷贝，仅保留指定 key 的属性。
     *
     * @param obj 待校验的 map
     * @param keys 指定 key
     * @return 拷贝后的 map
     */
    public final Map<K, V> validateAndCopy(Map<K, V> obj, Collection<K> keys) {
        return validateAndCopyInternal(obj, keys);
    }

    /**
     * 校验并拷贝，仅保留指定 key 的属性。
     *
     * @param obj 待校验的 map
     * @param keys 待校验的 key
     * @return 拷贝后的 map
     */
    @SafeVarargs
    public final Map<K, V> validateAndCopy(Map<K, V> obj, K... keys) {
        return validateAndCopyInternal(obj, Arrays.asList(keys));
    }

    private final Map<K, V> validateAndCopyInternal(Map<K, V> obj, Collection<K> keys) {
        validate(obj);
        return obj.entrySet().stream()
                .filter(kv -> keys.contains(kv.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // ================================
    // #endregion - validateAndCopy
    // ================================

    // ================================
    // #region - ruleFor
    // ================================

    /**
     * 添加一个属性校验器，对指定 key 对应的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final <T extends V> ObjectPropertyValidator<Map<K, V>, T> ruleFor(K key) {
        @SuppressWarnings("unchecked")
        final Function<Map<K, V>, T> func = m -> (T) m.get(key);
        return super.<T>ruleFor(func);
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code Integer} 类型的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final IntPropertyValidator<Map<K, V>> ruleForInt(K key) {
        return ruleForInt(m -> (Integer) m.get(key));
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code Long} 类型的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final LongPropertyValidator<Map<K, V>> ruleForLong(K key) {
        return ruleForLong(m -> (Long) m.get(key));
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code Double} 类型的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final DoublePropertyValidator<Map<K, V>> ruleForDouble(K key) {
        return ruleForDouble(m -> (Double) m.get(key));
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code Boolean} 类型的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final BoolPropertyValidator<Map<K, V>> ruleForBool(K key) {
        return ruleForBool(m -> (Boolean) m.get(key));
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code String} 类型的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final StringPropertyValidator<Map<K, V>> ruleForString(K key) {
        return ruleForString(m -> (String) m.get(key));
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的 {@code Comparable} 的 value 进行校验
     *
     * @param key key
     * @return 属性校验器
     */
    protected final <E extends Comparable<E>> ComparablePropertyValidator<Map<K, V>, E> ruleForComparable(K key) {
        @SuppressWarnings("unchecked")
        Function<Map<K, V>, E> getter = m -> (E) m.get(key);
        return ruleForComparable(getter);
    }

    /**
     * 添加一个属性校验器，对指定 key 对应的集合类型的 value 进行校验
     *
     * @param <E> 集合元素的类型
     * @param key key
     * @return 属性校验器
     */
    protected final <E> CollectionPropertyValidator<Map<K, V>, E> ruleForCollection(K key) {
        @SuppressWarnings("unchecked")
        Function<Map<K, V>, Collection<E>> getter = m -> (Collection<E>) m.get(key);
        return ruleForCollection(getter);
    }

    // ================================
    // #endregion - ruleFor
    // ================================
}
