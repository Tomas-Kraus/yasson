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
 * Deserialize JSON {@code string} or {@code number} as {@link Boolean} value.
 */
public final class DeserializerValueBoolean extends Deserializer<Boolean> {

    static final Deserializer<Boolean> INSTANCE = new DeserializerValueBoolean();

    private DeserializerValueBoolean() {
    }

    @Override
    public Boolean stringValue(ParserContext uCtx) {
        return Boolean.parseBoolean(uCtx.getParser().getString());
    }

    @Override
    public Boolean numberValue(ParserContext uCtx) {
        return Boolean.parseBoolean(uCtx.getParser().getString());
    }

    @Override
    public Boolean trueValue(ParserContext uCtx) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean falseValue(ParserContext uCtx) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean nullValue(ParserContext uCtx) {
        return null;
    }

}