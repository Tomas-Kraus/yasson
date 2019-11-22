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

import org.eclipse.yasson.internal.deserializer.deserializers.Container;
import org.eclipse.yasson.internal.deserializer.deserializers.Deserializers;

/**
 * JSON-B parser non terminal symbol representing JSON object {@code OBJECT}.
 * <br>
 * Original JSON Object Grammar:<ul>
 * <li>{@code OBJECT ⟶ key VALUE OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ '⎬'}</li>
 * <li>{@code VALUE  ⟶ string}</li>
 * <li>{@code VALUE  ⟶ number}</li>
 * <li>{@code VALUE  ⟶ true}</li>
 * <li>{@code VALUE  ⟶ false}</li>
 * <li>{@code VALUE  ⟶ null}</li>
 * <li>{@code VALUE  ⟶ '⎨' OBJECT}</li>
 * <li>{@code VALUE  ⟶ '[' ARRAY}</li></ul>
 * {@code VALUE} non terminal symbol can be reduced and modified LL(2) rules starting with {@code key} terminal symbols
 * are all handled in single {@code key} terminal handling method as reduced expansion.
 * <br>
 * Modified JSON Object Grammar:<ul>
 * <li>{@code OBJECT ⟶ key string OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key number OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key true OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key false OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key null OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key '⎨' OBJECT OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ key '[' ARRAY OBJECT} - reduced expansion</li>
 * <li>{@code OBJECT ⟶ '⎬'}</li></ul>
*/
final class NonTerminalJsonObject extends SymbolNonTerminal {

    /** Instance of {@code OBJECT} non terminal symbol class. */
    private static final NonTerminalJsonObject INSTANCE = new NonTerminalJsonObject();

    /**
     * Get instance of {@code OBJECT} non terminal symbol class.
     *
     * @return instance of {@code OBJECT} non terminal terminal symbol class
     */
    static NonTerminalJsonObject getInstance() {
        return INSTANCE;
    }

    /**
     * Expand grammar rule on {@code KEY_NAME}.
     * Reduced LL(2) grammar rules expansion:<ul>
     * <li>{@code OBJECT ⟶ key string OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key number OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key true OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key false OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key null OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key '⎨' OBJECT OBJECT} - reduced expansion</li>
     * <li>{@code OBJECT ⟶ key '[' ARRAY OBJECT} - reduced expansion</li></ul>
     * Both terminal symbols are processed in a single shot because this expansion rule must simulate LL(1) expansion
     * to the automaton.
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandKeyName(ParserContext uCtx, StackNode parent) {
        TerminalKeyName.getInstance().read(uCtx, null, parent);
        uCtx.nextToken();
        switch (uCtx.getToken()) {
            case START_OBJECT:
                final Container<?, ?, ?> objectContainer = ResolveType.deserializerForObject(uCtx, parent.getType());
                uCtx.getStack().push(
                        new StackNodeNonTerminalReduced(
                                NonTerminalJsonObject.getInstance(), parent,
                                parent.getContainer().valueType(),
                                objectContainer));
                TerminalStartObject.getInstance().read(uCtx, null, parent, objectContainer);
                break;
            case START_ARRAY:
                final Container<?, ?, ?> arrayContainer = ResolveType.deserializerForArray(uCtx, parent.getType());
                uCtx.getStack().push(
                        new StackNodeNonTerminalReduced(
                                NonTerminalJsonArray.getInstance(), parent,
                                parent.getContainer().valueType(),
                                arrayContainer));
                TerminalStartArray.getInstance().read(uCtx, null, parent, arrayContainer);
                break;
            case VALUE_STRING:
                TerminalValueString.getInstance().read(
                        uCtx, parent.getContainer().valueType(), parent,
                        uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
                break;
            case VALUE_NUMBER:
                TerminalValueNumber.getInstance().read(
                        uCtx, parent.getContainer().valueType(), parent,
                        uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
                break;
            case VALUE_TRUE:
                TerminalValueTrue.getInstance().read(
                        uCtx, parent.getContainer().valueType(), parent,
                        uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
                break;
            case VALUE_FALSE:
                TerminalValueFalse.getInstance().read(
                        uCtx, parent.getContainer().valueType(), parent,
                        uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
                break;
            case VALUE_NULL:
                TerminalValueNull.getInstance().read(
                        uCtx, parent.getContainer().valueType(), parent,
                        uCtx.getDeserializers().deserializer(parent.getContainer().valueType()));
                break;
            default: throw new IllegalStateException("Illegal JSON token "
                + uCtx.getToken().name() + " after KEY_NAME in JSON object");
        }
        uCtx.nextToken();
    }

    /**
     * Expand grammar rule on {@code END_OBJECT}.
     * Grammar rule expansion:<ul>
     * <li>{@code OBJECT ⟶ '⎬'}</li></ul>
     *
     * @param uCtx deserialization context
     * @param parent parent stack item reference
     */
    @Override
    void expandEndObject(ParserContext uCtx, StackNode parent) {
            TerminalEndObject.getInstance().read(
                    uCtx, null, parent, parent.getContainer());
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
        return token != JsonParser.Event.KEY_NAME;
    }

}
