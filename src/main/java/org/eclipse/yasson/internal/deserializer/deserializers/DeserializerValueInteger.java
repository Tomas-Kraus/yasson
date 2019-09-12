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
 * Deserialize JSON {@code string} or {@code number} as {@link Integer} value.
 */
public final class DeserializerValueInteger extends Deserializer<Integer> {

    static final Deserializer<Integer> INSTANCE = new DeserializerValueInteger();

    private static final Integer VALUE_TRUE = Integer.valueOf(1);
    
    private static final Integer VALUE_FALSE = Integer.valueOf(0);

    private DeserializerValueInteger() {
    }

    @Override
    public Integer stringValue(ParserContext uCtx) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

    @Override
    public Integer numberValue(ParserContext uCtx) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

    @Override
    public Integer trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Integer falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Integer nullValue(ParserContext uCtx) {
        return null;
    }

}