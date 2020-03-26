/*******************************************************************************
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import javax.json.bind.JsonbException;
import javax.json.bind.annotation.JsonbDateFormat;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;
import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;
import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * JSON simple value as date or time value.
 *
 * @param <D> the type of returned date or time value
 */
public abstract class DeserializerDateTime<D> extends Deserializer<D> {
    
    static final ZoneId UTC = ZoneId.of("UTC");
    static final LocalTime ZERO_LOCAL_TIME = LocalTime.parse("00:00:00");

    DeserializerDateTime() {
    }

    static final JsonbDateFormatter getJsonbDateFormatter(ParserContext uCtx, Customization customization) {
        if (customization != null && customization.getDeserializeDateFormatter() != null) {
            return customization.getDeserializeDateFormatter();
        }
        return uCtx.getJsonbContext().getConfigProperties().getConfigDateFormatter();
    }

    @Override
    public D stringValue(ParserContext uCtx, Type type, Customization customization) {
        final String dateTimeValue = uCtx.getParser().getString();
        final JsonbDateFormatter formatter = getJsonbDateFormatter(uCtx, customization);
        if (JsonbDateFormat.TIME_IN_MILLIS.equals(formatter.getFormat())) {
            return fromTimeInMillis(Long.parseLong(dateTimeValue));
        } else if (formatter.getDateTimeFormatter() != null) {
            return callFromFormatedString(type, dateTimeValue, formatter);
        }
        final JsonbDateFormatter configDateTimeFormatter
                = uCtx.getJsonbContext().getConfigProperties().getConfigDateFormatter();
        if (configDateTimeFormatter != null && configDateTimeFormatter.getDateTimeFormatter() != null) {
            return callFromFormatedString(type, dateTimeValue, configDateTimeFormatter);
        }
        final boolean strictIJson = uCtx.getJsonbContext().getConfigProperties().isStrictIJson();
        if (strictIJson) {
            return callFromFormatedString(type, dateTimeValue, JsonbDateFormatter.IJSON_JSONB_DATE_FORMATTER);
        }
        return callFromDefault(
                type, dateTimeValue,
                uCtx.getJsonbContext().getConfigProperties().getLocale(formatter.getLocale()));
    }

    @Override
    public D numberValue(ParserContext uCtx, Type type, Customization customization) {
        return fromTimeInMillis(Long.parseLong(uCtx.getParser().getString()));
    }

    private D callFromFormatedString(final Type type, final String dateTimeValue, final JsonbDateFormatter formatter) {
        try {
            return fromFormatedString(dateTimeValue, formatter);
        } catch (DateTimeException e) {
            throw new JsonbException(
                    Messages.getMessage(MessageKeys.DATE_PARSE_ERROR, dateTimeValue, type), e);
        }
    }

    private D callFromDefault(final Type type, final String dateTimeValue, final Locale locale) {
        try {
            return fromDefault(dateTimeValue, locale);
        } catch (DateTimeException e) {
            throw new JsonbException(
                    Messages.getMessage(MessageKeys.DATE_PARSE_ERROR, dateTimeValue, type), e);
        }
    }

    abstract D fromTimeInMillis(Long timeInMillis);

    abstract D fromFormatedString(String dateTimeValue, JsonbDateFormatter formatter);

    abstract D fromDefault(String dateTimeValue, Locale locale);

    /**
     * Convert temporal date and time accessor to epoch in milliseconds.
     *
     * @param parsed source temporal date and time accessor
     * @return epoch in milliseconds representing provided date and time accessor
     */
    protected static final long temporalToTimeInMilis(final TemporalAccessor parsed) {
        return 1000L * parsed.getLong(ChronoField.INSTANT_SECONDS) + parsed.getLong(ChronoField.MILLI_OF_SECOND);
    }

    /**
     * Append UTC zone in case zone is not set on formatter.
     *
     * @param formatter formatter
     * @return zoned formatter
     */
    protected static final DateTimeFormatter getZonedFormatter(DateTimeFormatter formatter) {
        return formatter.getZone() != null
                ? formatter
                : formatter.withZone(UTC);
    }

}
