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

import java.util.Optional;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value.
 */
public final class DeserializerValueOptionalObject extends Deserializer<Optional<?>> {

    static final Deserializer<Optional<?>> INSTANCE = new DeserializerValueOptionalObject();

    private DeserializerValueOptionalObject(){
    }

    @Override
    public Optional<?> stringValue(ParserContext uCtx) {
        return Optional.of(uCtx.getParser().getString());
    }

    @Override
    public Optional<?> numberValue(ParserContext uCtx) {
        return Optional.of(uCtx.getParser().getValue());
    }

    @Override
    public Optional<?> trueValue(ParserContext uCtx) {
        return Optional.of(uCtx.getParser().getString());
    }

    @Override
    public Optional<?> falseValue(ParserContext uCtx) {
        return Optional.of(uCtx.getParser().getString());
    }

    @Override
    public Optional<?> nullValue(ParserContext uCtx) {
        return null;
    }
}
