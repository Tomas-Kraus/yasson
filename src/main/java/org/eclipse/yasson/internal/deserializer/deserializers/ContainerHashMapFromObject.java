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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java {@code HashMap} deserializer.
 */
class ContainerHashMapFromObject extends ContainerObject<String, Object, Map<String, Object>> {

    /** Current value type (the same for all @code Map} values). */
    private Type valueType;

    /** Current value class (the same for all array elements). */
    private final Class<Object> valueClass;

    /** Map components customizations. */
    private Customization customization;

    /** Map components class model. */
    private ClassModel classModel;

    /** Target Java {@code HashMap} being built from JSON array. */
    private final HashMap<String, Object> map;

    /**
     * Get new instance of JSON object to Java {@code HashMap} deserializer.
     *
     * @param containerClass class of the container
     * @param keyType target Java key type of Map elements
     * @param valueType target Java value type of Map elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapFromObject
    newInstance(final Class<Map<String, Object>> containerClass, final Type keyType, final Type valueType) {
        return new ContainerHashMapFromObject(containerClass, valueType);
    }

    /**
     * Creates an instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of Collection elements
     */
    private ContainerHashMapFromObject(final Class<Map<String, Object>> containerClass, final Type valueType) {
        super(containerClass);
        this.valueType = valueType;
        this.valueClass = (Class<Object>) ResolveType.resolveGenericType(valueType);
        this.map = new HashMap<>();
    }

    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        classModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
        customization = classModel.getClassCustomization();
    }

    /**
     * Add last parsed JSON value to this {@code Map}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(Object value) {
        map.put(getKey(), value);
        resetKey();
    }

    /**
     * Return target Java {@code Map} value built from already added [key,value] pairs.
     *
     * @param deserialization context
     * @return target Java {@code List} value
     */
    @Override
    public final Map<String, Object> build() {
        return map;
    }

    /**
     * Get current key type.
     *
     * @return current key type as {@code String.class}
     */
    @Override
    public final Class<String> keyType() {
        return String.class;
    }

    /**
     * Get map value type.
     *
     * @return map value type
     */
    @Override
    public final Type valueType() {
        return valueType;
    }

    /**
     * Get map value class.
     *
     * @return map value class
     */
    @Override
    public final Class<Object> valueClass() {
        return valueClass;
    }

    /**
     * Get map value customizations.
     *
     * @return map value customizations
     */
    @Override
    public Customization valueCustomization() {
        return customization;
    }

}
