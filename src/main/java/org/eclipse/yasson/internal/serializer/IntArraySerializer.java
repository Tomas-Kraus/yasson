/*******************************************************************************
 * Copyright (c) 2016, 2019 Oracle and/or its affiliates. All rights reserved.
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

import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * Serializer for arrays of ints.
 *
 * @author Roman Grigoriadi
 */
public class IntArraySerializer extends AbstractArraySerializer<int[]> {

    protected IntArraySerializer(SerializerBuilder builder) {
        super(builder);
    }

    @Override
    public void serializeContainer(int[] arr, JsonGenerator generator, SerializationContext ctx) {
        for (int obj : arr) {
            generator.write(obj);
        }
    }
}
