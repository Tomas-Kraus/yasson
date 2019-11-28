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
 * Thibault Vallin
 ******************************************************************************/

package org.eclipse.yasson.internal.deserializer.deserializers;

import javax.json.JsonValue;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON array as {@link JsonValue}.
 */
public final class DeserializerValueJsonValue extends Deserializer<JsonValue> {

    static final Deserializer<JsonValue> INSTANCE = new DeserializerValueJsonValue();

    private static final JsonValue VALUE_TRUE = JsonValue.TRUE;

    private static final JsonValue VALUE_FALSE = JsonValue.FALSE;

    private static final JsonValue VALUE_NULL = JsonValue.NULL;

    private DeserializerValueJsonValue() {
    }

    @Override
    public JsonValue stringValue(ParserContext uCtx) {
        return uCtx.getParser().getValue();
    }

    @Override
    public JsonValue numberValue(ParserContext uCtx) {
        return uCtx.getParser().getValue();
    }

    @Override
    public JsonValue trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public JsonValue falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public JsonValue nullValue(ParserContext uCtx) {
        return VALUE_NULL;
    }

}