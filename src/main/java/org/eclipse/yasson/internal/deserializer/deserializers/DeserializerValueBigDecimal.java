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

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON simple value as {@link BigDecimal}.
 */
public final class DeserializerValueBigDecimal extends Deserializer<BigDecimal> {

    static final Deserializer<BigDecimal> INSTANCE = new DeserializerValueBigDecimal();

    private static final BigDecimal VALUE_TRUE = BigDecimal.valueOf(1);
    
    private static final BigDecimal VALUE_FALSE = BigDecimal.valueOf(0);

    private DeserializerValueBigDecimal() {
    }

    @Override
    public BigDecimal stringValue(ParserContext uCtx) {
        return new BigDecimal(uCtx.getParser().getString());
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

    @Override
    public BigDecimal nullValue(ParserContext uCtx) {
        return null;
    }

}
