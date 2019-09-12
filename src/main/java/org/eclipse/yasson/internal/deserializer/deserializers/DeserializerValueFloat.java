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
 * Deserialize JSON {@code string} or {@code number} as {@link Float} value.
 */
public final class DeserializerValueFloat extends Deserializer<Float> {

    static final Deserializer<Float> INSTANCE = new DeserializerValueFloat();

    private static final Float VALUE_TRUE = Float.valueOf(1);
    
    private static final Float VALUE_FALSE = Float.valueOf(0);

    private DeserializerValueFloat() {
    }

    @Override
    public Float stringValue(ParserContext uCtx) {
        return Float.parseFloat(uCtx.getParser().getString());
    }

    @Override
    public Float numberValue(ParserContext uCtx) {
        return Float.parseFloat(uCtx.getParser().getString());
    }

    @Override
    public Float trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Float falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Float nullValue(ParserContext uCtx) {
        return null;
    }

}
