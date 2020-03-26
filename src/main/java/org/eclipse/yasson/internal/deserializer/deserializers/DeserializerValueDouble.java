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
package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Double} value.
 */
public final class DeserializerValueDouble extends DeserializerValueNumbers<Double> {

    static final Deserializer<Double> INSTANCE = new DeserializerValueDouble();

    private static final Double VALUE_TRUE = Double.valueOf(1);
    
    private static final Double VALUE_FALSE = Double.valueOf(0);

    private DeserializerValueDouble() {
    }

    @Override
    public Class<Double> valueType() {
        return Double.class;
    }

    @Override
    public Double stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(false);
        format.setParseBigDecimal(false);
        return format.parse(jsonString).doubleValue();
    }

    @Override
    public Double stringValue(String jsonString) {
        return Double.parseDouble(jsonString);
    }

    @Override
    public Double numberValue(ParserContext uCtx, Type type, Customization customization) {
        return Double.parseDouble(uCtx.getParser().getString());
    }

    @Override
    public Double trueValue(ParserContext uCtx, Type type, Customization customization) {
        return VALUE_TRUE;
    }

    @Override
    public Double falseValue(ParserContext uCtx, Type type, Customization customization) {
        return VALUE_FALSE;
    }

}
