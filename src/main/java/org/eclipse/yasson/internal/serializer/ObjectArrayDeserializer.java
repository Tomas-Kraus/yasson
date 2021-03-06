/*******************************************************************************
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * Roman Grigoriadi
 * David Kral
 ******************************************************************************/
package org.eclipse.yasson.internal.serializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.yasson.internal.Unmarshaller;

/**
 * Item for handling arrays of objects.
 *
 * @param <T> object type
 */
public class ObjectArrayDeserializer<T> extends AbstractArrayDeserializer<T[]> {

    private final List<T> items = new ArrayList<>();

    private T[] arrayInstance;

    /**
     * Creates new instance of object array deserializer.
     *
     * @param builder deserializer builder
     */
    protected ObjectArrayDeserializer(DeserializerBuilder builder) {
        super(builder);
    }

    @Override
    protected List<?> getItems() {
        return items;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] getInstance(Unmarshaller unmarshaller) {
        if (arrayInstance == null || arrayInstance.length != items.size()) {
            arrayInstance = (T[]) Array.newInstance(getComponentClass(), items.size());
        }
        return items.toArray(arrayInstance);
    }
}
