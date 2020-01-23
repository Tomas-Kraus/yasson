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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link Timestamp} value.
 */
public class DeserializerValueSqlTimestamp extends DeserializerDateTime<Timestamp> {

    static final Deserializer<Timestamp> INSTANCE = new DeserializerValueSqlTimestamp();
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_DATE_TIME.withZone(UTC);

    @Override
    Timestamp fromTimeInMillis(final Long timeInMillis) {
        return new Timestamp(timeInMillis);
    }

    @Override
    Timestamp fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        final TemporalAccessor parsed = getZonedFormatter(formatter.getDateTimeFormatter()).parse(dateTimeValue);
        return Timestamp.from(LocalDateTime.from(parsed).atZone(ZoneId.of("UTC")).toInstant());
    }

    @Override
    Timestamp fromDefault(final String dateTimeValue, Locale locale) {
        TemporalAccessor parsed = DEFAULT_FORMATTER.withLocale(locale).parse(dateTimeValue);
        return Timestamp.from(LocalDateTime.from(parsed).atZone(ZoneId.of("UTC")).toInstant());
    }

}
