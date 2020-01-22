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
 * Deserialize JSON {@code string} or {@code number} as {@link String} value.
 */
public final class DeserializerValueString extends Deserializer<String> {

    static final Deserializer<String> INSTANCE = new DeserializerValueString();

    private DeserializerValueString() {
    }

    @Override
    public String stringValue(ParserContext uCtx, Type type, Customization customization) {
        return uCtx.getParser().getString();
    }

}
