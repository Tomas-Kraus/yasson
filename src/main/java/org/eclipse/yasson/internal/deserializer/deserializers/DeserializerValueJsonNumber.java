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
 * Thibault Vallin
 ******************************************************************************/

package org.eclipse.yasson.internal.deserializer.deserializers;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.json.Json;
import javax.json.JsonNumber;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value as {@link JsonNumber}.
 */
public final class DeserializerValueJsonNumber extends DeserializerValueNumbers<JsonNumber> {

    static final Deserializer<JsonNumber> INSTANCE = new DeserializerValueJsonNumber();

    /**
     * Get deserialized value type.
     *
     * @return deserialized value type
     */
    public Class<JsonNumber> valueType(){
        return JsonNumber.class;
    }

    /**
     * Deserialize JSON string value as {@link JsonNumber} child instance.
     * This method is called only when deserialization number format is set for target class.
     *
     * @param jsonString JSON string to be deserialized
     * @param format Json number formatter
     * @return {@link JsonNumber} child class instance containing JSON string value
     * @throws ParseException when decimal number formatter parsing fails
     */
    @Override
    public JsonNumber stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(false);
        return Json.createValue(format.parse(jsonString).intValue());
    }

    /**
     * Deserialize JSON string value as {@link Number} child instance.
     * This method is called by default (when deserialization number format is not set for target class).
     *
     * @param jsonString JSON string to be deserialized
     * @return {@link JsonNumber} child class instance containing JSON string value
     * @throws NumberFormatException when number parsing fails
     */
    @Override
    public JsonNumber stringValue(String jsonString) {
        return Json.createValue(Integer.parseInt(jsonString));
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

}
