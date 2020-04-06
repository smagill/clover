package net.kemitix.clover.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TypedProperties {

    private final Map<Class<? extends TypedProperty<?>>, Object> valueMap =
            new HashMap<>();

    public <T> TypedProperties set(
            final Class<? extends TypedProperty<T>> key,
            final T value) {
        valueMap.put(key, value);
        return this;
    }

    public <T> Optional<T> find(
            final Class<? extends TypedProperty<T>> key,
            final Class<T> tClass
    ) {
        return Optional.ofNullable(valueMap.get(key))
                .map(tClass::cast);
    }
}
