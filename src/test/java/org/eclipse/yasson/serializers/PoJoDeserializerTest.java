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

import org.eclipse.yasson.serializers.model.Expedition;
import org.eclipse.yasson.serializers.model.PoJoWithAllTypes;
import org.eclipse.yasson.serializers.model.Pokemon;
import org.eclipse.yasson.serializers.model.Trainer;
import org.junit.jupiter.api.Test;

public class PoJoDeserializerTest {

    private static final Jsonb JSONB = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));

    /**
     * Unmarshal simple JSON Object:
     *  - source: JSON string
     *  - target: Java PoJo
     */
    @Test
    public void deserializeObjectToPoJo() {
        PoJoWithAllTypes src = new PoJoWithAllTypes("John", "Connor", 42, (byte)1, true, false);
        String json = src.toJson();
        PoJoWithAllTypes pojo = JSONB.fromJson(json, PoJoWithAllTypes.class);
        assertEquals(src, pojo);
    }


    /**
     * Unmarshal array of simple JSON Objects:
     *  - source: JSON string
     *  - target: Java array of PoJo
     */
    @Test
    public void deserializeArrayOfObjectsToArrayOfPoJo() {
        PoJoWithAllTypes[] srcArray = new PoJoWithAllTypes[] {
                new PoJoWithAllTypes("Philip", "Fry", 3005, (byte)0, false, true),
                new PoJoWithAllTypes("Leela", "Turanga", 24, (byte)0, false, true),
                new PoJoWithAllTypes("Hermes", "Conrad", 38, (byte)1, true, true)
        };
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append('[');
        for (PoJoWithAllTypes pojo : srcArray) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append(pojo.toJson());
        }
        sb.append(']');
        String json = sb.toString();
        PoJoWithAllTypes[] pojoArray = JSONB.fromJson(json, PoJoWithAllTypes[].class);
        assertEquals(srcArray.length, pojoArray.length);
        for (int i = 0; i < srcArray.length; i++) {
            assertEquals(srcArray[i], pojoArray[i]);
        }
    }

    //    {
    //        "name":"Kanto",
    //        "trainer":{
    //            "name":"Ash Ketchum",
    //            "age":12
    //        },
    //        "pokemons":[
    //            {
    //                "name":"Pikachu",
    //                "type":"electric",
    //                "cp":518
    //            },{
    //                "name":"Squirtle",
    //                "type":"water",
    //                "cp":421
    //            }
    //        ]
    //    }
    /**
     * Unmarshal JSON object containing encapsulated JSON object and JSON array:
     *  - source: JSON string
     *  - target: Java PoJo
     */
    @Test
    public void deserializeComplexStructure() {
        Expedition src = new Expedition("Kanto",
                new Trainer("Ash Ketchum", 12),
                new Pokemon("Pikachu", "electric", 518),
                new Pokemon("Squirtle", "water", 421));
        String json = src.toJson();
        Expedition pojo = JSONB.fromJson(json, Expedition.class);
        assertEquals(src, pojo);
    }

}
