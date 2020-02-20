/*******************************************************************************
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
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

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java {@code HashMap} item deserializer.
 */
public class ContainerHashMapItemFromArray extends ContainerObject<Object, Object, ContainerHashMapItemFromArray> {

    private enum State {
        KEY,
        VALUE;
    }

    /**
     * Get new instance of JSON object to Java {@code HashMap} deserializer.
     *
     * @param containerClass class of the container
     * @param keyType target Java key type of Map elements
     * @param valueType target Java value type of Map elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapItemFromArray newInstance(Class<?> containerClass, Type keyType, Type valueType) {
        return new ContainerHashMapItemFromArray();
    }

    /** Map container reference. */    
    private ContainerHashMapFromArray mapContainer;

    /** Internal state: expecting key or value. */
    private State state;

    /** Stored MapItem key. */
    private Object key;

    /** Stored MapItem value. */
    private Object value;

    /**
     * Creates an instance of container item deserializer.
     */
    ContainerHashMapItemFromArray() {
        super(null);
    }

    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        this.mapContainer = (ContainerHashMapFromArray) parent;
    }

    @Override
    public void setKey(Object key) {
        switch ((String) key) {
            case "key":
                state = State.KEY;
                break;
            case "value":
                state = State.VALUE;
                break;
            default:
                throw new IllegalStateException("Unknown map entry key: " + key);
        }
    }

    @Override
    public final void addValue(Object value) {
        switch (state) {
            case KEY:
                this.key = value;
                break;
            case VALUE:
                this.value = value;
                break;
            default:
                throw new IllegalStateException("Unknown map entry state: " + state);
        }
    }

    /**
     * Get current MapItem key.
     *
     * @return current MapItem key
     */
    @Override
    Object getKey() {
        return key;
    }

    /**
     * Get current MapItem value.
     *
     * @return current MapItem key
     */
    Object getValue() {
        return value;
    }

    @Override
    public ContainerHashMapItemFromArray build() {
        // Break backward reference before being passed to GC
        mapContainer = null;
        return this;
    }

    @Override
    public Type valueType() {
        switch (state) {
            case KEY:
                return mapContainer.keyType();
            case VALUE:
                return mapContainer.mapValueType();
            default:
                throw new IllegalStateException("Unknown map entry state: " + state);
        }
    }

    @Override
    public Class<Object> valueClass() {
        switch (state) {
            case KEY:
                return mapContainer.keyClass();
            case VALUE:
                return mapContainer.mapValueClass();
            default:
                throw new IllegalStateException("Unknown map entry state: " + state);
        }
    }

    @Override
    public Type keyType() {
        throw new UnsupportedOperationException("Key type does not make sense in MapItem context"); 
    }

    @Override
    public Customization valueCustomization() {
        switch (state) {
            case KEY:
                return mapContainer.keyCustomization();
            case VALUE:
                return mapContainer.valueCustomization();
            default:
                throw new IllegalStateException("Unknown map entry state: " + state);
        }
    }

}
