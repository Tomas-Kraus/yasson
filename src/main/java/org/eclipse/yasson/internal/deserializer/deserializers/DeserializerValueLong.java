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
 * Deserialize JSON simple value as {@link Long}.
 */
public final class DeserializerValueLong extends DeserializerValueNumbers<Long> {

    static final Deserializer<Long> INSTANCE = new DeserializerValueLong();

    private static final Long VALUE_TRUE = Long.valueOf(1L);
    
    private static final Long VALUE_FALSE = Long.valueOf(0L);

    private DeserializerValueLong() {
    }

    @Override
    public Class<Long> valueType() {
        return Long.class;
    }

    @Override
    public Long stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(false);
        return format.parse(jsonString).longValue();
    }

    @Override
    public Long stringValue(String jsonString) {
        return Long.parseLong(jsonString);
    }

    @Override
    public Long numberValue(ParserContext uCtx, Type type) {
        return Long.parseLong(uCtx.getParser().getString());
    }

    @Override
    public Long trueValue(ParserContext uCtx, Type type) {
        return VALUE_TRUE;
    }

    @Override
    public Long falseValue(ParserContext uCtx, Type type) {
        return VALUE_FALSE;
    }

}