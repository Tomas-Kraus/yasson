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

import javax.json.JsonObject;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value as {@link JsonObject}.
 */
public final class DeserializerValueJsonObject extends Deserializer<JsonObject> {

    static final Deserializer<JsonObject> INSTANCE = new DeserializerValueJsonObject();

    private DeserializerValueJsonObject(){
    }

    @Override
    public JsonObject stringValue(ParserContext uCtx) {
        return uCtx.getParser().getObject();
    }

    @Override
    public JsonObject numberValue(ParserContext uCtx) {
        return uCtx.getParser().getObject();
    }

    @Override
    public JsonObject trueValue(ParserContext uCtx) {
        return uCtx.getParser().getObject();
    }

    @Override
    public JsonObject falseValue(ParserContext uCtx) {
        return uCtx.getParser().getObject();
    }

}
