package org.eclipse.yasson.internal.deserializer.deserializers;

import java.time.Instant;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link OffsetTime} value.
 */
public class DeserializerValueOffsetTime extends DeserializerDateTime<OffsetTime> {

    static final Deserializer<OffsetTime> INSTANCE = new DeserializerValueOffsetTime();

    @Override
    OffsetTime fromTimeInMillis(final Long timeInMillis) {
        return OffsetTime.ofInstant(Instant.ofEpochMilli(timeInMillis), UTC);
    }

    @Override
    OffsetTime fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        return OffsetTime.parse(dateTimeValue, formatter.getDateTimeFormatter());
    }

    @Override
    OffsetTime fromDefault(final String dateTimeValue, Locale locale) {
        return OffsetTime.parse(dateTimeValue, DateTimeFormatter.ISO_OFFSET_TIME.withLocale(locale));
    }

}
