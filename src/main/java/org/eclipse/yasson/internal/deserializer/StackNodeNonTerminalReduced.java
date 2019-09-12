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
 * Allows processing of reduced grammar rule expansion.
 * <p>
 * Stored non terminal symbol must override
 * {@link SymbolNonTerminal#isNotReduced(javax.json.stream.JsonParser.Event)} method to mark reduced grammar rules
 * properly.
 */
final class StackNodeNonTerminalReduced extends StackNode {

    /**
     * Creates an instance of JSON-B deserializer pushdown automaton stack item handling both regular and reduced
     * grammar rule expansions.
     *
     * @param symbol non terminal symbol stored on the stack
     * @param parent parent stack item reference
     * @param type returned Java value type
     * @param container container type converter for JSON structure being represented by current non terminal symbol
     */
    StackNodeNonTerminalReduced(
            final SymbolNonTerminal symbol, final StackNode parent, final Type type, final ContainerArray<?, ?> container
        ) {
            super(symbol, parent, type, container);
        }

    /**
     * Process current non terminal symbol stored on the top of the stack.
     * Handles single iteration of pushdown automaton processing for regular grammar rule.
     * Current item is removed from the top of the stack only when rule is not marked as reduced by
     * {@link SymbolNonTerminal#isNotReduced(javax.json.stream.JsonParser.Event)} method.
     *
     * @param uCtx JSON-B deserialization context
     */
    void processNode(final ParserContext uCtx) {
        if (getSymbol().isNotReduced(uCtx.getToken())) {
            uCtx.getStack().pop();
        }
        getSymbol().expand(uCtx, this);
    }

}
