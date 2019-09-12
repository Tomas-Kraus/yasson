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
import java.util.Collection;

/**
 * JSON array to Java Collection deserializer.
 *
 * @param <V> the type of Collection value returned by primitive type deserializer
 */
public abstract class ContainerCollectionFromArray<V> extends Container<Void, V, Collection<V>> {

    /** Current value type (the same for all Collection elements). */
    private final Class<V> valueType;

    /**
     * Creates an instance of JSON array to Java Collection deserializer.
     *
     * @param valueType target Java value type of Collection elements
     */
    @SuppressWarnings("unchecked")
    ContainerCollectionFromArray(Class<?> valueType) {
        this.valueType = (Class<V>) valueType;
    }

    /**
     * Get current value type.
     *
     * @return current value type
     */
    @Override
    public final Type valueType() {
        return valueType;
    }

    /**
     * Attempt to get current key type throws an exception for JSON array.
     *
     * @return never returns current key type
     */
    @Override
    public final Type keyType() {
        throw new IllegalStateException("Key type is not supported in array containers");
    }

}
