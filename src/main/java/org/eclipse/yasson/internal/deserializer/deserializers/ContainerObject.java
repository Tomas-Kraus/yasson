/*******************************************************************************
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * Tomas Kraus
 ******************************************************************************/
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

    /**
     * Interface to set type of Map key during container initialization.  
     */
    public interface MapKey {
        /**
         * Set key type.
         * This container is inicialized as an array container so key type must be passed later.
         *
         * @param keyType key type to set.
         */
        void setKeyType(Type keyType);

    }

    /** Key of current JSON object value being parsed. */
    private K key;

    /**
     * Creates an instance of container with key to value mapping deserializer.
     *
     * @param containerClass class of the container
     */
    ContainerObject(final Class<T> containerClass) {
        super(containerClass);
    }

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
