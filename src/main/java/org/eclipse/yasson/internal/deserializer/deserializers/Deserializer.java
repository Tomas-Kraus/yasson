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
 * JSON simpe value deserializer.
 * <p>
 * Deserializers are of two basic types:<ul>
 * <li>primitive type deserializer</li>
 * <li>container deserializer</li></ul>
 * <p>
 * Primitive type deserializer
 * <p>
 * Builds proper value for JSON primitive types ({@code string}, {@code number}, {@code true}, {@code false}
 * and {@code null}).
 * Child classes must be stateless to be used as singletons.
 * <p>
 * Container deserializer
 * <p>
 * Builds proper type for complex JSON types: <i>JSON object</i> and <i>JSON array</i>.
 * Child classes are not stateless because they hold Java values container being build.
 *
 * @param <T> the type of returned value
 */
public abstract class Deserializer<T> {

    /**
     * Build Java value from current JSON parser {@code string} value.
     * Implementing code can only retrieve value from the parser. It shall not change its current position.
     *
     * @param uCtx deserialization context
     * @param type the type of returned value
     * @param customization value customization
     * @return deserialized value
     */
    public abstract T stringValue(ParserContext uCtx, Type type, Customization customization);

    /**
     * Build Java value from current JSON parser {@code number} value.
     * Calls {@link Deserializer#stringValue(ParserContext,Type,Customization)} by default.
     *
     * @param uCtx deserialization context
     * @param type the type of returned value
     * @param customization value customization
     * @return deserialized value
     */
    public T numberValue(ParserContext uCtx, Type type, Customization customization) {
        return stringValue(uCtx, type, customization);
    }

    /**
     * Build Java value from current JSON parser {@code number} value.
     * Calls {@link Deserializer#stringValue(ParserContext,Type,Customization)} by default.
     *
     * @param uCtx deserialization context
     * @param type the type of returned value
     * @param customization value customization
     * @return deserialized value
     */
    public T trueValue(ParserContext uCtx, Type type, Customization customization) {
        return stringValue(uCtx, type, customization);
    }

    /**
     * Build Java value from current JSON parser {@code number} value.
     * Calls {@link Deserializer#stringValue(ParserContext,Type,Customization)} by default.
     *
     * @param uCtx deserialization context
     * @param type the type of returned value
     * @param customization value customization
     * @return deserialized value
     */
    public T falseValue(ParserContext uCtx, Type type, Customization customization) {
        return stringValue(uCtx, type, customization);
    }

    /**
     * Build Java value from current JSON parser {@code null} value.
     *
     * @param uCtx deserialization context
     * @param type the type of returned value
     * @param customization value customization
     * @return deserialized value as {@code null}
     */
    public T nullValue(ParserContext uCtx, Type type, Customization customization) {
        return null;
    }

}
