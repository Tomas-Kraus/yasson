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

import javax.json.JsonArray;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON array as {@link JsonArray}.
 */
public final class DeserializerValueJsonArray extends Deserializer<JsonArray> {

    static final Deserializer<JsonArray> INSTANCE = new DeserializerValueJsonArray();

    private DeserializerValueJsonArray(){
    }

    @Override
    public JsonArray stringValue(ParserContext uCtx) {
        return uCtx.getParser().getArray();
    }

    @Override
    public JsonArray numberValue(ParserContext uCtx) {
        return uCtx.getParser().getArray();
    }

    @Override
    public JsonArray trueValue(ParserContext uCtx) {
        return uCtx.getParser().getArray();
    }

    @Override
    public JsonArray falseValue(ParserContext uCtx) {
        return uCtx.getParser().getArray();
    }

    @Override
    public JsonArray nullValue(ParserContext uCtx) {
        return null;
    }
}
