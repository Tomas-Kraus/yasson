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

import javax.json.stream.JsonParser;

/**
 * JSON-B parser non terminal symbol representing JSON array {@code ARRAY}.
 *
 * JSON Array Grammar:<ul>
 * <li>{@code ARRAY  ⟶ string ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ number ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ true ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ false ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ null ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ '⎨' OBJECT ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ '[' ARRAY ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ ']'}</li></ul>
 */
final class NonTerminalJsonArray extends SymbolNonTerminal {

    /** Instance of {@code ARRAY} non terminal symbol class. */
    private static final NonTerminalJsonArray INSTANCE = new NonTerminalJsonArray();

    /**
     * Get instance of {@code ARRAY} non terminal symbol class.
     *
     * @return instance of {@code ARRAY} non terminal terminal symbol class
     */
    static NonTerminalJsonArray getInstance() {
        return INSTANCE;
    }

    /**
     * Expand grammar rule on {@code START_OBJECT}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY  ⟶ '⎨' OBJECT ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandStartObject(ParserContext uCtx, StackNode parent) {
        final StackNode stackNode = new StackNodeNonTerminalReduced(
                NonTerminalJsonObject.getInstance(), parent,
                parent.getContainer().valueType(),
                // Calling this before new StackNodeNonTerminalReduced causes tests failure
                ResolveType.deserializerForObject(uCtx, parent));
        uCtx.getStack().push(stackNode);
        TerminalStartObject.read(uCtx, null, parent, stackNode.getContainer());
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code START_ARRAY}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY  ⟶ '[' ARRAY ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandStartArray(ParserContext uCtx, StackNode parent) {
        final StackNode stackNode = new StackNodeNonTerminalReduced(
                NonTerminalJsonArray.getInstance(), parent,
                parent.getContainer().valueType(),
                // Calling this before new StackNodeNonTerminalReduced causes tests failure
                ResolveType.deserializerForArray(uCtx, parent.getContainer().valueType()));
        uCtx.getStack().push(stackNode);
        TerminalStartArray.read(uCtx, null, parent, stackNode.getContainer());
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_STRING}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY ⟶ string ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueString(ParserContext uCtx, StackNode parent) {
        TerminalValueString.read(
                uCtx, parent.getContainer().valueType(), parent,
                uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_NUMBER}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY ⟶ number ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueNumber(ParserContext uCtx, StackNode parent) {
        TerminalValueNumber.read(
                uCtx, parent.getContainer().valueType(), parent,
                uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_TRUE}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY ⟶ true ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueTrue(ParserContext uCtx, StackNode parent) {
        TerminalValueTrue.read(
                uCtx, parent.getContainer().valueType(), parent,
                uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_FALSE}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY ⟶ false ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueFalse(ParserContext uCtx, StackNode parent) {
        TerminalValueFalse.read(
                uCtx, parent.getContainer().valueType(), parent,
                uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_NULL}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY ⟶ null ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueNull(ParserContext uCtx, StackNode parent) {
        TerminalValueNull.read(
                uCtx, parent.getContainer().valueType(), parent,
                uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code END_ARRAY}.
     * Grammar rule expansion:<ul>
     * <li>{@code ARRAY  ⟶ ']'}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandEndArray(ParserContext uCtx, StackNode parent) {
        if (parent != null && parent.getParent() != null) {
            TerminalEndArray.read(
                    uCtx, null, parent.getParent(), parent.getContainer());
        }
        uCtx.nextToken();
    }

    /**
     * Mark rules matching current input token as regular or reduced.
     *
     * @param token current input token
     * @return Value of {@code true} for regular rule or value of {@code false} for reduced rule.
     */
    @Override
    boolean isNotReduced(JsonParser.Event token) {
        switch (token) {
            case VALUE_STRING:
            case VALUE_NUMBER:
            case VALUE_TRUE:
            case VALUE_FALSE:
            case VALUE_NULL:
            case START_OBJECT:
            case START_ARRAY:
                return false;
            default:
                return true;
        }
    }

}
