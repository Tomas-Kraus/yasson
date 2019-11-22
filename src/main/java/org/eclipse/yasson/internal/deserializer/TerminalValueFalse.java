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
package org.eclipse.yasson.internal.deserializer;

import java.lang.reflect.Type;

import org.eclipse.yasson.internal.deserializer.deserializers.Container;
import org.eclipse.yasson.internal.deserializer.deserializers.Deserializer;

/**
 * {@code VALUE_FALSE} terminal symbol.
 */
final class TerminalValueFalse extends TerminalValue {

    /** Instance of {@code VALUE_FALSE} terminal symbol class. */
    private static final TerminalValueFalse INSTANCE = new TerminalValueFalse();

    /**
     * Get instance of {@code VALUE_FALSE} terminal symbol class.
     *
     * @return instance of {@code VALUE_FALSE} terminal symbol class
     */
    static TerminalValueFalse getInstance() {
        return INSTANCE;
    }

    /**
     * Reads {@code VALUE_FALSE} input token and stores converted Java value into parent comntainer.
     *
     * @param uCtx deserialization context
     * @param type target Java type for deserialization
     * @param parent parent stack item reference
     * @param deserializer primitive type deserializer
     */
    @Override
    @SuppressWarnings("unchecked")
    void read(ParserContext uCtx, Type type, StackNode parent, Deserializer<?> deserializer) {
        ((Container<Object, Object, Object>) parent.getContainer()).addValue(deserializer.falseValue(uCtx));
    }

}
