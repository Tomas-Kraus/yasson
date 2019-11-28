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
 * Thibault Vallin
 ******************************************************************************/

package org.eclipse.yasson.internal.deserializer.deserializers;

import java.util.OptionalLong;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value as {@link OptionalLong}.
 */
public class DeserializerValueOptionalLongType extends Deserializer<OptionalLong> {

    static final Deserializer<OptionalLong> INSTANCE = new DeserializerValueOptionalLongType();

    private DeserializerValueOptionalLongType(){
    }

    @Override
    public OptionalLong stringValue(ParserContext uCtx) {
        return OptionalLong.of(Long.parseLong(uCtx.getParser().getString()));
    }

    @Override
    public OptionalLong numberValue(ParserContext uCtx) {
        return OptionalLong.of(Long.parseLong(uCtx.getParser().getString()));
    }

    @Override
    public OptionalLong trueValue(ParserContext uCtx) {
        return OptionalLong.of(Long.parseLong(uCtx.getParser().getString()));
    }

    @Override
    public OptionalLong falseValue(ParserContext uCtx) {
        return OptionalLong.of(Long.parseLong(uCtx.getParser().getString()));
    }

    @Override
    public OptionalLong nullValue(ParserContext uCtx) {
        return null;
    }
}
