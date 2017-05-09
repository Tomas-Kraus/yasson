/*******************************************************************************
 * Copyright (c) 2016, 2017 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * Roman Grigoriadi
 ******************************************************************************/

package org.eclipse.yasson.internal.serializer;

import org.eclipse.yasson.internal.Unmarshaller;
import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;
import org.eclipse.yasson.internal.model.JsonBindingModel;

import javax.json.bind.JsonbException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Deserializer for {@link TimeZone} type.
 * 
 * @author David Kral
 */
public class TimeZoneTypeDeserializer extends AbstractValueTypeDeserializer<TimeZone> {

    /**
     * Creates a new instance.
     *
     * @param model Binding model.
     */
    public TimeZoneTypeDeserializer(JsonBindingModel model) {
        super(TimeZone.class, model);
    }

    @Override
    protected TimeZone deserialize(String jsonValue, Unmarshaller unmarshaller, Type rtType) {
        try {
            final ZoneId zoneId = ZoneId.of(jsonValue);
            final ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(zoneId);
            return new SimpleTimeZone(zonedDateTime.getOffset().getTotalSeconds() * 1000, zoneId.getId());
        } catch (ZoneRulesException e) {
            throw new JsonbException(Messages.getMessage(MessageKeys.ZONE_PARSE_ERROR, jsonValue), e);
        }
    }

}
