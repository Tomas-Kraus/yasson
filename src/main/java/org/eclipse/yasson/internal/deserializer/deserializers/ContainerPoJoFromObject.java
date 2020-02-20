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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.ReflectionUtils;
import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.CreatorModel;
import org.eclipse.yasson.internal.model.JsonbCreator;
import org.eclipse.yasson.internal.model.PropertyModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java PoJo deserializer.
 *
 * @param <T> the type of returned PoJo
 */
public class ContainerPoJoFromObject<T> extends ContainerObject<String, Object, T>  {

    private interface DataBuilder<T> {
        String getKey(String key);
        void addValue(Object value);
        T build();
    }

    /**
     * Get new instance of JSON object to Java PoJo deserializer.
     *
     * @param <T> target type 
     * @param containerClass class of the container
     * @param keyType target container key type (ignored)
     * @param valueType target container key type (will be overwritten by PoJo properties types)
     * @return new instance of JSON object to Java PoJo deserializer
     */
    public static final <T> ContainerPoJoFromObject<T>
    newInstance(Class<T> containerClass, Type keyType, Type valueType) {
        return new ContainerPoJoFromObject<>(containerClass, valueType);
    }

    /** Type of current PoJo value to be deserialized. Set when key is being processed. */
    private Type valueType;

    /** Current value class (the same for all array elements). */
    private Class<Object> valueClass;

    /** Array components customizations. */
    private Customization customization;

    /** Java class model of the container type. */
    private ClassModel classModel;

    /** Internal data builder depending on customizations. */
    private DataBuilder<T> dataBuilder;
    
    /**
     * Creates an instance of JSON object to Java PoJo deserializer.
     *
     * @param containerClass class of the container
     * @param valueType type of current PoJo value to be deserialized
     */
    ContainerPoJoFromObject(Class<T> containerClass, Type valueType) {
        super(containerClass);
        this.valueType = valueType;
    }

    /**
     * Notification about beginning of container deserialization.
     *
     * @param uCtx deserialization context
     * @param type container type
     * @param parent parent container or {@code null} if no parent exists
     */
    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        classModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(getContainerClass());
        dataBuilder = classModel.getClassCustomization().getCreator() == null
                ? new WithoutCreator<>(this) : new WithCreator<>(this);
    }

    /**
     * Get current key type.
     *
     * @return current key type as {@code String.class}
     */
    @Override
    public final Type keyType() {
        return String.class;
    }

    /**
     * Get current value type.
     *
     * @return current value type
     */
    @Override
    public final Type valueType() {
        return valueType;
    }

    /**
     * Get current value type.
     *
     * @return current value type
     */
    @Override
    public final Class<Object> valueClass() {
        return valueClass;
    }

    /**
     * Get current value customization.
     *
     * @return current value customization
     */
    @Override
    public Customization valueCustomization() {
        return customization;
    }

    /**
     * Set current value type.
     *
     * @param valueType current value type to set
     */
    final void setValueType(Type valueType) {
        this.valueType = valueType;
        this.valueClass = (Class<Object>) ResolveType.resolveGenericType(valueType);
    }

    /**
     * Set current value customization.
     *
     * @param customization current value customization to set
     */
    final void setValueCustomization(Customization customization) {
        this.customization = customization;
    }

    private ClassModel getClassModel() {
        return classModel;
    }

    
    @Override
    public T build() {
        return dataBuilder.build();
    }

    /**
     * Set key of next JSON object value to be added to the container.
     *
     * @param key key of next JSON value (already converted to target key type)
     */
    public void setKey(String key) {
        super.setKey(dataBuilder.getKey(key));
    }

    @Override
    public void addValue(Object value) {
        dataBuilder.addValue(value);
    }

    /**
     * JSON object to Java PoJo deserializer.
     * Performance optimized version with no custom instance creator.
     *
     * @param <T> the type of returned PoJo
     */
    private static final class WithoutCreator<T> implements DataBuilder<T> {

        /** JSON object to Java PoJo deserializer. */
        private final ContainerPoJoFromObject<T> container;

        /** Target PoJo instance. */
        private final T instance;

        /** Property model for current key. */
        private PropertyModel propertyModel;

        /**
         * Creates an instance of JSON object to Java PoJo deserializer.
         *
         * @param cm target Java class model
         * @param valueType type of current PoJo value to be deserialized
         */
        @SuppressWarnings("unchecked")
        private WithoutCreator(final ContainerPoJoFromObject<T> container) {
            this.container = container;
            this.instance = (T) ReflectionUtils.createNoArgConstructorInstance(container.getClassModel());
        }

        /**
         * Set key of next JSON object value to be added to the container.
         *
         * @param key key of next JSON value (already converted to target key type)
         */
        @Override
        public String getKey(String key) {
            propertyModel = container.getClassModel().findPropertyModelByJsonReadName(key);
            final Type propertyType = propertyModel.getPropertyDeserializationType();
            container.setValueType(propertyType instanceof Class
                    ? propertyType
                    : ResolveType.resolveType(container, propertyModel.getPropertyDeserializationType()));
            container.setValueCustomization(propertyModel.getCustomization());
            return propertyModel.getPropertyName();
        }

        /**
         * Add last parsed JSON value to this container.
         *
         * @param value value already converted to target type
         */
        @Override
        public void addValue(Object value) {
            propertyModel.setValue(instance, value);
            container.resetKey();
        }

        /**
         * Return target Java PoJo value built from already added [key,value] pairs.
         *
         * @param deserialization context
         * @return target Java {@code List} value
         */
        @Override
        public T build() {
            return instance;
        }

    }

    /**
     * JSON object to Java PoJo deserializer.
     * Performance optimized version with custom instance creator.
     *
     * @param <T> the type of returned PoJo
     */
    private static final class WithCreator<T> implements DataBuilder<T> {

        /** JSON object to Java PoJo deserializer. */
        private final ContainerPoJoFromObject<T> container;

        /** Values retrieved from JSON object. */
        private Map<String, Object> values;

        /** Custom instance creator. */
        private final JsonbCreator creator;

        /** Creator constructor parameters. */
        private CreatorModel param;

        /**
         * Creates an instance of JSON object to Java PoJo deserializer.
         *
         * @param cm target Java class model
         * @param valueType type of current PoJo value to be deserialized
         */
        private WithCreator(ContainerPoJoFromObject<T> container) {
            this.container = container;
            this.creator = container.getClassModel().getClassCustomization().getCreator();
            this.values = new HashMap<>(creator.getParams().length);
        }

        /**
         * Set key of next JSON object value to be added to the container.
         *
         * @param key key of next JSON value (already converted to target key type)
         */
        @Override
        public String getKey(String key) {
            // Find proper creator parameter for key. Returns null when not found.
            param = creator.findByName(key);
            container.setValueType(param.getType());
            container.setValueCustomization(param.getCustomization());
            return param.getName();
        }

        /**
         * Add last parsed JSON value to this container.
         *
         * @param value value already converted to target type
         */
        @Override
        public void addValue(Object value) {
            // Add value only when its name was recognized as creator parameter.
            if (param != null) {
                values.put(container.getKey(), value);
            }
            container.resetKey();
        }

        /**
         * Build target Java PoJo value built from already stored [key,value] pairs.
         *
         * @param deserialization context
         * @return target Java byte array value
         */
        @Override
        @SuppressWarnings("unchecked")
        public T build() {
            final CreatorModel[] params = creator.getParams();
            final int parLength = params.length;
            Object[] paramValues = new Object[parLength];
            for (int i = 0; i < parLength; i++) {
                paramValues[i] = values.get(params[i].getName());
            }
            return (T) creator.call(paramValues, container.getClassModel().getType());
        }

    }

}
