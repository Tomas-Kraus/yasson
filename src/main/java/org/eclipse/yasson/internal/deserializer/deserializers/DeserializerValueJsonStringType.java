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

import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.JsonString;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Deserialize JSON value as {@link JsonString}.
 */
public final class DeserializerValueJsonStringType extends Deserializer<JsonString> {

    static final Deserializer<JsonString> INSTANCE = new DeserializerValueJsonStringType();

    private DeserializerValueJsonStringType() {
    }

    @Override
    public JsonString stringValue(ParserContext uCtx, Type type, Customization customization) {
        return Json.createValue(uCtx.getParser().getString());
    }

    @Override
    public JsonString numberValue(ParserContext uCtx, Type type, Customization customization) {
        return Json.createValue(uCtx.getParser().getString());
    }

    @Override
    public JsonString trueValue(ParserContext uCtx, Type type, Customization customization) {
        return Json.createValue(uCtx.getParser().getString());
    }

    @Override
    public JsonString falseValue(ParserContext uCtx, Type type, Customization customization) {
        return Json.createValue(uCtx.getParser().getString());
    }

}
