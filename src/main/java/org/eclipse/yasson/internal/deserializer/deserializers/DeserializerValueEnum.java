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
 * Deserialize JSON {@code string} as {@link Enum} value.
 */
public class DeserializerValueEnum extends Deserializer<Enum<?>> {

    static final Deserializer<Enum<?>> INSTANCE = new DeserializerValueEnum();

    private DeserializerValueEnum() {
    }

    @Override
    public Enum<?> stringValue(ParserContext uCtx, Type type, Customization customization) {
        return Enum.valueOf((Class<Enum>) type, uCtx.getParser().getString());
    }

}
