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

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Boolean} value.
 */
public final class DeserializerValueBoolean extends Deserializer<Boolean> {

    static final Deserializer<Boolean> INSTANCE = new DeserializerValueBoolean();

    private DeserializerValueBoolean() {
    }

    @Override
    public Boolean stringValue(ParserContext uCtx, Type type, Customization customization) {
        return Boolean.parseBoolean(uCtx.getParser().getString());
    }

    @Override
    public Boolean numberValue(ParserContext uCtx, Type type, Customization customization) {
        return Boolean.parseBoolean(uCtx.getParser().getString());
    }

    @Override
    public Boolean trueValue(ParserContext uCtx, Type type, Customization customization) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean falseValue(ParserContext uCtx, Type type, Customization customization) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean nullValue(ParserContext uCtx, Type type, Customization customization) {
        return null;
    }

}
