/*******************************************************************************
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link Date} value.
 */
public class DeserializerValueDate extends DeserializerDateTime<Date> {

    static final Deserializer<Date> INSTANCE = new DeserializerValueDate();
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    Date fromTimeInMillis(final Long timeInMillis) {
        return new Date(timeInMillis);
    }

    @Override
    Date fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        TemporalAccessor parsed = parseWithOrWithoutZone(dateTimeValue, formatter.getDateTimeFormatter(), UTC);
        return Date.from(Instant.from(parsed));
    }

    @Override
    Date fromDefault(final String dateTimeValue, Locale locale) {
        TemporalAccessor parsed = parseWithOrWithoutZone(dateTimeValue, DEFAULT_DATE_TIME_FORMATTER.withLocale(locale), UTC);
        return Date.from(Instant.from(parsed));
    }

    /**
     * Parses the jsonValue as a java.time.ZonedDateTime that can later be use to be converted into a java.util.Date.<br>
     * At first the Json-Date is parsed with an Offset/Zone.<br>
     * If no Offset/Zone is present and the parsing fails, it will be parsed again with the fixed Zone that was passed as
     * defaultZone.
     *
     * @param dateTimeValue String value from json
     * @param formatter   DateTimeFormat options
     * @param defaultZone This Zone will be used if no other Zone was found in the jsonValue
     * @return Parsed date on base of a java.time.ZonedDateTime
     */
    private TemporalAccessor parseWithOrWithoutZone(String dateTimeValue, DateTimeFormatter formatter, ZoneId defaultZone) {
        try {
            // Try parsing with a Zone
            return ZonedDateTime.parse(dateTimeValue, formatter);
        } catch (DateTimeParseException e) {
            // Possibly exception occures because no Offset/ZoneId was found
            // Therefore parse with defaultZone again
            return ZonedDateTime.parse(dateTimeValue, formatter.withZone(defaultZone));
        }
    }
}
