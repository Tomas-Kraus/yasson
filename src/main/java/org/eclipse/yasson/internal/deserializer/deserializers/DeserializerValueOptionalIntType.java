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

import java.util.OptionalInt;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value as {@link OptionalInt}.
 */
public class DeserializerValueOptionalIntType extends Deserializer<OptionalInt> {

    static final Deserializer<OptionalInt> INSTANCE = new DeserializerValueOptionalIntType();

    private DeserializerValueOptionalIntType(){
    }

    @Override
    public OptionalInt stringValue(ParserContext uCtx) {
        return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public OptionalInt numberValue(ParserContext uCtx) {
        return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public OptionalInt trueValue(ParserContext uCtx) {
        return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public OptionalInt falseValue(ParserContext uCtx) {
        return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString()));
    }

    @Override
    public OptionalInt nullValue(ParserContext uCtx) {
        return null;
    }
}
