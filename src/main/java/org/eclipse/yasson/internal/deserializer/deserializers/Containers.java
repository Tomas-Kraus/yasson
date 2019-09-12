package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.yasson.internal.JsonbContext;
import org.eclipse.yasson.internal.model.ClassModel;

/**
 * Containers deserializers selection utilities.
 */
public final class Containers {

    /**
     * Container instance creator for JSON array deserializers.
     */
    @FunctionalInterface
    interface ArrayContainerBuiler {
        /**
         * Create a new instance of container deserializer.
         *
         * @param cm Java class deserialization model for current container type
         * @param keyType type of container key
         * @param valueType type of container value
         * @return container deserializer instance
         */
        Container<Void, ?, ?> newInstance(ClassModel cm, Class<?> valueType);
    }

    /**
     * Container instance creator for JSON object deserializers.
     */
    @FunctionalInterface
    interface ObjectContainerBuiler {
        /**
         * Create a new instance of container deserializer.
         *
         * @param cm Java class deserialization model for current container type
         * @param keyType type of container key
         * @param valueType type of container value
         * @return container deserializer instance
         */
        Container<?, ?, ?> newInstance(ClassModel cm, Class<?> keyType, Class<?> valueType);
    }

    private static final Map<Type, ArrayContainerBuiler> ARRAY_CONTAINERS = new HashMap<>(16);
    private static final Map<Type, ObjectContainerBuiler> OBJECT_CONTAINERS = new HashMap<>(16);

    static {
        ARRAY_CONTAINERS.put(byte[].class, ContainerArrayFromArray.PrimitiveByte::newInstance);
        ARRAY_CONTAINERS.put(Byte[].class, ContainerArrayFromArray.ObjectByte::newInstance);
        ARRAY_CONTAINERS.put(short[].class, ContainerArrayFromArray.PrimitiveShort::newInstance);
        ARRAY_CONTAINERS.put(Short[].class, ContainerArrayFromArray.ObjectShort::newInstance);
        ARRAY_CONTAINERS.put(int[].class, ContainerArrayFromArray.PrimitiveInteger::newInstance);
        ARRAY_CONTAINERS.put(Integer[].class, ContainerArrayFromArray.ObjectInteger::newInstance);
        ARRAY_CONTAINERS.put(long[].class, ContainerArrayFromArray.PrimitiveLong::newInstance);
        ARRAY_CONTAINERS.put(Long[].class, ContainerArrayFromArray.ObjectLong::newInstance);
        ARRAY_CONTAINERS.put(float[].class, ContainerArrayFromArray.PrimitiveFloat::newInstance);
        ARRAY_CONTAINERS.put(Float[].class, ContainerArrayFromArray.ObjectFloat::newInstance);
        ARRAY_CONTAINERS.put(double[].class, ContainerArrayFromArray.PrimitiveDouble::newInstance);
        ARRAY_CONTAINERS.put(Double[].class, ContainerArrayFromArray.ObjectDouble::newInstance);
        ARRAY_CONTAINERS.put(BigInteger[].class, ContainerArrayFromArray.ObjectBigInteger::newInstance);
        ARRAY_CONTAINERS.put(BigDecimal[].class, ContainerArrayFromArray.ObjectBigDecimal::newInstance);
        ARRAY_CONTAINERS.put(List.class, ContainerListFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(ArrayList.class, ContainerListFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(LinkedList.class, ContainerListFromArray.AsLinkedList::newInstance);
        OBJECT_CONTAINERS.put(Object.class, ContainerHashMapFromObject::newInstance);
        OBJECT_CONTAINERS.put(Map.class, ContainerHashMapFromObject::newInstance);
        OBJECT_CONTAINERS.put(HashMap.class, ContainerHashMapFromObject::newInstance);
    }

    private final Map<Type, ArrayContainerBuiler> arrayContainers;
    private final Map<Type, ObjectContainerBuiler> objectContainers;

    /**
     * Creates an instance of containers deserializers selector.
     *
     * @param jsonbContext current JSON-B context
     */
    public Containers(JsonbContext jsonbContext) {
        arrayContainers = new HashMap<>(ARRAY_CONTAINERS);
        objectContainers = new HashMap<>(OBJECT_CONTAINERS);
    }

    /**
     * Select container deserializer for JSON array.
     *
     * @param cm class model
     * @param valueType type of container value
     * @return container deserializer for JSON array
     */
    public Container<Void, ?, ?> arrayContainer(ClassModel cm, Class<?> valueType) {
        return arrayContainers.getOrDefault(
                cm.getType(), ContainerArrayFromArray.ObjectArray::newInstance).newInstance(cm, valueType);
    }

    /**
     * Select container deserializer for JSON object.
     *
     * @param cm class model
     * @param keyType type of container key
     * @param valueType type of container value
     * @return container deserializer for JSON array
     */
    public Container<?, ?, ?> objectContainer(ClassModel cm, Class<?> keyType, Class<?> valueType) {
        return objectContainers.get(cm.getType()).newInstance(cm, keyType, valueType);
    }

}
