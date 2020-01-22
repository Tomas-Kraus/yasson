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

import java.lang.reflect.Type;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.stream.JsonParser;

import org.eclipse.yasson.internal.JsonbContext;

/**
 * JSON-B deserialization entry point.
 * Parses JSON document from provided JSON parser and returns it as requested Java type.
 */
public final class JsonUnmarshaller implements DeserializationContext {

    /** JSON-B context holding central components and configuration. */
    private final JsonbContext jsonbContext;

    /**
     * Creates sn instance of JSON-B deserializer.
     *
     * @param jsonbContext JSON-B context holding central components and configuration
     */
    public JsonUnmarshaller(JsonbContext jsonbContext) {
        this.jsonbContext = jsonbContext;
    }

    /**
     * Deserialize JSON document into target Java instance of provided class.
     *
     * @param clazz target Java class for deserialization
     * @param parser JSON parser to read data from
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<T> clazz, JsonParser parser) {
        return (T) deserialize(
                new ParserContext(jsonbContext, parser, new ParserStack(clazz)));
    }

    /**
     * Deserialize JSON document into target Java instance of provided type.
     *
     * @param type target Java type for deserialization
     * @param parser JSON parser to read data from
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Type type, JsonParser parser) {
        return (T) deserialize(
                new ParserContext(jsonbContext, parser, new ParserStack(type)));
    }

    /**
     * Process JSON document deserialization.
     * Deserialization top level controll logic processing context free grammar rules.
     * Controll logic is implemented as pushdown automaton with stack stored in {@link ParserContext} instance.
     * Context free grammar rules are described in {@link NonTerminalJson} class.
     *
     * @param uCtx initialized deserialization context
     * @return Java object instance containing data from parsed JSON document
     */
    @SuppressWarnings("unchecked")
    private <T> T deserialize(ParserContext uCtx) {
        T data = null;
        uCtx.nextToken();
        StackNode node = null;
        while (!uCtx.getStack().isEmpty()) {
            node = uCtx.getStack().peek();
            node.processNode(uCtx);
        }
        if (node.getContainer() != null) {
            data = (T) node.getContainer().build();
        }
        return data;
    }

}
