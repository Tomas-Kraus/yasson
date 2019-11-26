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
 * Deserialize JSON simple value as {@link Short}.
 */
public final class DeserializerValueShort extends DeserializerValueNumbers<Short> {

    static final Deserializer<Short> INSTANCE = new DeserializerValueShort();

    private static final Short VALUE_TRUE = Short.valueOf((short) 1);
    
    private static final Short VALUE_FALSE = Short.valueOf((short) 0);

    private DeserializerValueShort() {
    }

    @Override
    public Class<Short> valueType() {
        return Short.class;
    }

    @Override
    public Short stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(false);
        return format.parse(jsonString).shortValue();
    }

    @Override
    public Short stringValue(String jsonString) {
        return Short.parseShort(jsonString);
    }

    @Override
    public Short numberValue(ParserContext uCtx) {
        return Short.parseShort(uCtx.getParser().getString());
    }

    @Override
    public Short trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Short falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

}
