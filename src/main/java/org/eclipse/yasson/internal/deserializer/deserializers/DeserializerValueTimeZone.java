/*******************************************************************************
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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
import java.time.ZoneId;
import java.util.TimeZone;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Deserialize JSON simple value as {@link TimeZone} value.
 */
public class DeserializerValueTimeZone extends Deserializer<TimeZone> {

    static final Deserializer<TimeZone> INSTANCE = new DeserializerValueTimeZone();

    @Override
    public TimeZone stringValue(ParserContext uCtx, Type type, Customization customization) {
        return TimeZone.getTimeZone(ZoneId.of(uCtx.getParser().getString()));
    }

}
