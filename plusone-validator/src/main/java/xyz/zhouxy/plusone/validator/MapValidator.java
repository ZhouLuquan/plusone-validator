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

public abstract class MapValidator<K, V> extends BaseValidator<Map<K, V>> {

    private final Set<K> keys;

    protected MapValidator(K[] keys) {
        this(Arrays.asList(keys));
    }

    protected MapValidator(Collection<K> keys) {
        this.keys = keys.stream().collect(Collectors.toSet());
    }

    // ========== validate & validateAndCopy ==========

    public final Map<K, V> validateAndCopy(Map<K, V> obj) {
        return validateAndCopyInternal(obj, this.keys);
    }

    public final Map<K, V> validateAndCopy(Map<K, V> obj, Collection<K> keys) {
        return validateAndCopyInternal(obj, keys);
    }

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

    // ========== ruleFor ==========

    protected final ObjectPropertyValidator<Map<K, V>, V> ruleFor(K key) {
        return ruleFor(m -> m.get(key));
    }

    protected final IntPropertyValidator<Map<K, V>> ruleForInt(K key) {
        return ruleForInt(m -> (Integer) m.get(key));
    }

    protected final LongPropertyValidator<Map<K, V>> ruleForLong(K key) {
        return ruleForLong(m -> (Long) m.get(key));
    }

    protected final DoublePropertyValidator<Map<K, V>> ruleForDouble(K key) {
        return ruleForDouble(m -> (Double) m.get(key));
    }

    protected final BoolPropertyValidator<Map<K, V>> ruleForBool(K key) {
        return ruleForBool(m -> (Boolean) m.get(key));
    }

    protected final StringPropertyValidator<Map<K, V>> ruleForString(K key) {
        return ruleForString(m -> (String) m.get(key));
    }

    protected final <E> CollectionPropertyValidator<Map<K, V>, E> ruleForCollection(K key) {
        @SuppressWarnings("unchecked")
        Function<Map<K, V>, Collection<E>> getter = m -> (Collection<E>) m.get(key);
        return ruleForCollection(getter);
    }
}
