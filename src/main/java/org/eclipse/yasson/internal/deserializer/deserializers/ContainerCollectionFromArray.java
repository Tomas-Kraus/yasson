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
package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.Collection;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON array to Java Collection deserializer.
 *
 * @param <V> the type of Collection value returned by primitive type deserializer
 */
public abstract class ContainerCollectionFromArray<V> extends ContainerArray<V, Collection<V>> {

    /** Current value type (the same for all Collection elements). */
    private final Class<V> valueType;

    /** Collection components customizations. */
    private Customization customization;

    /** Collection components class model. */
    private ClassModel classModel;

    /**
     * Creates an instance of JSON array to Java Collection deserializer.
     *
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    @SuppressWarnings("unchecked")
    ContainerCollectionFromArray(Class<?> valueType, final ClassModel classModel) {
        super(classModel);
        this.valueType = (Class<V>) valueType;
    }

    /**
     * Notification about beginning of container deserialization.
     *
     * @param uCtx deserialization context
     * @param type container type
     * @param parent parent container or {@code null} if no parent exists
     */
    @Override
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        classModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueType);
        customization = classModel.getClassCustomization();
    }

    /**
     * Get collection value type.
     *
     * @return current value type
     */
    @Override
    public final Type valueType() {
        return valueType;
    }

    /**
     * Get collection value customizations.
     *
     * @return current value customizations
     */
    @Override
    public Customization valueCustomization() {
        return customization;
    }

}
