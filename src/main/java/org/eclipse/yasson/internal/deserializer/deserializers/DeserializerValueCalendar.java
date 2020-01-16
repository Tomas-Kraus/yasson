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

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.json.bind.JsonbException;
import javax.json.bind.annotation.JsonbDateFormat;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.customization.Customization;
import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;
import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

public class DeserializerValueCalendar extends Deserializer<Calendar> {

    static final Deserializer<Calendar> INSTANCE = new DeserializerValueCalendar();

    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final LocalTime ZERO_LOCAL_TIME = LocalTime.parse("00:00:00");
    private static final Calendar TEMPLATE = initTemplate();

    private DeserializerValueCalendar() {
    }

    private static final Calendar initTemplate() {
        Calendar template = new GregorianCalendar();
        template.clear();
        template.setTimeZone(TimeZone.getTimeZone(UTC));
        return template;
    }

    private static Class<Calendar> valueType() {
        return Calendar.class;
    }

    static final JsonbDateFormatter getJsonbDateFormatter(ParserContext uCtx) {
        Customization customization
                = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueType()).getClassCustomization();
        if (customization != null && customization.getDeserializeDateFormatter() != null) {
            return customization.getDeserializeDateFormatter();
        }
        return uCtx.getJsonbContext().getConfigProperties().getConfigDateFormatter();
    }

    @Override
    public Calendar stringValue(ParserContext uCtx, Type type) {
        final JsonbDateFormatter formatter = getJsonbDateFormatter(uCtx);
        if (JsonbDateFormat.TIME_IN_MILLIS.equals(formatter.getFormat())) {
            return fromTimeInMilis(uCtx, type);
        } else if (formatter.getDateTimeFormatter() != null) {
            return fromFormatedString(uCtx, type, formatter);
        }
        final JsonbDateFormatter configDateTimeFormatter
                = uCtx.getJsonbContext().getConfigProperties().getConfigDateFormatter();
        if (configDateTimeFormatter != null) {
            return fromFormatedString(uCtx, type, configDateTimeFormatter);
        }
        final boolean strictIJson = uCtx.getJsonbContext().getConfigProperties().isStrictIJson();
        if (strictIJson) {
            return fromFormatedString(uCtx, type, JsonbDateFormatter.IJSON_JSONB_DATE_FORMATTER);
        }
        try {
            return fromDefault(uCtx, type, formatter);
        } catch (DateTimeException e) {
            throw new JsonbException(
                    Messages.getMessage(MessageKeys.DATE_PARSE_ERROR, uCtx.getParser().getString(), type), e);
        }
    }

    private Calendar fromTimeInMilis(ParserContext uCtx, Type type) {
        final Instant instant = Instant.ofEpochMilli(Long.parseLong(uCtx.getParser().getString()));
        final Calendar calendar = (Calendar) TEMPLATE.clone();
        calendar.setTimeInMillis(instant.toEpochMilli());
        return calendar;
    }

    private Calendar fromFormatedString(ParserContext uCtx, Type type, JsonbDateFormatter formatter) {
        final String jsonValue = uCtx.getParser().getString();
        try {
            final TemporalAccessor parsed = formatter.getDateTimeFormatter().parse(jsonValue);
            LocalTime time = parsed.query(TemporalQueries.localTime());
            ZoneId zone = parsed.query(TemporalQueries.zone());
            if (zone == null) {
                zone = UTC;
            }
            if (time == null) {
                time = ZERO_LOCAL_TIME;
            }
            final ZonedDateTime result = LocalDate.from(parsed).atTime(time).atZone(zone);
            return GregorianCalendar.from(result);
        } catch (DateTimeException e) {
            throw new JsonbException(Messages.getMessage(MessageKeys.DATE_PARSE_ERROR, jsonValue, type), e);
        }
    }

    private Calendar fromDefault(ParserContext uCtx, Type type, JsonbDateFormatter formatter) {
        final Locale locale = uCtx.getJsonbContext().getConfigProperties().getLocale(formatter.getLocale());
        final String jsonValue = uCtx.getParser().getString();
        final DateTimeFormatter dtFormatter = jsonValue.contains("T")
                ? DateTimeFormatter.ISO_DATE_TIME
                : DateTimeFormatter.ISO_DATE;
        final TemporalAccessor parsed = dtFormatter.withLocale(locale).parse(jsonValue);
        LocalTime time = parsed.query(TemporalQueries.localTime());
        ZoneId zone = parsed.query(TemporalQueries.zone());
        if (zone == null) {
            zone = UTC;
        }
        if (time == null) {
            time = ZERO_LOCAL_TIME;
        }
        ZonedDateTime result = LocalDate.from(parsed).atTime(time).atZone(zone);
        return GregorianCalendar.from(result);
    }

}
