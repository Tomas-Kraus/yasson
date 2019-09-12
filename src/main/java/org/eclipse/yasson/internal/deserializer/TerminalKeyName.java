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

/**
 * {@code KEY_NAME} terminal symbol.
 */
final class TerminalKeyName {

    /**
     * Disable creation of class instances.
     *
     * @throws UnsupportedOperationException on any attempt to call class constructor
     */
    private TerminalKeyName() {
        throw new UnsupportedOperationException("Instances of TerminalKeyName are not allowed.");
    }

    /**
     * Reads {@code KEY_NAME} input token and sets key name to parent container.
     *
     * @param uCtx deserialization context
     * @param type target Java type for deserialization
     * @param parent parent stack item reference
     */
    static void read(ParserContext uCtx, Type type, StackNode parent) {
        parent.getContainer().object().setKey(uCtx.getParser().getString());
    }

}
