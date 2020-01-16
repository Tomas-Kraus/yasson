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

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.ClassModel;

/**
 * JSON array to generic array deserializer.
 */
public class ContainerGenericArrayFromArray<V> extends ContainerArrayFromArray<V, V[]> {

    public static final class ComponentType implements GenericArrayType {

        private final Type component;

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
     * @param cm Java class model (ignored)
     * @param valueType target Java value type of array elements (ignored)
     * @return new instance of JSON array to byte array deserializer
     */
    static ContainerGenericArrayFromArray<?> newInstance(ClassModel cm, Type valueType) {
        return new ContainerGenericArrayFromArray<>(cm, valueType);
    }

    /**
     * Creates an instance of JSON array to generic array deserializer.
     *
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    @SuppressWarnings("unchecked")
    ContainerGenericArrayFromArray(ClassModel classModel, Type valueType) {
        super((Class<V>) ((GenericArrayType) valueType).getGenericComponentType(), classModel);
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
