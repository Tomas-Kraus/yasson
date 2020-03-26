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
import java.util.ArrayList;

/**
 * JSON-B deserializer pushdown automaton stack.
 * Stores non terminal symbols from JSON grammar described in {@link NonTerminalJson}.
 */
final class ParserStack {

    /** Initial stack size. */
    private static final int INIT_SIZE = 64;

    /** Internal stack structure. */
    private final ArrayList<StackNode> stack;

    /**
     * Creates an instance of JSON-B deserializer pushdown automaton stack.
     *
     * @param type target Java type for deserialization
     */
    ParserStack(Type type) {
        stack = new ArrayList<>(INIT_SIZE);
        stack.add(new StackNodeNonTerminal(NonTerminalJson.getInstance(), null, type, null));
    }

    /**
     * Return non terminal symbol from the top of the stack.
     *
     * @return node from the top of the stack
     */
    StackNode peek() {
        return stack.size() > 0 ? stack.get(stack.size() - 1) : null;
    }

    /**
     * Remove and return non terminal symbol from the top of the stack.
     *
     * @return node from the top of the stack
     */
    StackNode pop() {
        return stack.size() > 0 ? stack.remove(stack.size() - 1) : null;
    }

    /**
     * Push non terminal symbol to the top of the stack.
     * @param symbol non terminal symbol to be added to the top of the stack
     */
    void push(final StackNode symbol) {
        stack.add(symbol);
    }

    /**
     * Check whether this stack is empty.
     * @return {@code true} when this stack is empty or {@code false} otherwise.
     */
    boolean isEmpty() {
        return stack.isEmpty();
    }

}
