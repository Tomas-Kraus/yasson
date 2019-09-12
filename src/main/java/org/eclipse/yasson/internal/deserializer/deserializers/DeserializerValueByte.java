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
 * Deserialize JSON {@code string} or {@code number} as {@link Byte} value.
 */
public final class DeserializerValueByte extends Deserializer<Byte> {

    static final Deserializer<Byte> INSTANCE = new DeserializerValueByte();

    private static final Byte VALUE_TRUE = Byte.valueOf((byte) 1);
    
    private static final Byte VALUE_FALSE = Byte.valueOf((byte) 0);

    private DeserializerValueByte() {
    }

    @Override
    public Byte stringValue(ParserContext uCtx) {
        return Byte.parseByte(uCtx.getParser().getString());
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

    @Override
    public Byte nullValue(ParserContext uCtx) {
        return null;
    }

}
