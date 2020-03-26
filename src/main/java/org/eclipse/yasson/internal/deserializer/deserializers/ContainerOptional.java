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
import java.util.Optional;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Optional with Java PoJo deserializer.
 *
 * @param <T> the type of returned PoJo
 */
public class ContainerOptional<T> extends ContainerObject<Object, Object, Optional<T>> {

    /** Key type (if defined). */
    private Type keyType;
    /** Value type. */
    private Type valueType;
    /** PoJo Container. */
    private ContainerObject<Object, Object, T> container;

    /**
     * Get new instance of JSON object to Java PoJo deserializer.
     *
     * @param <T> target type 
     * @param containerClass class of the container
     * @param keyType target container key type
     * @param valueType target container value type
     * @return new instance of JSON object to Java PoJo deserializer
     */
    public static final <T> ContainerOptional<T>
    newObjectInstance(Class<Optional<T>> containerClass, Type keyType, Type valueType) {
        return new ContainerOptional<>(containerClass, keyType, valueType);
    }

    /**
     * Get new instance of JSON object to Java PoJo deserializer.
     *
     * @param <T> target type 
     * @param containerClass class of the container
     * @param valueType target container value type
     * @return new instance of JSON object to Java PoJo deserializer
     */
    public static final <T> ContainerOptional<T>
    newArrayInstance(Class<Optional<T>> containerClass, Type valueType) {
        return new ContainerOptional<>(containerClass, valueType);
    }

    /**
     * Creates an instance of JSON object to Java PoJo deserializer.
     *
     * @param containerClass class of the container
     * @param keyType target container key type
     * @param valueType target container value type
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ContainerOptional(Class<Optional<T>> containerClass, Type keyType, Type valueType) {
        super(containerClass);
        this.keyType = keyType;
        this.valueType = valueType;
        this.container = null;
    }

    /**
     * Creates an instance of JSON object to Java PoJo deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target container value type
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ContainerOptional(Class<Optional<T>> containerClass, Type valueType) {
        super(containerClass);
        this.keyType = null;
        this.valueType = valueType;
        this.container = null;
    }


    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        // PERF: Value class is already resolved, ResolveType.deserializerForArray will do one more resolving
        this.container = this.keyType == null
                ? (ContainerObject<Object, Object, T>) ResolveType.deserializerForArray(uCtx, valueType)
                : (ContainerObject<Object, Object, T>) ResolveType.deserializerForObject(uCtx, valueType);
        container.start(uCtx, type, parent);
    }    

    @Override
    public Optional<T> build() {
        return Optional.of(container.build());
    }

    @Override
    public Type keyType() {
        return container.keyType();
    }

    @Override
    public Object getKey() {
        return container.getKey();
    }

    @Override
    public void setKey(Object key) {
        container.setKey(key);
    }

    @Override
    public void resetKey() {
        container.resetKey();
    }

    @Override
    public void addValue(Object value) {
        container.addValue(value);
    }

    @Override
    public Type valueType() {
        return container.valueType();
    }

    @Override
    public Class<Object> valueClass() {
        return container.valueClass();
    }

    @Override
    public Customization valueCustomization() {
        return container.valueCustomization();
    }

}
