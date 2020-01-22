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

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link Date} value.
 */
public class DeserializerValueSqlDate extends DeserializerDateTime<Date> {

    static final Deserializer<Date> INSTANCE = new DeserializerValueSqlDate();
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_DATE.withZone(UTC);

    @Override
    Date fromTimeInMillis(final Long timeInMillis) {
        return new Date(timeInMillis);
    }

    @Override
    Date fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        final TemporalAccessor parsed = getZonedFormatter(formatter.getDateTimeFormatter()).parse(dateTimeValue);
        return new Date(temporalToTimeInMilis(parsed));
    }

    @Override
    Date fromDefault(final String dateTimeValue, Locale locale) {
        TemporalAccessor parsed = DEFAULT_FORMATTER.withLocale(locale).parse(dateTimeValue);
        return new Date(temporalToTimeInMilis(parsed));
    }

    /**
     * Append UTC zone in case zone is not set on formatter.
     *
     * @param formatter formatter
     * @return zoned formatter
     */
    private DateTimeFormatter getZonedFormatter(DateTimeFormatter formatter) {
        return formatter.getZone() != null
                ? formatter
                : formatter.withZone(UTC);
    }

    private Instant getInstant(TemporalAccessor parsed) {
        LocalDate local = LocalDate.from(parsed);
        return local.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
    }

}
