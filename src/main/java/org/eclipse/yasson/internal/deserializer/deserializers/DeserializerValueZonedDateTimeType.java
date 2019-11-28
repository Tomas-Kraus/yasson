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

import java.time.ZonedDateTime;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON array as {@link ZonedDateTime}.
 */
public final class DeserializerValueZonedDateTimeType extends Deserializer<ZonedDateTime> {

    static final Deserializer<ZonedDateTime> INSTANCE = new DeserializerValueZonedDateTimeType();

    private DeserializerValueZonedDateTimeType(){
    }

    @Override
    public ZonedDateTime stringValue(ParserContext uCtx) {
        return ZonedDateTime.parse(uCtx.getParser().getString());
    }

    @Override
    public ZonedDateTime numberValue(ParserContext uCtx) {
        return ZonedDateTime.parse(uCtx.getParser().getString());
    }

    @Override
    public ZonedDateTime trueValue(ParserContext uCtx) {
        return ZonedDateTime.parse(uCtx.getParser().getString());
    }

    @Override
    public ZonedDateTime falseValue(ParserContext uCtx) {
        return ZonedDateTime.parse(uCtx.getParser().getString());
    }

    @Override
    public ZonedDateTime nullValue(ParserContext uCtx) {
        return null;
    }
}
