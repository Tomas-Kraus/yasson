package org.eclipse.yasson.internal.deserializer.deserializers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.model.ClassModel;

/**
 * JSON object to Java {@code HashMap} deserializer.
 */
class ContainerHashMapFromObject extends ContainerObject<String, Object, Map<String, Object>> {

    /** Current value type (the same for all @code Map} values). */
    private Class<?> valueType;

    /** Target Java {@code HashMap} being built from JSON array. */
    private final HashMap<String, Object> map;

    /**
     * Get new instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param cm Java class model (ignored)
     * @param valueType target Java value type of array elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapFromObject newInstance(ClassModel cm, Class<?> keyType, Class<?> valueType) {
        return new ContainerHashMapFromObject(valueType);
    }

    /**
     * Creates an instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param valueType target Java value type of Collection elements
     */
    private ContainerHashMapFromObject(Class<?> valueType) {
        this.valueType = valueType;
        this.map = new HashMap<>();
    }

    /**
     * Add last parsed JSON value to this {@code Map}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(Object value) {
        map.put(getKey(), value);
        resetKey();
    }

    /**
     * Return target Java {@code Map} value built from already added [key,value] pairs.
     *
     * @param deserialization context
     * @return target Java {@code List} value
     */
    @Override
    public final Map<String, Object> build() {
        return map;
    }

    /**
     * Get current key type.
     *
     * @return current key type as {@code String.class}
     */
    @Override
    public final Class<String> keyType() {
        return String.class;
    }

    /**
     * Get current value type.
     *
     * @return current value type
     */
    @Override
    public final Class<? extends Object> valueType() {
        return valueType;
    }

}
