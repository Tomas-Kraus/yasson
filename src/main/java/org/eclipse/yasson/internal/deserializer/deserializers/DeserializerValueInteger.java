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

/**
 * Deserialize JSON simple value as {@link Integer}.
 */
public final class DeserializerValueInteger extends DeserializerValueNumbers<Integer> {

    static final Deserializer<Integer> INSTANCE = new DeserializerValueInteger();

    private static final Integer VALUE_TRUE = Integer.valueOf(1);
    
    private static final Integer VALUE_FALSE = Integer.valueOf(0);

    private DeserializerValueInteger() {
    }

    @Override
    public Class<Integer> valueType() {
        return Integer.class;
    }

    @Override
    public Integer stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(false);
        return format.parse(jsonString).intValue();
    }

    @Override
    public Integer stringValue(String jsonString) {
        return Integer.parseInt(jsonString);
    }

    @Override
    public Integer numberValue(ParserContext uCtx, Type type) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

    @Override
    public Integer trueValue(ParserContext uCtx, Type type) {
        return VALUE_TRUE;
    }

    @Override
    public Integer falseValue(ParserContext uCtx, Type type) {
        return VALUE_FALSE;
    }

}