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
