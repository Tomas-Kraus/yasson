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
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link Instant} value.
 */
public class DeserializerValueInstant extends DeserializerDateTime<Instant> {

    static final Deserializer<Instant> INSTANCE = new DeserializerValueInstant();
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(UTC);

    @Override
    Instant fromTimeInMillis(final Long timeInMillis) {
        return Instant.ofEpochMilli(timeInMillis);
    }

    @Override
    Instant fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return Instant.from(getZonedFormatter(formatter.getDateTimeFormatter()).parse(dateTimeValue));
    }

    @Override
    Instant fromDefault(final String dateTimeValue, Locale locale) {
        return Instant.from(DEFAULT_FORMATTER.withLocale(locale).parse(dateTimeValue));
    }

}
