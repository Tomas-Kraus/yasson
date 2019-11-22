package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.ReflectionUtils;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.CreatorModel;
import org.eclipse.yasson.internal.model.JsonbCreator;
import org.eclipse.yasson.internal.model.PropertyModel;

/**
 * JSON object to Java PoJo deserializer.
 *
 * @param <T> the type of returned PoJo
 */
public abstract class ContainerPoJoFromObject<T> extends Container<String, Object, T>  {

    /**
     * Get new instance of JSON object to Java PoJo deserializer.
     *
     * @param cm target Java class model
     * @param keyType target container key type (ignored)
     * @param valueType target container key type (will be overwritten by PoJo properties types)
     * @return new instance of JSON object to Java PoJo deserializer
     */
    public static final ContainerPoJoFromObject<?> newInstance(ClassModel cm, Class<?> keyType, Class<?> valueType) {
        return cm.getClassCustomization().getCreator() == null
                ? new WithoutCreator<>(cm, valueType) : new WithCreator<>(cm, valueType);
    }

    /** Type of current PoJo value to be deserialized. Set when key is being processed. */
    private Type valueType;

    /** Target Java class model. */
    private final ClassModel cm;

    /**
     * Creates an instance of JSON object to Java PoJo deserializer.
     *
     * @param cm target Java class model
     * @param valueType type of current PoJo value to be deserialized
     */
    ContainerPoJoFromObject(ClassModel cm, Class<?> valueType) {
        this.valueType = valueType;
        this.cm = cm;
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
     * Set current value type.
     *
     * @param valueType current value type to set
     */
    final void setValueType(Type valueType) {
        this.valueType = valueType;
    }

    /**
     * Get target Java class model.
     *
     * @return target Java class model.
     */
    ClassModel getCm() {
        return cm;
    }

    /**
     * JSON object to Java PoJo deserializer.
     * Performance optimized version with no custom instance creator.
     *
     * @param <T> the type of returned PoJo
     */
    private static final class WithoutCreator<T> extends ContainerPoJoFromObject<T> {

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
        private WithoutCreator(ClassModel cm, Class<?> valueType) {
            super(cm, valueType);
            instance = (T) ReflectionUtils.createNoArgConstructorInstance(cm);
        }

        /**
         * Set key of next JSON object value to be added to the container.
         *
         * @param key key of next JSON value (already converted to target key type)
         */
        @Override
        public void setKey(String key) {
            propertyModel = getCm().findPropertyModelByJsonReadName(key);
            setValueType(propertyModel.getPropertyDeserializationType());
            super.setKey(propertyModel.getPropertyName());
        }

        /**
         * Add last parsed JSON value to this container.
         *
         * @param value value already converted to target type
         */
        @Override
        public void addValue(Object value) {
            propertyModel.setValue(instance, value);
            resetKey();
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
    private static final class WithCreator<T> extends ContainerPoJoFromObject<T> {

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
        private WithCreator(ClassModel cm, Class<?> valueType) {
            super(cm, valueType);
            this.creator = cm.getClassCustomization().getCreator();
            this.values = new HashMap<>(creator.getParams().length);
        }

        /**
         * Set key of next JSON object value to be added to the container.
         *
         * @param key key of next JSON value (already converted to target key type)
         */
        @Override
        public void setKey(String key) {
            // Find proper creator parameter for key. Returns null when not found.
            param = creator.findByName(key);
            setValueType(param.getType());
            super.setKey(param.getName());
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
                values.put(getKey(), value);
            }
            resetKey();
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
            return (T) creator.call(paramValues, getCm().getType());
        }

    }

}
