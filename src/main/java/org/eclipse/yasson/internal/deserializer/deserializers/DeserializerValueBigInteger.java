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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link BigInteger} value.
 */
public final class DeserializerValueBigInteger extends DeserializerValueNumbers<BigInteger> {

    static final Deserializer<BigInteger> INSTANCE = new DeserializerValueBigInteger();

    private static final BigInteger VALUE_TRUE = BigInteger.valueOf(1);
    
    private static final BigInteger VALUE_FALSE = BigInteger.valueOf(0);

    private DeserializerValueBigInteger() {
    }

    @Override
    public Class<BigInteger> valueType() {
        return BigInteger.class;
    }

    @Override
    public BigInteger stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(true);
        format.setParseBigDecimal(true);
        return ((BigDecimal) format.parse(jsonString)).toBigIntegerExact();
    }

    @Override
    public BigInteger stringValue(String jsonString) {
        return new BigInteger(jsonString);
    }

    @Override
    public BigInteger numberValue(ParserContext uCtx, Type type) {
        return new BigInteger(uCtx.getParser().getString());
    }

    @Override
    public BigInteger trueValue(ParserContext uCtx, Type type) {
        return VALUE_TRUE;
    }

    @Override
    public BigInteger falseValue(ParserContext uCtx, Type type) {
        return VALUE_FALSE;
    }

}
