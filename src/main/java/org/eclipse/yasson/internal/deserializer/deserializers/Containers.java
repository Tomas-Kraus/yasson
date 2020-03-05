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
package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

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
        ARRAY_CONTAINERS.put(Collection.class, ContainerCollectionFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(List.class, ContainerCollectionFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(Object.class, ContainerCollectionFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(ArrayList.class, ContainerCollectionFromArray.AsArrayList::newInstance);
        ARRAY_CONTAINERS.put(LinkedList.class, ContainerCollectionFromArray.AsLinkedList::newInstance);
        ARRAY_CONTAINERS.put(Set.class, ContainerCollectionFromArray.AsHashSet::newInstance);
        ARRAY_CONTAINERS.put(HashSet.class, ContainerCollectionFromArray.AsHashSet::newInstance);
        ARRAY_CONTAINERS.put(SortedSet.class, ContainerCollectionFromArray.AsTreeSet::newInstance);
        ARRAY_CONTAINERS.put(NavigableSet.class, ContainerCollectionFromArray.AsTreeSet::newInstance);
        ARRAY_CONTAINERS.put(TreeSet.class, ContainerCollectionFromArray.AsTreeSet::newInstance);
        ARRAY_CONTAINERS.put(LinkedHashSet.class, ContainerCollectionFromArray.AsLinkedHashSet::newInstance);
        ARRAY_CONTAINERS.put(TreeSet.class, ContainerCollectionFromArray.AsTreeSet::newInstance);
        ARRAY_CONTAINERS.put(EnumSet.class, ContainerCollectionFromArray.AsEnumSet::newInstance);
        ARRAY_CONTAINERS.put(Queue.class, ContainerCollectionFromArray.AsArrayDeque::newInstance);
        ARRAY_CONTAINERS.put(Deque.class, ContainerCollectionFromArray.AsArrayDeque::newInstance);
        ARRAY_CONTAINERS.put(ArrayDeque.class, ContainerCollectionFromArray.AsArrayDeque::newInstance);
        ARRAY_CONTAINERS.put(PriorityQueue.class, ContainerCollectionFromArray.AsPriorityQueue::newInstance);
        ARRAY_CONTAINERS.put(ComponentType.class, ContainerGenericArrayFromArray::newInstance);
        ARRAY_CONTAINERS.put(Map.class, ContainerMapFromArray.AsHashMap::newInstance);
        ARRAY_CONTAINERS.put(HashMap.class, ContainerMapFromArray.AsHashMap::newInstance);
        ARRAY_CONTAINERS.put(SortedMap.class, ContainerMapFromArray.AsTreeMap::newInstance);
        ARRAY_CONTAINERS.put(NavigableMap.class, ContainerMapFromArray.AsTreeMap::newInstance);
        ARRAY_CONTAINERS.put(TreeMap.class, ContainerMapFromArray.AsTreeMap::newInstance);
        ARRAY_CONTAINERS.put(LinkedHashMap.class, ContainerMapFromArray.AsLinkedHashMap::newInstance);
        ARRAY_CONTAINERS.put(EnumMap.class, ContainerMapFromArray.AsEnumMap::newInstance);
        ARRAY_CONTAINERS.put(Optional.class, ContainerOptional::newArrayInstance);
        ARRAY_CONTAINERS.put(JsonValue.class, ContainerJsonArray::newInstance);
        ARRAY_CONTAINERS.put(JsonArray.class, ContainerJsonArray::newInstance);
        OBJECT_CONTAINERS.put(Object.class, ContainerMapFromObject.AsHashMap::newInstance);
        OBJECT_CONTAINERS.put(Map.class, ContainerMapFromObject.AsHashMap::newInstance);
        OBJECT_CONTAINERS.put(HashMap.class, ContainerMapFromObject.AsHashMap::newInstance);
        OBJECT_CONTAINERS.put(SortedMap.class, ContainerMapFromObject.AsTreeMap::newInstance);
        OBJECT_CONTAINERS.put(NavigableMap.class, ContainerMapFromObject.AsTreeMap::newInstance);
        OBJECT_CONTAINERS.put(TreeMap.class, ContainerMapFromObject.AsTreeMap::newInstance);
        OBJECT_CONTAINERS.put(LinkedHashMap.class, ContainerMapFromObject.AsLinkedHashMap::newInstance);
        OBJECT_CONTAINERS.put(EnumMap.class, ContainerMapFromObject.AsEnumMap::newInstance);
        OBJECT_CONTAINERS.put(ContainerHashMapItemFromArray.class, ContainerHashMapItemFromArray::newInstance);
        OBJECT_CONTAINERS.put(Optional.class, ContainerOptional::newObjectInstance);
        OBJECT_CONTAINERS.put(JsonValue.class, ContainerJsonObject::newInstance);
        OBJECT_CONTAINERS.put(JsonObject.class, ContainerJsonObject::newInstance);
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
