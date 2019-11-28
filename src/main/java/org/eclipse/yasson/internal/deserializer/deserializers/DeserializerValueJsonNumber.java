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

import javax.json.Json;
import javax.json.JsonNumber;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON array as {@link JsonNumber}.
 */
public final class DeserializerValueJsonNumber extends Deserializer<JsonNumber> {

    static final Deserializer<JsonNumber> INSTANCE = new DeserializerValueJsonNumber();

    private DeserializerValueJsonNumber(){
    }

    @Override
    public JsonNumber stringValue(ParserContext uCtx) {
        return Json.createValue(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public JsonNumber numberValue(ParserContext uCtx) {
        return  Json.createValue(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public JsonNumber trueValue(ParserContext uCtx) {
        return Json.createValue(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public JsonNumber falseValue(ParserContext uCtx) {
        return Json.createValue(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public JsonNumber nullValue(ParserContext uCtx) {
        return null;
    }
}
