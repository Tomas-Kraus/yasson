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
 * Thibault Vallin
 ******************************************************************************/

package org.eclipse.yasson.internal.deserializer.deserializers;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON value as {@link XMLGregorianCalendar}.
 */
public final class DeserializerValueXMLGregorianCalendarType extends Deserializer<XMLGregorianCalendar> {

    static final Deserializer<XMLGregorianCalendar> INSTANCE = new DeserializerValueXMLGregorianCalendarType();

    private DeserializerValueXMLGregorianCalendarType(){
    }

    @Override
    public XMLGregorianCalendar stringValue(ParserContext uCtx) {
        return null;
    }

    @Override
    public XMLGregorianCalendar numberValue(ParserContext uCtx) {
        return null;
    }

    @Override
    public XMLGregorianCalendar trueValue(ParserContext uCtx) {
        return null;
    }

    @Override
    public XMLGregorianCalendar falseValue(ParserContext uCtx) {
        return null;
    }

    @Override
    public XMLGregorianCalendar nullValue(ParserContext uCtx) {
        return null;
    }
}
