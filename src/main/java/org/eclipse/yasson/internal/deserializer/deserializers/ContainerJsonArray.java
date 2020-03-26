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

import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java {@code JsonArray} deserializer.
 */
public class ContainerJsonArray extends ContainerArray<JsonValue, JsonArray> {

    /** Targer {@code JsonArray} builder instance. */
    private JsonArrayBuilder builder;

    /** Current value class (the same for all array elements). */
    private final Class<JsonValue> valueClass;

//    /** Array components class model. */
//    private ClassModel valueClassModel;
//
//    /** Array components customizations. */
//    private Customization customization;

    /**
     * Get new instance of JSON array to Java {@code JsonArray} deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of array elements
     * @return new instance of JSON array to byte array deserializer
     */
    public static final ContainerJsonArray
    newInstance(final Class<JsonArray> containerClass, final Type valueType) {
        return new ContainerJsonArray(containerClass, valueType);
    }

    /**
     * Creates an instance of JSON array to Java {@code JsonArray} deserializer.
     *
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    ContainerJsonArray(final Class<JsonArray> containerClass, final Type valueType) {
        super(containerClass);
        valueClass = JsonValue.class;
    }

    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        builder = Json.createArrayBuilder();
//        valueClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
//        customization = valueClassModel.getClassCustomization();
    }

    @Override
    public JsonArray build() {
        return builder.build();
    }

    @Override
    public void addValue(JsonValue value) {
        builder.add(value);
        
    }

    @Override
    public Type valueType() {
        return valueClass;
    }

    @Override
    public Class<JsonValue> valueClass() {
        return valueClass;
    }

    @Override
    public Customization valueCustomization() {
        return null;
    }

}
