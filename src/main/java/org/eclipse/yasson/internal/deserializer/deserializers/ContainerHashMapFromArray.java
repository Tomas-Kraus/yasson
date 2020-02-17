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
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON array to Java {@code HashMap} deserializer.
 */
public class ContainerHashMapFromArray extends ContainerObject<Object, Object, Map<Object, Object>> {

    /** Current value type (the same for all @code Map} values). */
    private Class<?> valueType;

    /** Current value type (the same for all @code Map} values). */
    private Class<?> keyType;

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
     * @param classModel Java class model
     * @param keyType target Java key type of Map elements
     * @param valueType target Java value type of Map elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapFromArray newInstance(ClassModel classModel, Class<?> valueType) {
        return new ContainerHashMapFromArray(classModel, valueType);
    }

    /**
     * Creates an instance of container deserializer.
     *
     * @param classModel Java class model of the container type
     * @param valueType target Java value type of Map elements
     */
    ContainerHashMapFromArray(final ClassModel classModel, Class<?> valueType) {
        super(classModel);
        this.valueType = valueType;
        this.keyType = null;
        this.map = new HashMap<>();
    }

    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        keyClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(keyType);
        keyCustomization = keyClassModel.getClassCustomization();
        valueClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueType);
        valueCustomization = valueClassModel.getClassCustomization();
    }

    /**
     * Set key type.
     * This container is inicialized as an array container so key type must be passed later.
     *
     * @param keyType key type to set.
     */
    public void setKeyType(Class<?> keyType) {
        this.keyType = keyType;
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
    public Type keyType() {
        return keyType;
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

}
