/*******************************************************************************
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
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
 * Container deserializer.
 * <p>
 * Deserializer of complex JSON types: <i>JSON object</i> and <i>JSON array</i>.
 * <p>
 * Deserialization of complex types is done in several steps: Each element of <i>JSON array</i> or each [key,value] pair
 * of <i>JSON object</i> is added separately.
 *
 * @param <K> the type of container key (only for <i>JSON object</i>)
 * @param <V> the type of container value
 * @param <T> the type of returned value
 */
public abstract class Container<K, V, T> extends Deserializer<T> {

    /** Key of current JSON object value being parsed. Makes no sense for <i>JSON array</i> deserialization. */
    private K key;

    /**
     * Set key of next JSON object value to be added to the container.
     * Used only when <i>JSON object</i> is being deserialized. Makes no sense for <i>JSON array</i> deserialization
     * where only unnamed values are stored.
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
     * Add last parsed JSON value to this container.
     * In <i>JSON object</i> deserialization {@code setKey(String)} is always called before this method by parser
     * for specific [key,value] pair.
     *
     * @param value value already converted to target type
     */
    public abstract void addValue(V value);

    /**
     * Get current key type.
     *
     * @return current key type
     */
    public abstract Type keyType();

    /**
     * Get current value type.
     *
     * @return current value type
     */
    public abstract Type valueType();

}
