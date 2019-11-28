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

import org.eclipse.yasson.internal.RuntimeTypeInfo;
import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Container deserializer.
 * <p>
 * Deserializer of complex <i>JSON array</i> type and base for <i>JSON object</i> deserializer.
 * <p>
 * Deserialization of complex types is done in several steps: Each element of <i>JSON array</i> or each [key,value] pair
 * of <i>JSON object</i> is added separately.
 *
 * @param <V> the type of container value
 * @param <T> the type of returned value
 */
public abstract class ContainerArray<V, T> implements RuntimeTypeInfo {

    /** Deserialization context. */
    private ParserContext uCtx;
    /** Container type. */
    private Type type;
    /** Parent container or {@code null} if no parent exists. */
    private ContainerArray<?, ?> parent;

    /**
     * Notification about beginning of container deserialization.
     *
     * @param uCtx deserialization context
     * @param type container type
     * @param parent parent container or {@code null} if no parent exists
     */
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        this.uCtx = uCtx;
        this.type = type;
        this.parent = parent;
    }

    /**
     * Notification about end of container deserialization.
     * Finalize building of value to be returned.
     *
     * @return value to be returned
     */
    public abstract T build();


    /**
     * Add last parsed JSON value to this container.
     * In <i>JSON object</i> deserialization {@code setKey(String)} is always called before this method by parser
     * for specific [key,value] pair.
     *
     * @param value value already converted to target type
     */
    public abstract void addValue(V value);


    /**
     * Get current value type.
     *
     * @return current value type
     */
    public abstract Type valueType();

    /**
     * Get current deserialization context.
     *
     * @return current deserialization context
     */
    ParserContext getContext() {
        return uCtx;
    }

    /**
     * Get container type.
     *
     * @return container type
     */
    @Override
    public Type getRuntimeType() {
        return type;
    }

    /**
     * Get parent container if exists.
     *
     * @return parent container or {@code null} if no parent container exists
     */
    @Override
    public ContainerArray<?, ?> getWrapper() {
        return parent;
    }

    /**
     * Get {@link ContainerObject} instance of this class if implemented.
     *
     * @return {@link ContainerObject} instance of this class if implemented.
     */
    public ContainerObject<Object, Object, Object> object() {
        throw new UnsupportedOperationException("This instance does not implement ContainerObject.");
    }

}
