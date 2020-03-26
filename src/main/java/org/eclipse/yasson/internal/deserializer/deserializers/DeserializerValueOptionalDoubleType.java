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
import java.util.OptionalDouble;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Deserialize JSON value as {@link OptionalDouble}.
 */
public final class DeserializerValueOptionalDoubleType extends Deserializer<OptionalDouble> {

    static final Deserializer<OptionalDouble> INSTANCE = new DeserializerValueOptionalDoubleType();

    private DeserializerValueOptionalDoubleType(){
    }

    @Override
    public OptionalDouble stringValue(ParserContext uCtx, Type type, Customization customization) {
        return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString()));
    }

    @Override
    public OptionalDouble numberValue(ParserContext uCtx, Type type, Customization customization) {
        return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString()));
    }

    @Override
    public OptionalDouble trueValue(ParserContext uCtx, Type type, Customization customization) {
        return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString()));
    }

    @Override
    public OptionalDouble falseValue(ParserContext uCtx, Type type, Customization customization) {
        return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString()));
    }

    @Override
    public OptionalDouble nullValue(ParserContext uCtx, Type type, Customization customization) {
        return OptionalDouble.empty();
    }
}
