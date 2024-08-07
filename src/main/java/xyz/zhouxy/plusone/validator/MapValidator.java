package xyz.zhouxy.plusone.validator;

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

    public final void validate(Map<K, V> obj) {
        this.consumers.forEach(consumer -> consumer.accept(obj));
    }

    // ========== ruleFor ==========

    protected final ObjectValidator<Map<K, V>, V> ruleFor(K key) {
        ObjectValidator<Map<K, V>, V> validator = new ObjectValidator<>(m -> m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final IntValidator<Map<K, V>> ruleForInt(K key) {
        IntValidator<Map<K, V>> validator = new IntValidator<>(m -> (Integer) m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final DoubleValidator<Map<K, V>> ruleForDouble(K key) {
        DoubleValidator<Map<K, V>> validator = new DoubleValidator<>(m -> (Double) m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final BoolValidator<Map<K, V>> ruleForBool(K key) {
        BoolValidator<Map<K, V>> validator = new BoolValidator<>(m -> (Boolean) m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final StringValidator<Map<K, V>> ruleForString(K key) {
        StringValidator<Map<K, V>> validator = new StringValidator<>(m -> (String) m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    protected final <E> CollectionValidator<Map<K, V>, E> ruleForCollection(K key) {
        @SuppressWarnings("unchecked")
        CollectionValidator<Map<K, V>, E> validator = new CollectionValidator<>(m -> (Collection<E>) m.get(key));
        this.consumers.add(validator::validate);
        return validator;
    }

    // ========== withRule ==========

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
