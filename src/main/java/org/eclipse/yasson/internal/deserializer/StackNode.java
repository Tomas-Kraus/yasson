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

/**
 * JSON-B deserializer pushdown automaton stack item.
 * Represents non terminal symbol stored on the stack.
 */
abstract class StackNode {

    /** Non terminal symbol stored on the stack. Shall never be {@code null}. */
    private final SymbolNonTerminal symbol;

    /** Parent stack item reference. Root item value is {@code null}, non root items must always have
     *  parent reference. */
    private final StackNode parent;

    /** Java value type to be returned. Shall never be {@code null}. */
    private final Type type;

    /** Container type converter for JSON structure being represented by current non terminal symbol.
     *  Shall never be {@code null}. */
    private Container<?, ?, ?> container;

    /**
     * Creates an instance of JSON-B deserializer pushdown automaton stack item.
     *
     * @param symbol non terminal symbol stored on the stack
     * @param parent parent stack item reference
     * @param type returned Java value type
     * @param container container type converter for JSON structure being represented by current non terminal symbol
     */
    StackNode(
        final SymbolNonTerminal symbol, final StackNode parent, final Type type, final Container<?, ?, ?> container
    ) {
        this.symbol = symbol;
        this.parent = parent;
        this.type = type;
        this.container = container;
    }

    /**
     * Check whether current stack item is root item or not.
     *
     * @return {@code true} when current stack item is root item or {@code false} otherwise
     */
    boolean isRoot() {
        return parent == null;
    }

    /**
     * Get non terminal symbol stored on the stack.
     *
     * @return non terminal symbol stored on the stack
     */
    SymbolNonTerminal getSymbol() {
        return symbol;
    }

    /**
     * Get Java value type to be returned.
     *
     * @return Java value type to be returned
     */
    Type getType() {
        return type;
    }

    /**
     * Get parent stack item reference.
     *
     * @return parent parent stack item reference or {@code null} if this item is root item.
     */
    StackNode getParent() {
        return parent;
    }

    /**
     * Get {@link Container} type converter for JSON structure.
     *
     * @return {@link Container} type converter for JSON structure
     */
    Container<? extends Object, ? extends Object, ? extends Object> getContainer() {
       return container;
    }

    /**
     * Set {@link Container} type converter for JSON structure.
     * Used to set {@link Container} type converter for {@code JSON} non terminal symbol when being expanded
     * to JSON primitive type value.
     *
     * @param container {@link Container} type converter for JSON structure
     */
    void setContainer(Container<?, ?, ?> container) {
        if (this.container == null) {
            this.container = container;
        } else {
            throw new IllegalStateException("Data builder was already set");
        }
    }

    /**
     * Process current non terminal symbol stored on the top of the stack.
     * Handles single iteration of pushdown automaton processing depending on current stack item type:<ul>
     * <li>removes current item from the top of the stack for regular grammar rules (skipped for reduced expansion
     *     rules)</li>
     * <li>runs grammar rule expansion and related data processing</li></ul>
     *
     * @param uCtx JSON-B deserialization context
     */
    abstract void processNode(ParserContext uCtx);

}
