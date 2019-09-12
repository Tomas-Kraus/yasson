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

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Double} value.
 */
public final class DeserializerValueDouble extends Deserializer<Double> {

    static final Deserializer<Double> INSTANCE = new DeserializerValueDouble();

    private static final Double VALUE_TRUE = Double.valueOf(1);
    
    private static final Double VALUE_FALSE = Double.valueOf(0);

    private DeserializerValueDouble() {
    }

    @Override
    public Double stringValue(ParserContext uCtx) {
        return Double.parseDouble(uCtx.getParser().getString());
    }

    @Override
    public Double numberValue(ParserContext uCtx) {
        return Double.parseDouble(uCtx.getParser().getString());
    }

    @Override
    public Double trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Double falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Double nullValue(ParserContext uCtx) {
        return null;
    }

}
