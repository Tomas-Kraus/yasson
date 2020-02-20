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
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerObject.MapKey;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON array to Java {@code HashMap} deserializer.
 */
public class ContainerHashMapFromArray extends ContainerObject<Object, Object, Map<Object, Object>> implements MapKey {

    /** Current value type (the same for all @code Map} values). */
    private final Type valueType;

    /** Current value type (the same for all @code Map} values). */
    private Type keyType;

    /** Current key class (the same for all array elements). */
    private Class<Object> keyClass;

    /** Current value class (the same for all array elements). */
    private final Class<Object> valueClass;

    /** Map components class model. */
    private ClassModel keyClassModel;

    /** Map components class model. */
    private ClassModel valueClassModel;

    /** Map components customizations. */
    private Customization valueCustomization;

    /** Map components customizations. */
    private Customization keyCustomization;

    /** Target Java {@code HashMap} being built from JSON array. */
    private final HashMap<Object, Object> map;

    /**
     * Get new instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param containerClass class of the container
     * @param keyType target Java key type of Map elements
     * @param valueType target Java value type of Map elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapFromArray
    newInstance(final Class<Map<Object, Object>> containerClass, final Type valueType) {
        return new ContainerHashMapFromArray(containerClass, valueType);
    }

    /**
     * Creates an instance of container deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of Map elements
     */
    ContainerHashMapFromArray(final Class<Map<Object, Object>> containerClass, final Type valueType) {
        super(containerClass);
        this.valueType = valueType;
        this.valueClass = (Class<Object>) ResolveType.resolveGenericType(valueType);
        this.keyType = null;
        this.keyClass = null;
        this.map = new HashMap<>();
    }

    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        keyClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(keyClass);
        keyCustomization = keyClassModel.getClassCustomization();
        valueClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
        valueCustomization = valueClassModel.getClassCustomization();
    }

    /**
     * Set key type.
     * This container is inicialized as an array container so key type must be passed later.
     *
     * @param keyType key type to set.
     */
    public void setKeyType(Type keyType) {
        this.keyType = keyType;
        this.keyClass = (Class<Object>) ResolveType.resolveGenericType(keyType);
    }

    /**
     * Add last parsed JSON value to this {@code Map}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(Object value) {
        map.put(
                ((ContainerHashMapItemFromArray) value).getKey(),
                ((ContainerHashMapItemFromArray) value).getValue());
    }

    @Override
    public Map<Object, Object> build() {
        return map;
    }

    /**
     * Cointainer component value type.
     * Internal container value that represents MapItem JSON Object.
     */
    @Override
    public Class<ContainerHashMapItemFromArray> valueType() {
        return ContainerHashMapItemFromArray.class;
    }

    @Override
    public Class<Object> valueClass() {
        return (Class) ContainerHashMapItemFromArray.class;
    }

    @Override
    public Type keyType() {
        return keyType;
    }

    /**
     * Get class of map key.
     *
     * @return class of map key
     */
    public Class<Object> keyClass() {
        return keyClass;
    }

    /**
     * Map value type.
     * Type of map value to be returned.
     *
     * @return type of map value to be returned.
     */
    public Type mapValueType() {
        return valueType;
    }

    /**
     * Map value type.
     * Type of map value to be returned.
     *
     * @return type of map value to be returned.
     */
    public Class<Object> mapValueClass() {
        return valueClass;
    }

    /**
     * Get current key customization.
     *
     * @return current key customization
     */
    public Customization keyCustomization() {
        return keyCustomization;
    }

    @Override
    public Customization valueCustomization() {
        return valueCustomization;
    }

    /**
     * Check whether this class implements key to value mapping and needs key type.
     *
     * @return value of {@code true} if this class needs key type or {@code false} otherwise
     */
    @Override
    public boolean isMap() {
        return true;
    }

    /**
     * Get {@link MapKey} instance of this class if implemented.
     *
     * @return {@link MapKey} instance of this class if implemented.
     */
    public MapKey mapKey() {
        return this;
    }

}
