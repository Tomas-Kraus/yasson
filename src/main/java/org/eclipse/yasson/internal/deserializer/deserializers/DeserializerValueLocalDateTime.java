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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link LocalDateTime} value.
 */
public class DeserializerValueLocalDateTime extends DeserializerDateTime<LocalDateTime> {

    static final Deserializer<LocalDateTime> INSTANCE = new DeserializerValueLocalDateTime();

    @Override
    protected LocalDateTime fromTimeInMillis(final Long timeInMillis) {
        return Instant.ofEpochMilli(timeInMillis).atZone(UTC).toLocalDateTime();
    }

    @Override
    protected LocalDateTime fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return LocalDateTime.parse(dateTimeValue, formatter.getDateTimeFormatter());
    }

    @Override
    protected LocalDateTime fromDefault(final String dateTimeValue, Locale locale) {
        return LocalDateTime.parse(dateTimeValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(locale));
    }

}
