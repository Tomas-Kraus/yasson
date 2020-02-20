package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.yasson.internal.JsonbContext;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerGenericArrayFromArray.ComponentType;

/**
 * Containers deserializers selection utilities.
 */
public final class Containers {

    /**
     * Container instance creator for JSON array deserializers.
     *
     * @param <V> the type of container value
     * @param <T> the type of returned value
     */
    @FunctionalInterface
    public interface ArrayContainerBuilder<V, T> {
        /**
         * Create a new instance of container deserializer.
         *
         * @param containerClass class of the container
         * @param valueType type of container value
         * @return container deserializer instance
         */
        ContainerArray<V, T> newInstance(Class<T> containerClass, Type valueType);
    }

    /**
     * Container instance creator for JSON object deserializers.
     *
     * @param <K> the type of container key
     * @param <V> the type of container value
     * @param <T> the type of returned value
     */
    @FunctionalInterface
    public interface ObjectContainerBuilder<K, V, T> {
        /**
         * Create a new instance of container deserializer.
         *
         * @param containerClass class of the container
         * @param keyType type of container key
         * @param valueType type of container value
         * @return container deserializer instance
         */
        ContainerObject<K, V, T> newInstance(Class<T> containerClass, Type keyType, Type valueType);
    }

    private static final Map<Type, ArrayContainerBuilder> ARRAY_CONTAINERS = new HashMap<>(16);
    private static final Map<Type, ObjectContainerBuilder> OBJECT_CONTAINERS = new HashMap<>(16);

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
        ARRAY_CONTAINERS.put(char[].class, ContainerArrayFromArray.PrimitiveChar::newInstance);
        ARRAY_CONTAINERS.put(Character[].class, ContainerArrayFromArray.ObjectChar::newInstance);
        ARRAY_CONTAINERS.put(String[].class, ContainerArrayFromArray.StringArray::newInstance);
        ARRAY_CONTAINERS.put(List.class, ContainerListFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(Object.class, ContainerListFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(ArrayList.class, ContainerListFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(LinkedList.class, ContainerListFromArray.AsLinkedList::newInstance);
        ARRAY_CONTAINERS.put(ComponentType.class, ContainerGenericArrayFromArray::newInstance);
        ARRAY_CONTAINERS.put(HashMap.class, ContainerHashMapFromArray::newInstance);
        ARRAY_CONTAINERS.put(Map.class, ContainerHashMapFromArray::newInstance);
        ARRAY_CONTAINERS.put(Optional.class, ContainerOptional::newArrayInstance);
        OBJECT_CONTAINERS.put(Object.class, ContainerHashMapFromObject::newInstance);
        OBJECT_CONTAINERS.put(Map.class, ContainerHashMapFromObject::newInstance);
        OBJECT_CONTAINERS.put(HashMap.class, ContainerHashMapFromObject::newInstance);
        OBJECT_CONTAINERS.put(ContainerHashMapItemFromArray.class, ContainerHashMapItemFromArray::newInstance);
        OBJECT_CONTAINERS.put(Optional.class, ContainerOptional::newObjectInstance);
    }

    private final Map<Type, ArrayContainerBuilder> arrayContainers;
    private final Map<Type, ObjectContainerBuilder> objectContainers;

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
     * Select container deserializer builder for JSON array.
     *
     * @param containerClass class of the container
     * @return container deserializer for JSON array
     */
    public ArrayContainerBuilder arrayContainerBuilder(Class<?> containerClass) {
        return arrayContainers.get(containerClass);
    }

    /**
     * Select container deserializer for JSON array.
     *
     * @param containerClass class of the container
     * @param valueType type of container value
     * @return container deserializer for JSON array
     */
    public ContainerArray<?, ?> arrayContainer(Class<?> containerClass, Type valueType) {
        return arrayContainers.getOrDefault(containerClass, ContainerArrayFromArray.ObjectArray::newInstance)
                .newInstance(containerClass, valueType);
    }

    /**
     * Select container deserializer for JSON array.
     *
     * @param type type of container
     * @param containerClass class of the container
     * @param valueType type of container value
     * @return container deserializer for JSON array
     */
    public ContainerArray<?, ?> arrayContainer(Type type, Class<?> containerClass, Type valueType) {
        return arrayContainers.getOrDefault(
                type, ContainerArrayFromArray.ObjectArray::newInstance).newInstance(containerClass, valueType);
    }

    /**
     * Select container deserializer builder for JSON object.
     *
     * @param containerClass class of the container
     * @return container deserializer for JSON array
     */
    public ObjectContainerBuilder objectContainerBuilder(Class<?> containerClass) {
        return objectContainers.get(containerClass);
    }

    /**
     * Select container deserializer for JSON object.
     *
     * @param containerClass class of the container
     * @param keyType type of container key
     * @param valueType type of container value
     * @return container deserializer for JSON array
     */
    public ContainerObject<?, ?, ?> objectContainer(Class<?> containerClass, Type keyType, Type valueType) {
        return objectContainerBuilder(containerClass).newInstance(containerClass, keyType, valueType);
    }



}
