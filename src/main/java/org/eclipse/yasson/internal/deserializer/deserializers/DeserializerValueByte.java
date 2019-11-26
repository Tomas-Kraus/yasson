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

import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON simple value as {@link Byte}.
 */
public final class DeserializerValueByte extends DeserializerValueNumbers<Byte> {

    static final Deserializer<Byte> INSTANCE = new DeserializerValueByte();

    private static final Byte VALUE_TRUE = Byte.valueOf((byte) 1);
    
    private static final Byte VALUE_FALSE = Byte.valueOf((byte) 0);

    private DeserializerValueByte() {
    }

    @Override
    public Class<Byte> valueType() {
        return Byte.class;
    }

    @Override
    public Byte stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(false);
        return format.parse(jsonString).byteValue();
    }

    @Override
    public Byte stringValue(String jsonString) {
        return Byte.parseByte(jsonString);
    }

    @Override
    public Byte numberValue(ParserContext uCtx) {
        return Byte.parseByte(uCtx.getParser().getString());
    }

    @Override
    public Byte trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Byte falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

}
