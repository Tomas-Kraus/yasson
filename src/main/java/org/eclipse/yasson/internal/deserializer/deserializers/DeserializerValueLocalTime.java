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

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link LocalTime} value.
 */
public class DeserializerValueLocalTime extends DeserializerDateTime<LocalTime> {

    static final Deserializer<LocalTime> INSTANCE = new DeserializerValueLocalTime();

    @Override
    protected LocalTime fromTimeInMillis(final Long timeInMillis) {
        return Instant.ofEpochMilli(timeInMillis).atZone(UTC).toLocalTime();
    }

    @Override
    protected LocalTime fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return LocalTime.parse(dateTimeValue, formatter.getDateTimeFormatter());
    }

    @Override
    protected LocalTime fromDefault(final String dateTimeValue, Locale locale) {
        return LocalTime.parse(dateTimeValue, DateTimeFormatter.ISO_LOCAL_TIME.withLocale(locale));
    }

}
