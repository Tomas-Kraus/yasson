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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON simple value as {@link BigDecimal}.
 */
public final class DeserializerValueBigDecimal extends DeserializerValueNumbers<BigDecimal> {

    static final Deserializer<BigDecimal> INSTANCE = new DeserializerValueBigDecimal();

    private static final BigDecimal VALUE_TRUE = BigDecimal.valueOf(1);
    
    private static final BigDecimal VALUE_FALSE = BigDecimal.valueOf(0);

    private DeserializerValueBigDecimal() {
    }

    @Override
    public Class<BigDecimal> valueType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal stringValueFormated(String jsonString, DecimalFormat format) throws ParseException {
        format.setParseIntegerOnly(false);
        format.setParseBigDecimal(true);
        return (BigDecimal) format.parse(jsonString);
    }

    @Override
    public BigDecimal stringValue(String jsonString) {
        return new BigDecimal(jsonString);
    }

    @Override
    public BigDecimal numberValue(ParserContext uCtx) {
        return new BigDecimal(uCtx.getParser().getString());
    }

    @Override
    public BigDecimal trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public BigDecimal falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

}
