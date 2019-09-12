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
 * Represents grammar non terminal symbol and it's expansion rule. For each non
 * terminal symbol on the stack grammar rule method matching current terminal
 * symbol is called. Each child class represents single non terminal symbol and
 * its expansion rules.
 */
abstract class SymbolNonTerminal {

    /**
     * JSON token dispatcher for grammar rules.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    final void expand(ParserContext uCtx, StackNode parent) {
        switch (uCtx.getToken()) {
            case START_OBJECT:
                expandStartObject(uCtx, parent);
                return;
            case START_ARRAY:
                expandStartArray(uCtx, parent);
                return;
            case KEY_NAME:
                expandKeyName(uCtx, parent);
                return;
            case VALUE_NUMBER:
                expandValueNumber(uCtx, parent);
                return;
            case VALUE_STRING:
                expandValueString(uCtx, parent);
                return;
            case VALUE_TRUE:
                expandValueTrue(uCtx, parent);
                return;
            case VALUE_FALSE:
                expandValueFalse(uCtx, parent);
                return;
            case VALUE_NULL:
                expandValueNull(uCtx, parent);
                return;
            case END_OBJECT:
                expandEndObject(uCtx, parent);
                return;
            case END_ARRAY:
                expandEndArray(uCtx, parent);
                return;
            default:
                throw new IllegalArgumentException("Unknown JSON token " + uCtx.getToken().name());
        }
    }

    /**
     * Apply grammar expansion rule matching {@code START_OBJECT} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandStartObject(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: '{' was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code START_ARRAY} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandStartArray(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: '[' was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code KEY_NAME} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandKeyName(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON object value key was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code VALUE_NUMBER} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandValueNumber(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON number value was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code VALUE_STRING} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandValueString(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON string value was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code VALUE_TRUE} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandValueTrue(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON boolean value was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code VALUE_FALSE} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandValueFalse(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException("[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON boolean value was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code VALUE_NULL} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandValueNull(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: JSON null value was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code END_OBJECT} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandEndObject(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: '}' was not expected");
    }

    /**
     * Apply grammar expansion rule matching {@code END_ARRAY} input token.
     *
     * @param uCtx   deserialization context
     * @param parent parent stack item reference
     */
    void expandEndArray(ParserContext uCtx, StackNode parent) {
        throw new IllegalStateException(
                "[Line " + uCtx.getParser().getLocation().getLineNumber()
                + "] Syntax error: ']' was not expected");
    }

    /**
     * Mark rules matching current input token as regular or reduced. Default
     * implementation considers each rule as regular. Child classes with reduced
     * expansion rules shall override this method.
     *
     * @param token current input token
     * @return Value of {@code true} for regular rule or value of {@code false} for
     *         reduced rule.
     */
    boolean isNotReduced(JsonParser.Event token) {
        return true;
    }

}
