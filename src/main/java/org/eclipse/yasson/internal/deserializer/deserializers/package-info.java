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
 * <b>JSON to Java Value Converters for JSON Document Deserializer</b>
 * <p>
 * Converts value from source JSON document to target Java object structure.<br>
 * There are two types of converters:<ul>
 * <li>primitive type converter</li>
 * <li>container type converter</li></ul>
 * <b>Primitive type converter</b>
 * <p>
 * Converts JSON primitive type value:<ul>
 * <li>string</li>
 * <li>number</li>
 * <li>true</li>
 * <li>false</li>
 * <li>null</li></ul>
 * to target Java value (e.g. int, Integer, String, Date, etc.).
 * <p>
 * Primitive type converters are stateless classes extending {@link Deserializer} class. Conversion is implemented
 * by overriding {@link Deserializer#deserialize(org.eclipse.yasson.internal.deserializer.ParserContext)} method.<br>
 * Only JSON string and JSON number have special converters for various target Java types. Those converters
 * are registered in {@link Deserializers} class which handles their proper selection for primitive JSON value being
 * processed.
 * <p>
 * <b>Container type converter</b>
 * <p>
 * Converts complex structure from JSON object or JSON array to Java Collection, Map, PoJo and other structures.
 * Primitive type converters are statefull classes extending {@link Container} class. Conversion is implemented
 * by overriding:<ul>
 * <li>{@link Container#addValue(Object)} method to add next Java value to target Java structure</li>
 * <li>{@link Container#keyType()} method to retrieve Java type of key to be stored in target Java Map (makes no sense
 *     for Collection)</li>
 * <li>{@link Container#valueType()} method to retrieve Java type of value to be stored in target Java structure</li>
 * <li>{@link Deserializer#deserialize(org.eclipse.yasson.internal.deserializer.ParserContext)} method
 *     to retrieve target Java structure after being built</li></ul>
 * Converters are registered in {@link Containers} class which handles their proper selection for JSON object
 * and JSON array being processed.
 * <p>
 * @since 1.0.6
*/
package org.eclipse.yasson.internal.deserializer.deserializers;
