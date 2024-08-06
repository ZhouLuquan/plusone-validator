package xyz.zhouxy.plusone.validator.map;

import java.util.Map;

import xyz.zhouxy.plusone.validator.BasePropertyValidator;

public class EntryValidator<K, V>
        extends BasePropertyValidator<Map<K, ? super V>, V, EntryValidator<K, V>> {

    public EntryValidator(K key) {
        super(m -> {
            @SuppressWarnings("unchecked")
            V v = (V) m.get(key);
            return v;
        });
    }

    @Override
    protected EntryValidator<K, V> thisObject() {
        return this;
    }
}
