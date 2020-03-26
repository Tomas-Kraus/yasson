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

import org.eclipse.yasson.internal.deserializer.deserializers.ContainerArray;
import org.eclipse.yasson.internal.deserializer.deserializers.Deserializer;

/**
 * {@code VALUE_STRING} terminal symbol.
 */
final class TerminalValueString {

    /**
     * Disable creation of class instances.
     *
     * @throws UnsupportedOperationException on any attempt to call class constructor
     */
    private TerminalValueString() {
        throw new UnsupportedOperationException("Instances of TerminalValueString are not allowed.");
    }

    /**
     * Reads {@code VALUE_STRING} input token and stores converted Java value into parent comntainer.
     *
     * @param uCtx deserialization context
     * @param type target Java type for deserialization
     * @param parent parent stack item reference
     * @param deserializer primitive type deserializer
     */
    @SuppressWarnings("unchecked")
    static void read(ParserContext uCtx, Type type, StackNode parent, Deserializer<?> deserializer) {
        final ContainerArray<Object, Object> container = (ContainerArray<Object, Object>) parent.getContainer();
        container.addValue(deserializer.stringValue(uCtx, type, container.valueCustomization()));
    }

}
