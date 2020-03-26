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
package org.eclipse.yasson.internal.deserializer;

import javax.json.stream.JsonParser;

import org.eclipse.yasson.internal.JsonbContext;
import org.eclipse.yasson.internal.deserializer.deserializers.Containers;
import org.eclipse.yasson.internal.deserializer.deserializers.Deserializers;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.serializer.JsonbNumberFormatter;

/**
 * JSON-B deserialization context.
 * Contains all objects necessary for deserialization process.
 */
public final class ParserContext {

    /** JSON-B context holding central components and configuration. */
    private final JsonbContext jsonbContext;

    /** JSON parser to read data from. */
    private final JsonParser parser;

    /** Pushdown automaton stack. */
    private final ParserStack stack;

    /** Primitive type converters register. */
    private final Deserializers deserializers;

    /** Container type converters register. */
    private final Containers containers;

    /** Current JSON document token. */
    private JsonParser.Event token;

    /**
     * Creates an instance of JSON-B deserialization context.
     * @param jsonbContext JSON-B context holding central components and configuration
     * @param parser JSON parser to read data from
     * @param stack initialized instance of pushdown automaton stack
     */
    ParserContext(JsonbContext jsonbContext, JsonParser parser, ParserStack stack) {
        this.jsonbContext = jsonbContext;
        this.parser = parser;
        this.token = null;
        this.stack = stack;
        this.deserializers = new Deserializers(jsonbContext);
        this.containers = new Containers(jsonbContext);
    }

    /**
     * Retrieve next token from JSON parser and store it in this context instance.
     */
    public void nextToken() {
        if (getParser().hasNext()) {
            token = getParser().next();
        }
    }

    /**
     * Get JSON parser to read data from.
     *
     * @return JSON parser to read data from.
     */
    public JsonParser getParser() {
        return parser;
    }

    /**
     * Get current JSON document token.
     *
     * @return current JSON document token
     */
    public JsonParser.Event getToken() {
        return token;
    }

    /**
     * Get current JSON-B context holding central components and configuration.
     *
     * @return current JSON-B context
     */
    public JsonbContext getJsonbContext() {
        return jsonbContext;
    }

    /**
     * Get number formatter for deserialization for provided class if exists.
     *
     * @param clazz class to search for
     * @return number formatter for deserialization for provided class or {@code null} if no such formatter exists
     */
    public JsonbNumberFormatter getDeserializeNumberFormatter(Class<?> clazz) {
        final ClassModel cm = jsonbContext.getMappingContext().getClassModel(clazz);
        return cm != null && cm.getClassCustomization() != null
                ? cm.getClassCustomization().getDeserializeNumberFormatter()
                : null;
    }

    ParserStack getStack() {
        return stack;
    }

    public Deserializers getDeserializers() {
        return deserializers;
    }

    Containers getContainers() {
        return containers;
    }

}
