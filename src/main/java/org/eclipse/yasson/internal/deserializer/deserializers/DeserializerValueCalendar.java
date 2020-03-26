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

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link Calendar} value.
 */
 public class DeserializerValueCalendar extends DeserializerDateTime<Calendar> {

    static final Deserializer<Calendar> INSTANCE = new DeserializerValueCalendar();
    private static final Calendar TEMPLATE = initTemplate();

    private static Calendar initTemplate() {
        Calendar template = new GregorianCalendar();
        template.clear();
        template.setTimeZone(TimeZone.getTimeZone(UTC));
        return template;
    }

    @Override
    Calendar fromTimeInMillis(final Long timeInMillis) {
        final Calendar calendar = (Calendar) TEMPLATE.clone();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    @Override
    Calendar fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        final TemporalAccessor parsed = formatter.getDateTimeFormatter().parse(dateTimeValue);
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
    }

    @Override
    Calendar fromDefault(final String dateTimeValue, Locale locale) {
        final DateTimeFormatter dtFormatter = dateTimeValue.contains("T")
                ? DateTimeFormatter.ISO_DATE_TIME
                : DateTimeFormatter.ISO_DATE;
        final TemporalAccessor parsed = dtFormatter.withLocale(locale).parse(dateTimeValue);
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
