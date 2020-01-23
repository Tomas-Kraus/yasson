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

import javax.json.bind.JsonbException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;
import org.eclipse.yasson.internal.serializer.JsonbDateFormatter;

/**
 * Deserialize JSON simple value as {@link XMLGregorianCalendar} value.
 */
public final class DeserializerValueXMLGregorianCalendarType extends DeserializerDateTime<XMLGregorianCalendar> {

    static final Deserializer<XMLGregorianCalendar> INSTANCE = new DeserializerValueXMLGregorianCalendarType();
    private static final Calendar TEMPLATE = initTemplate();

    private static Calendar initTemplate() {
        Calendar template = new GregorianCalendar();
        template.clear();
        template.setTimeZone(TimeZone.getTimeZone(UTC));
        return template;
    }

    @Override
    protected XMLGregorianCalendar fromTimeInMillis(final Long timeInMillis) {
        final GregorianCalendar calendar = (GregorianCalendar) TEMPLATE.clone();
        calendar.setTimeInMillis(timeInMillis);
        return newXMLGregorianCalendar(calendar);
    }

    @Override
    protected XMLGregorianCalendar fromFormatedString(final String dateTimeValue, final JsonbDateFormatter formatter) {
        final TemporalAccessor parsed = formatter.getDateTimeFormatter().parse(dateTimeValue);
        return temporalAccessorToXMLGregorianCalendar(parsed);
    }

    @Override
    protected XMLGregorianCalendar fromDefault(final String dateTimeValue, Locale locale) {
        final DateTimeFormatter formatter = dateTimeValue.contains("T")
                ? DateTimeFormatter.ISO_DATE_TIME
                : DateTimeFormatter.ISO_DATE;
        final TemporalAccessor parsed = formatter.withLocale(locale).parse(dateTimeValue);
        return temporalAccessorToXMLGregorianCalendar(parsed);
    }

    private static XMLGregorianCalendar temporalAccessorToXMLGregorianCalendar(final TemporalAccessor parsed) {
        LocalTime time = parsed.query(TemporalQueries.localTime());
        ZoneId zone = parsed.query(TemporalQueries.zone());
        if (zone == null) {
            zone = UTC;
        }
        if (time == null) {
            time = ZERO_LOCAL_TIME;
        }
        final ZonedDateTime result = LocalDate.from(parsed).atTime(time).atZone(zone);
        return newXMLGregorianCalendar(GregorianCalendar.from(result));
    }

    private static XMLGregorianCalendar newXMLGregorianCalendar(final GregorianCalendar calendar) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new JsonbException(Messages.getMessage(MessageKeys.DATATYPE_FACTORY_CREATION_FAILED), e);
        }
    }

}
