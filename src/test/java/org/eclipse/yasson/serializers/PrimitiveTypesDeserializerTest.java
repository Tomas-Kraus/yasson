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
 ******************************************************************************/
package org.eclipse.yasson.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.junit.jupiter.api.Test;

public class PrimitiveTypesDeserializerTest {

    private static final Jsonb JSONB = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));

    /**
     * Unmarshal primitive type:
     *  - source: JSON string
     *  - target: Java String
     */
    @Test
    public void deserializeStringToString() {
        final String string = "StringToUnmarshal";
        final String json = "\"" + string + "\"";
        String target = JSONB.fromJson(json, String.class);
        assertEquals(string, target);
    }

    /**
     * Unmarshal primitive type:
     *  - source: JSON number
     *  - target: Java BigDecimal
     */
    @Test
    public void deserializeNumberToInteger() {
        final String json = "25";
        Integer target = JSONB.fromJson(json, Integer.class);
        assertEquals(Integer.parseInt(json), target.intValue());
    }

}
