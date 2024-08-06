package xyz.zhouxy.plusone.validator.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class MapValidator<K, V> {

    private final List<Consumer<Map<K, V>>> consumers = new LinkedList<>();

    private final Set<K> keys;

    protected MapValidator(K[] keys) {
        this(Arrays.asList(keys));
    }

    protected MapValidator(Collection<K> keys) {
        this.keys = keys.stream().collect(Collectors.toSet());
    }

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

    public final void validate(Map<K, V> obj) {
        this.consumers.forEach(consumer -> consumer.accept(obj));
    }

    @SuppressWarnings("unused")
    protected final <VV extends V> EntryValidator<K, VV> checkValue(K key, Class<VV> clazz) {
        return checkValue(key);
    }

    protected final <VV extends V> EntryValidator<K, VV> checkValue(K key) {
        EntryValidator<K, VV> validator = new EntryValidator<>(key);
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final void withRule(Predicate<? super Map<K, V>> rule, String errMsg) {
        withRule(rule, map -> new IllegalArgumentException(errMsg));
    }

    protected final void withRule(Predicate<? super Map<K, V>> rule, String errMsgFormat, Object... args) {
        withRule(rule, map -> new IllegalArgumentException(String.format(errMsgFormat, args)));
    }

    protected final <E extends RuntimeException> void withRule(Predicate<? super Map<K, V>> rule, Supplier<E> e) {
        withRule(rule, map -> e.get());
    }

    protected final <E extends RuntimeException> void withRule(Predicate<? super Map<K, V>> rule, Function<Map<K, V>, E> e) {
        this.consumers.add(map -> {
            if (!rule.test(map)) {
                throw e.apply(map);
            }
        });
    }
}
