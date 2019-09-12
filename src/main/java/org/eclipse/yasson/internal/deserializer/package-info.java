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
/**
 * <b>JSON Document Deserializer</b>
 * <p>
 * Deserializes JSON structure described by RFC 8259 into Java object(s) structure.
 * Deserializer implements {@link javax.json.bind.serializer.DeserializationContext} API interface.
 * <p>
 * Usage (see {@link org.eclipse.yasson.internal.JsonBinding} for details):
 * <pre>
 * // JsonbContext jsonbContext - holding central components and configuration of jsonb runtime.
 * // Class&lt;?&gt; type - deserialization target Java class
 * // JsonParser parser - JSON parser to read data from
 * JsonUnmarshaller unmarshaller = new JsonUnmarshaller(jsonbContext);
 * unmarshaller.deserialize(type, parser);
 * </pre>
 * <p>
 * Package contains deserialization controll logic excluding JSON to Java value converters which are availavle
 * in a separate package.
 * <p>
 * @since 1.0.6
 */
package org.eclipse.yasson.internal.deserializer;
