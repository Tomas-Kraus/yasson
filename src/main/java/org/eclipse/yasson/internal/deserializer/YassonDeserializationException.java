/*******************************************************************************
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.yasson.internal.deserializer;

import javax.json.bind.JsonbException;

/**
 * Deserialization exception indicates problem in deserialization process.
 */
public class YassonDeserializationException  extends JsonbException {

    static final long serialVersionUID = -4428313249846528427L;

    /**
     * Constructs a new JSON binding deserialization exception with the specified
     * detail message and cause.
     * Note that the detail message associated with {@code cause} is <i>not</i>
     * automatically incorporated in this runtime exception's detail message.
     *
     * @param message  the detail message. The detail message is saved for
     *                 later retrieval by the {@link #getMessage()} method.
     * @param cause
     *      The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *      (A {@code null} value is permitted, and indicates that the cause is nonexistent or
     *      unknown.)
     */
    public YassonDeserializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new JSON binding deserialization exception with the specified
     * detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message  the detail message. The detail message is saved for
     *                 later retrieval by the {@link #getMessage()} method.
     */
    public YassonDeserializationException(final String message) {
        super(message);
    }

}
