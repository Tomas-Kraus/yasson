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
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java {@code JsonObject} deserializer.
 */
public class ContainerJsonObject extends ContainerObject<String, JsonValue, JsonObject> {

    /** Targer {@code JsonObject} builder instance. */
    private JsonObjectBuilder builder;

    /** Current value class (the same for all array elements). */
    private final Class<JsonValue> valueClass;

//    /** Array components class model. */
//    private ClassModel valueClassModel;
//
//    /** Array components customizations. */
//    private Customization customization;

    /**
     * Get new instance of JSON object to Java {@code JsonObject} deserializer.
     *
     * @param containerClass class of the container
     * @param keyType target Java key type of Map elements
     * @param valueType target Java value type of Map elements
     * @return new instance of JSON array to Java {@code JsonObject} deserializer
     */
    static final ContainerJsonObject
    newInstance(final Class<JsonObject> containerClass, final Type keyType, final Type valueType) {
        return new ContainerJsonObject(containerClass, valueType);
    }

    /**
     * Creates an instance of JSON object to Java {@code JsonObject} deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of Collection elements
     */
    private ContainerJsonObject(final Class<JsonObject> containerClass, final Type valueType) {
        super(containerClass);
        valueClass = JsonValue.class;
    }

    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        builder = Json.createObjectBuilder();
//        valueClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
//        customization = valueClassModel.getClassCustomization();
    }

    @Override
    public JsonObject build() {
        return builder.build();
    }

    @Override
    public Type keyType() {
        return String.class;
    }

    @Override
    public void addValue(JsonValue value) {
        builder.add(getKey(), value);
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
