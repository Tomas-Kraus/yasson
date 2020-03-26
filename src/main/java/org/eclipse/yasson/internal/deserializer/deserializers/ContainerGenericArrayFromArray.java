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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * JSON array to generic array deserializer.
 *
 * @param <V> the type of array value returned by primitive type deserializer
 */
public class ContainerGenericArrayFromArray<V> extends ContainerArrayFromArray<V, V[]> {

    /**
     * Generic type of array component.
     */
    public static final class ComponentType implements GenericArrayType {

        private final Type component;

        /**
         * Creates an instance of generic type of array component.
         *
         * @param component type of array component
         */
        public ComponentType(final Type component) {
            this.component = component;
        }
        @Override
        public Type getGenericComponentType() {
            return component;
        }
        
    }

    /**
     * Get new instance of JSON array to byte array deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of array elements
     * @return new instance of JSON array to byte array deserializer
     */
    static final <V> ContainerGenericArrayFromArray<V>
    newInstance(final Class<V[]> containerClass, final Type valueType) {
        return new ContainerGenericArrayFromArray<>(containerClass, valueType);
    }

    /**
     * Creates an instance of JSON array to generic array deserializer.
     *
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    @SuppressWarnings("unchecked")
    ContainerGenericArrayFromArray(final Class<V[]> containerClass, final Type valueType) {
        super(containerClass, (Class<V>) ((GenericArrayType) valueType).getGenericComponentType());
    }

    /**
     * Build target Java array value from array elements already stored in this container.
     *
     * @return target Java array value
     */
    @Override
    public V[] build() {
        @SuppressWarnings("unchecked")
        final V[] array = (V[]) Array.newInstance(valueClass(), getList().size());
        for (int i = 0; i < getList().size(); i++) {
            array[i] = getList().get(i);
        }
        return array;
    }

}
