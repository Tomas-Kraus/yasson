/*******************************************************************************
 * Copyright (c) 2016, 2018 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * Roman Grigoriadi
 * David Kral
 ******************************************************************************/
package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON simple value as {@link Character}.
 */
public class DeserializerValueChar extends Deserializer<Character> {

    static final Deserializer<Character> INSTANCE = new DeserializerValueChar();

    private static final Character VALUE_TRUE = '1';
    
    private static final Character VALUE_FALSE = '0';

    private DeserializerValueChar() {
    }

    /**
     * Convert JSON {@code string} to Character instance.
     *
     * @return 1st character from JSON string
     */
    @Override
    public Character stringValue(ParserContext uCtx) {
        final String str = uCtx.getParser().getString();
        return str.isEmpty() ? null : str.charAt(0);
    }

    /**
     * Convert JSON {@code number} to Character instance.
     *
     * @return character matching provided UTF-16 non supplementary code point in JSON number
     */
    @Override
    public Character numberValue(ParserContext uCtx) {
        return (char) Integer.parseInt(uCtx.getParser().getString());
    }

    /**
     * Convert JSON {@code true} to Character instance.
     *
     * @return '1' as JSON true value representation
     */
    @Override
    public Character trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    /**
     * Convert JSON {@code false} to Character instance.
     *
     * @return '0' as JSON false value representation
     */
    @Override
    public Character falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    /**
     * Convert JSON {@code null} to Character instance.
     *
     * @return {@code null} as JSON null value representation
     */
    @Override
    public Character nullValue(ParserContext uCtx) {
        return null;
    }

}
