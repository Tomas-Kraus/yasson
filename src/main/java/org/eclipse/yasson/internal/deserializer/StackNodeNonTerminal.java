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

/**
 * JSON-B deserializer pushdown automaton stack item.
 * Represents non terminal symbol stored on the stack.
 * Allows processing of regular grammar rule expansion only.
 */
final class StackNodeNonTerminal extends StackNode {


    /**
     * Creates an instance of JSON-B deserializer pushdown automaton stack item handling regular grammar rule expansions
     * only.
     *
     * @param symbol non terminal symbol stored on the stack
     * @param parent parent stack item reference
     * @param type returned Java value type
     * @param container container type converter for JSON structure being represented by current non terminal symbol
     */
    StackNodeNonTerminal(
            final SymbolNonTerminal symbol, final StackNode parent, final Type type, final ContainerArray<?, ?> container
        ) {
            super(symbol, parent, type, container);
        }

    /**
     * Process current non terminal symbol stored on the top of the stack.
     * Handles single iteration of pushdown automaton processing for regular grammar rule.
     * Current item is always removed from the top of the stack.
     *
     * @param uCtx JSON-B deserialization context
     */
    void processNode(final ParserContext uCtx) {
        uCtx.getStack().pop();
        getSymbol().expand(uCtx, this);
    }

}
