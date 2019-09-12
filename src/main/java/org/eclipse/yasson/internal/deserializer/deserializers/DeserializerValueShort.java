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
 * Deserialize JSON {@code string} or {@code number} as {@link Short} value.
 */
public final class DeserializerValueShort extends Deserializer<Short> {

    static final Deserializer<Short> INSTANCE = new DeserializerValueShort();

    private static final Short VALUE_TRUE = Short.valueOf((short) 1);
    
    private static final Short VALUE_FALSE = Short.valueOf((short) 0);

    private DeserializerValueShort() {
    }

    @Override
    public Short stringValue(ParserContext uCtx) {
        return Short.parseShort(uCtx.getParser().getString());
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

    @Override
    public Short nullValue(ParserContext uCtx) {
        return null;
    }

}
