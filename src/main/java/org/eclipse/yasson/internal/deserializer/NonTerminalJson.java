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

import java.math.BigDecimal;

import org.eclipse.yasson.internal.deserializer.deserializers.ContainerSimple;

/**
 * JSON-B parser initial non terminal symbol {@code JSON}.
 *
 * JSON Parser LL(1) Grammar:<ul>
 * <li>{@code JSON   ⟶ string}</li>
 * <li>{@code JSON   ⟶ number}</li>
 * <li>{@code JSON   ⟶ true}</li>
 * <li>{@code JSON   ⟶ false}</li>
 * <li>{@code JSON   ⟶ null}</li>
 * <li>{@code JSON   ⟶ '⎨' OBJECT}</li>
 * <li>{@code JSON   ⟶ '[' ARRAY}</li>
 * <li>{@code OBJECT ⟶ key VALUE OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ '⎬'}</li>
 * <li>{@code ARRAY  ⟶ string ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ number ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ true ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ false ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ null ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ '⎨' OBJECT ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ '[' ARRAY ARRAY} - reduced expansion</li>
 * <li>{@code ARRAY  ⟶ ']'}</li>
 * <li>{@code VALUE  ⟶ string}</li>
 * <li>{@code VALUE  ⟶ number}</li>
 * <li>{@code VALUE  ⟶ true}</li>
 * <li>{@code VALUE  ⟶ false}</li>
 * <li>{@code VALUE  ⟶ null}</li>
 * <li>{@code VALUE  ⟶ '⎨' OBJECT}</li>
 * <li>{@code VALUE  ⟶ '[' ARRAY}</li></ul>
 *
 * This class represents {@code JSON} non terminal stack symbol and defines it's expansion rules.
 */
final class NonTerminalJson extends SymbolNonTerminal {

    /** Instance of {@code JSON} non terminal symbol class. */
    private static final NonTerminalJson INSTANCE = new NonTerminalJson();

    /**
     * Get instance of {@code JSON} non terminal symbol class.
     *
     * @return instance of {@code JSON} non terminal terminal symbol class
     */
    static NonTerminalJson getInstance() {
        return INSTANCE;
    }

    /**
     * Expand grammar rule on {@code START_OBJECT}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ '⎨' OBJECT}</li></ul>
     * Runtime type for {@code START_OBJECT} is {@code jsonbContext.getConfigProperties().getDefaultMapImplType();}
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandStartObject(ParserContext uCtx, StackNode parent) {
        final StackNode stackNode = new StackNodeNonTerminalReduced(
                NonTerminalJsonObject.getInstance(), parent,
                uCtx.getJsonbContext().getConfigProperties().getDefaultMapImplType(),
                // Calling this before new StackNodeNonTerminalReduced causes tests failure
                ResolveType.deserializerForObject(uCtx, parent.getType()));
        uCtx.getStack().push(stackNode);
        TerminalStartObject.read(uCtx, null, parent, stackNode.getContainer());
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code START_ARRAY}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ '[' ARRAY}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandStartArray(ParserContext uCtx, StackNode parent) {
        final StackNode stackNode = new StackNodeNonTerminalReduced(
                NonTerminalJsonArray.getInstance(), parent,
                parent.getType(),
                // Calling this before new StackNodeNonTerminalReduced causes tests failure
                ResolveType.deserializerForArray(uCtx, parent.getType()));
        uCtx.getStack().push(stackNode);
        TerminalStartArray.read(uCtx, null, parent, stackNode.getContainer());
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_STRING}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ string}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueString(ParserContext uCtx, StackNode parent) {
        // JSON (parent) is last stack symbol before getting empty, it shall hold output data.
        parent.setContainer(new ContainerSimple());
        TerminalValueString.read(
                uCtx, String.class, parent,
                uCtx.getDeserializers().deserializer(parent.getType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_NUMBER}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ number}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueNumber(ParserContext uCtx, StackNode parent) {
        // JSON (parent) is last stack symbol before getting empty, it shall hold output data.
        parent.setContainer(new ContainerSimple());
        TerminalValueNumber.read(
                uCtx, BigDecimal.class, parent,
                uCtx.getDeserializers().deserializer(parent.getType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_TRUE}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ true}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueTrue(ParserContext uCtx, StackNode parent) {
        // JSON (parent) is last stack symbol before getting empty, it shall hold output data.
        parent.setContainer(new ContainerSimple());
        TerminalValueTrue.read(
                uCtx, Boolean.class, parent,
                uCtx.getDeserializers().deserializer(parent.getType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_FALSE}.
     * Grammar rule expansion:<ul>
     * <li>{@code JSON ⟶ false}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueFalse(ParserContext uCtx, StackNode parent) {
        // JSON (parent) is last stack symbol before getting empty, it shall hold output data.
        parent.setContainer(new ContainerSimple());
        TerminalValueFalse.read(
                uCtx, Boolean.class, parent,
                uCtx.getDeserializers().deserializer(parent.getType()));
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code VALUE_NULL}.
     * Grammar rule expansion:<ul>
     * <li>{@code VALUE ⟶ null}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandValueNull(ParserContext uCtx, StackNode parent) {
        // JSON (parent) is last stack symbol before getting empty, it shall hold output data.
        parent.setContainer(new ContainerSimple());
        TerminalValueNull.read(
                uCtx, null, parent,
                uCtx.getDeserializers().deserializer(parent.getType()));
        uCtx.nextToken();
    }

}
