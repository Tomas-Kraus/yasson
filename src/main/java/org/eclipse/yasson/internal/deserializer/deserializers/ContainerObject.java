package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;

/**
 * Container with key to value mapping deserializer.
 * <p>
 * Deserializer of complex <i>JSON object</i> type.
 * <p>
 * Deserialization of complex types is done in several steps: Each element of each [key,value] pair
 * of <i>JSON object</i> is added separately.
 *
 * @param <K> the type of container key
 * @param <V> the type of container value
 * @param <T> the type of returned value
 */
public abstract class ContainerObject<K, V, T> extends ContainerArray<V, T> {

    /** Key of current JSON object value being parsed. */
    private K key;

    /**
     * Set key of next JSON object value to be added to the container.
     *
     * @param key key of next JSON value (already converted to target key type)
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Reset current JSON object key name.
     */
    void resetKey() {
        key = null;
    }

    /**
     * Get key name of current JSON object value being parsed.
     *
     * @return key of current JSON object value
     */
    K getKey() {
        return key;
    }

    /**
     * Get current key type.
     *
     * @return current key type
     */
    public abstract Type keyType();

    /**
     * Get {@link ContainerObject} instance of this class if implemented.
     *
     * @return {@link ContainerObject} instance of this class if implemented.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ContainerObject<Object, Object, Object> object() {
        return (ContainerObject<Object, Object, Object>) this;
    }

}
