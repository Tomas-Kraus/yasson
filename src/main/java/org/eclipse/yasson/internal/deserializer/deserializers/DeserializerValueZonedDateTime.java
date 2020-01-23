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
 * Thibault Vallin
 * Tomas Kraus
 ******************************************************************************/
package org.eclipse.yasson.internal.deserializer.deserializers;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link ZonedDateTime} value.
 */
public final class DeserializerValueZonedDateTime extends DeserializerDateTime<ZonedDateTime> {

    static final Deserializer<ZonedDateTime> INSTANCE = new DeserializerValueZonedDateTime();

    @Override
    ZonedDateTime fromTimeInMillis(final Long timeInMillis) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), UTC);
    }

    @Override
    ZonedDateTime fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return ZonedDateTime.parse(dateTimeValue, getZonedFormatter(formatter.getDateTimeFormatter()));
    }

    @Override
    ZonedDateTime fromDefault(final String dateTimeValue, Locale locale) {
        return ZonedDateTime.parse(dateTimeValue, DateTimeFormatter.ISO_ZONED_DATE_TIME.withLocale(locale));
    }

}
