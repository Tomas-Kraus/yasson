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
package org.eclipse.yasson.internal.deserializer.deserializers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.json.bind.JsonbException;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;
import org.eclipse.yasson.internal.serializer.JsonbNumberFormatter;

/**
 * Deserialize JSON simple value as {@link Number} instance.
 *
 * @param <N> the type of returned Number value
 */
public abstract class DeserializerValueNumbers<N extends Number> extends Deserializer<N> {

    @Override
    public final N stringValue(ParserContext uCtx) {
        final JsonbNumberFormatter numberFormat = uCtx.getDeserializeNumberFormatter(valueType());
        if (numberFormat != null) {
            DecimalFormat format = new DecimalFormat(
                    numberFormat.getFormat(),
                    DecimalFormatSymbols.getInstance(
                            uCtx.getJsonbContext().getConfigProperties().getLocale(numberFormat.getLocale())));
            try {
                return stringValueFormated(uCtx.getParser().getString(), format);
            } catch (ParseException | ArithmeticException e) {
                throw new JsonbException(
                        Messages.getMessage(
                                MessageKeys.PARSING_NUMBER, uCtx.getParser().getString(), numberFormat.getFormat()), e);
            }
        }
        try {
            return stringValue(uCtx.getParser().getString());
        } catch (NumberFormatException e) {
            throw new JsonbException(
                    Messages.getMessage(MessageKeys.PARSING_NUMBER, uCtx.getParser().getString(), "N/A"), e);
        }
    }

    /**
     * Get deserialized value type.
     *
     * @return deserialized value type
     */
    public abstract Class<N> valueType();

    /**
     * Deserialize JSON string value as {@link Number} child instance.
     * This method is called only when deserialization number format is set for target class.
     *
     * @param jsonString JSON string to be deserialized
     * @param format decimal number formatter
     * @return {@link Number} child class instance containing JSON string value
     * @throws ParseException when decimal number formatter parsing fails
     */
    public abstract N stringValueFormated(String jsonString, DecimalFormat format) throws ParseException;

    /**
     * Deserialize JSON string value as {@link Number} child instance.
     * This method is called by default (when deserialization number format is not set for target class).
     *
     * @param jsonString JSON string to be deserialized
     * @return {@link Number} child class instance containing JSON string value
     * @throws NumberFormatException when number parsing fails
     */
    public abstract N stringValue(String jsonString) throws NumberFormatException;

}
