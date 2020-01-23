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
 * Tomas Kraus
 * Thibault Vallin
 ******************************************************************************/

package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.yasson.internal.JsonbContext;

/**
 * Primitive types deserializers selection utilities.
 */
public final class Deserializers {

    private static final Map<Type, Deserializer<?>> DESERIALIZERS = new HashMap<>(64);

    static {
        DESERIALIZERS.put(String.class, DeserializerValueString.INSTANCE);
        DESERIALIZERS.put(Character.class, DeserializerValueChar.INSTANCE);
        DESERIALIZERS.put(char.class, DeserializerValueChar.INSTANCE);
        DESERIALIZERS.put(boolean.class, DeserializerValueBoolean.INSTANCE);
        DESERIALIZERS.put(Boolean.class, DeserializerValueBoolean.INSTANCE);
        DESERIALIZERS.put(byte.class, DeserializerValueByte.INSTANCE);
        DESERIALIZERS.put(Byte.class, DeserializerValueByte.INSTANCE);
        DESERIALIZERS.put(short.class, DeserializerValueShort.INSTANCE);
        DESERIALIZERS.put(Short.class, DeserializerValueShort.INSTANCE);
        DESERIALIZERS.put(int.class, DeserializerValueInteger.INSTANCE);
        DESERIALIZERS.put(Integer.class, DeserializerValueInteger.INSTANCE);
        DESERIALIZERS.put(long.class, DeserializerValueLong.INSTANCE);
        DESERIALIZERS.put(Long.class, DeserializerValueLong.INSTANCE);
        DESERIALIZERS.put(float.class, DeserializerValueFloat.INSTANCE);
        DESERIALIZERS.put(Float.class, DeserializerValueFloat.INSTANCE);
        DESERIALIZERS.put(double.class, DeserializerValueDouble.INSTANCE);
        DESERIALIZERS.put(Double.class, DeserializerValueDouble.INSTANCE);
        DESERIALIZERS.put(BigInteger.class, DeserializerValueBigInteger.INSTANCE);
        DESERIALIZERS.put(BigDecimal.class, DeserializerValueBigDecimal.INSTANCE);
        DESERIALIZERS.put(Number.class, DeserializerValueBigDecimal.INSTANCE);
        DESERIALIZERS.put(Object.class, DeserializerValueObject.INSTANCE);
        DESERIALIZERS.put(JsonValue.class, DeserializerValueJsonValue.INSTANCE);
        DESERIALIZERS.put(JsonString.class, DeserializerValueJsonStringType.INSTANCE);
        DESERIALIZERS.put(JsonArray.class, DeserializerValueJsonArray.INSTANCE);
        DESERIALIZERS.put(JsonNumber.class, DeserializerValueJsonNumber.INSTANCE);
        DESERIALIZERS.put(JsonObject.class, DeserializerValueJsonObject.INSTANCE);
        DESERIALIZERS.put(OptionalDouble.class, DeserializerValueOptionalDoubleType.INSTANCE);
        DESERIALIZERS.put(OptionalInt.class, DeserializerValueOptionalIntType.INSTANCE);
        DESERIALIZERS.put(OptionalLong.class, DeserializerValueOptionalLongType.INSTANCE);
        DESERIALIZERS.put(Optional.class, DeserializerValueOptionalObject.INSTANCE);
        DESERIALIZERS.put(ZoneOffset.class, DeserializerValueZoneOffset.INSTANCE);
        DESERIALIZERS.put(ZoneId.class, DeserializerValueZoneId.INSTANCE);
        DESERIALIZERS.put(ZonedDateTime.class, DeserializerValueZonedDateTime.INSTANCE);
        DESERIALIZERS.put(XMLGregorianCalendar.class, DeserializerValueXMLGregorianCalendarType.INSTANCE);
        DESERIALIZERS.put(Calendar.class, DeserializerValueCalendar.INSTANCE);
        DESERIALIZERS.put(Date.class, DeserializerValueDate.INSTANCE);
        DESERIALIZERS.put(java.sql.Date.class, DeserializerValueSqlDate.INSTANCE);
        DESERIALIZERS.put(java.sql.Timestamp.class, DeserializerValueSqlTimestamp.INSTANCE);
        DESERIALIZERS.put(LocalDateTime.class, DeserializerValueLocalDateTime.INSTANCE);
        DESERIALIZERS.put(LocalDate.class, DeserializerValueLocalDate.INSTANCE);
        DESERIALIZERS.put(LocalTime.class, DeserializerValueLocalTime.INSTANCE);
        DESERIALIZERS.put(Instant.class, DeserializerValueInstant.INSTANCE);
        DESERIALIZERS.put(OffsetDateTime.class, DeserializerValueOffsetDateTime.INSTANCE);
        DESERIALIZERS.put(OffsetTime.class, DeserializerValueOffsetTime.INSTANCE);
        DESERIALIZERS.put(SimpleTimeZone.class, DeserializerValueTimeZone.INSTANCE);
        DESERIALIZERS.put(TimeZone.class, DeserializerValueTimeZone.INSTANCE);
    }

    private final Map<Type, Deserializer<?>> deserializers;

    /**
     * Creates an instance of primitive types deserializers selectior.
     *
     * @param jsonbContext current JSON-B context
     */
    public Deserializers(JsonbContext jsonbContext) {
        deserializers = new HashMap<>(DESERIALIZERS);
    }

    /**
     * Get deserializer of JSON {@code string} or {@code number} value.
     *
     * @param type type of value to be returned
     * @return deserializer of JSON {@code string} or {@code number} value
     */
    public Deserializer<?> deserializer(Type type) {
        return deserializers.get(type);
    }

}
