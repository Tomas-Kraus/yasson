/*******************************************************************************
 * Copyright (c) 2015, 2018 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Dmitry Kornilov - initial implementation
 ******************************************************************************/
package org.eclipse.yasson.defaultmapping.basic;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.eclipse.yasson.Jsonbs.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Default mapping primitives tests.
 *
 * @author Dmitry Kornilov
 */
public class BasicTest {

    @Test
    public void testMarshallEscapedString() {
        assertEquals("[\" \\\\ \\\" / \\f\\b\\r\\n\\t 9\"]", bindingJsonb.toJson(new String[] {" \\ \" / \f\b\r\n\t \u0039"}));
    }

    @Test
    public void testMarshallWriter() {
        Writer writer = new StringWriter();
        bindingJsonb.toJson(new Long[]{5L}, writer);
        assertEquals("[5]", writer.toString());
    }

    @Test
    public void testMarshallOutputStream() throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        	bindingJsonb.toJson(new Long[]{5L}, baos);
            assertEquals("[5]", baos.toString("UTF-8"));
        }
    }

    @Test
    public void testObjectSerialization() {
        final String val = bindingJsonb.toJson(new Object());
        assertEquals("{}", val);
    }
}
