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
import java.time.ZoneId;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON string or number as {@link ZoneId}.
 */
public final class DeserializerValueZoneIdType extends Deserializer<ZoneId> {

    static final Deserializer<ZoneId> INSTANCE = new DeserializerValueZoneIdType();

    private DeserializerValueZoneIdType(){
    }

    @Override
    public ZoneId stringValue(ParserContext uCtx, Type type) {
        return ZoneId.of(uCtx.getParser().getString());
    }

    @Override
    public ZoneId numberValue(ParserContext uCtx, Type type) {
        return ZoneId.of(uCtx.getParser().getString());
    }

    @Override
    public ZoneId trueValue(ParserContext uCtx, Type type) {
        return ZoneId.of(uCtx.getParser().getString());
    }

    @Override
    public ZoneId falseValue(ParserContext uCtx, Type type) {
        return ZoneId.of(uCtx.getParser().getString());
    }

    @Override
    public ZoneId nullValue(ParserContext uCtx, Type type) {
        return null;
    }
}
