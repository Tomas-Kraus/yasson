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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link OffsetDateTime} value.
 */
public class DeserializerValueOffsetDateTime extends DeserializerDateTime<OffsetDateTime> {

    static final Deserializer<OffsetDateTime> INSTANCE = new DeserializerValueOffsetDateTime();

    @Override
    OffsetDateTime fromTimeInMillis(final Long timeInMillis) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), UTC);
    }

    @Override
    OffsetDateTime fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return OffsetDateTime.parse(dateTimeValue, formatter.getDateTimeFormatter());
    }

    @Override
    OffsetDateTime fromDefault(final String dateTimeValue, Locale locale) {
        return OffsetDateTime.parse(dateTimeValue, DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(locale));
    }

}
