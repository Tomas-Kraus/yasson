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

import java.lang.reflect.Type;
import java.util.Optional;

import javax.json.bind.JsonbException;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;

/**
 * Deserialize JSON value.
 */
public final class DeserializerValueOptionalObject extends Deserializer<Optional<?>> {

    static final Deserializer<Optional<?>> INSTANCE = new DeserializerValueOptionalObject();

    private DeserializerValueOptionalObject(){
    }

    @Override
    public Optional<?> stringValue(ParserContext uCtx, Type type) {
        final Type valueType = ResolveType.resolveOptionalType(type);
        final Deserializer<?> deserializer = uCtx.getDeserializers().deserializer(valueType);
        if (deserializer == null) {
            throw new JsonbException("Can't deserialize JSON string into: " + valueType);
        }
        return Optional.of(deserializer.stringValue(uCtx, type));
    }

    @Override
    public Optional<?> numberValue(ParserContext uCtx, Type type) {
        final Type valueType = ResolveType.resolveOptionalType(type);
        final Deserializer<?> deserializer = uCtx.getDeserializers().deserializer(valueType);
        if (deserializer == null) {
            throw new JsonbException("Can't deserialize JSON number into: " + valueType);
        }
        return Optional.of(deserializer.numberValue(uCtx, type));
    }

    @Override
    public Optional<?> trueValue(ParserContext uCtx, Type type) {
        final Type valueType = ResolveType.resolveOptionalType(type);
        final Deserializer<?> deserializer = uCtx.getDeserializers().deserializer(valueType);
        if (deserializer == null) {
            throw new JsonbException("Can't deserialize JSON true into: " + valueType);
        }
        return Optional.of(deserializer.trueValue(uCtx, type));
    }

    @Override
    public Optional<?> falseValue(ParserContext uCtx, Type type) {
        final Type valueType = ResolveType.resolveOptionalType(type);
        final Deserializer<?> deserializer = uCtx.getDeserializers().deserializer(valueType);
        if (deserializer == null) {
            throw new JsonbException("Can't deserialize JSON false into: " + valueType);
        }
        return Optional.of(deserializer.falseValue(uCtx, type));
    }

    @Override
    public Optional<?> nullValue(ParserContext uCtx, Type type) {
        return Optional.empty();
    }
}
