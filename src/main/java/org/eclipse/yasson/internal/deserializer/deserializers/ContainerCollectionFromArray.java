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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.EnumSet;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;
import org.eclipse.yasson.internal.deserializer.YassonDeserializationException;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON array to Java Collection deserializer.
 *
 * @param <V> the type of Collection value returned by primitive type deserializer
 */
public abstract class ContainerCollectionFromArray<V> extends ContainerArray<V, Collection<V>> {

    /** Current value type (the same for all Collection elements). */
    private final Type valueType;

    /** Current value class (the same for all array elements). */
    private final Class<V> valueClass;

    /** Collection components customizations. */
    private Customization customization;

    /** Collection components class model. */
    private ClassModel classModel;

    private Collection<V> collection;

    /**
     * Creates an instance of JSON array to Java Collection deserializer.
     *
     * @param collection target {@link Collection} instance
     * @param containerClass class of the container
     * @param valueType target Java value type of Collection elements
     */
    @SuppressWarnings("unchecked")
    ContainerCollectionFromArray(final Collection<V> collection, final Class<Collection<V>> containerClass, final Type valueType) {
        super(containerClass);
        this.valueType = valueType;
        this.valueClass = (Class<V>) ResolveType.resolveGenericType(valueType);
        this.collection = collection;
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
        classModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
        customization = classModel.getClassCustomization();
    }

    protected Collection<V> getCollection() {
        return collection;
    }

    protected void setCollection(final Collection<V> collection) {
        this.collection = collection;
    }

    /**
     * Add last parsed JSON value to this {@code List}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(V value) {
        collection.add(value);

    }

    /**
     * Return target Java {@code List} value built from already added array elements.
     *
     * @param deserialization context
     * @return target Java {@code List} value
     */
    @Override
    public Collection<V> build() {
        return collection;
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
     * Get collection value type.
     *
     * @return current value type
     */
    @Override
    public final Class<V> valueClass() {
        return valueClass;
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

    public static final class CommonCollection<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code ArrayList} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code ArrayList} deserializer
         */
        public static <V> CommonCollection<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            try {
                return new CommonCollection<>(containerClass, valueType);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
                    | IllegalArgumentException | InstantiationException | SecurityException ex) {
                throw new YassonDeserializationException(
                        "Could not create instance of " + containerClass.getSimpleName() + "collection.", ex);
            }
        }

        /**
         * Creates an instance of JSON array to Java {@code ArrayList} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         * @throws SecurityException
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         * @throws InstantiationException
         */
        CommonCollection(final Class<Collection<V>> containerClass, final Type valueType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
            super(containerClass.getDeclaredConstructor().newInstance(), containerClass, valueType);
        }
    }

    /**
     * JSON array to Java {@code ArrayList} deserializer.
     *
     * @param <V> the type of {@code ArrayList} value returned by primitive type deserializer
     */
    static final class AsArrayList<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code ArrayList} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code ArrayList} deserializer
         */
        static <V> AsArrayList<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsArrayList<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code ArrayList} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsArrayList(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new ArrayList<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code LinkedList} deserializer.
     *
     * @param <V> the type of {@code LinkedList} value returned by primitive type deserializer
     */
    static final class AsLinkedList<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code LinkedList} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code LinkedList} deserializer
         */
        static <V> AsLinkedList<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsLinkedList<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code LinkedList} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsLinkedList(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new LinkedList<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code TreeSet} deserializer.
     *
     * @param <V> the type of {@code TreeSet} value returned by primitive type deserializer
     */
    static final class AsTreeSet<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code TreeSet} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code TreeSet} deserializer
         */
        static <V> AsTreeSet<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsTreeSet<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code TreeSet} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsTreeSet(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new TreeSet<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code HashSet} deserializer.
     *
     * @param <V> the type of {@code HashSet} value returned by primitive type deserializer
     */
    static final class AsHashSet<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code HashSet} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code HashSet} deserializer
         */
        static <V> AsHashSet<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsHashSet<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code HashSet} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsHashSet(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new HashSet<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code LinkedHashSet} deserializer.
     *
     * @param <V> the type of {@code LinkedHashSet} value returned by primitive type deserializer
     */
    static final class AsLinkedHashSet<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code LinkedHashSet} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code LinkedHashSet} deserializer
         */
        static <V> AsLinkedHashSet<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsLinkedHashSet<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code LinkedHashSet} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsLinkedHashSet(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new LinkedHashSet<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code ArrayDeque} deserializer.
     *
     * @param <V> the type of {@code ArrayDeque} value returned by primitive type deserializer
     */
    static final class AsArrayDeque<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code ArrayDeque} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code ArrayDeque} deserializer
         */
        static <V> AsArrayDeque<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsArrayDeque<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code ArrayDeque} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsArrayDeque(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new ArrayDeque<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code PriorityQueue} deserializer.
     *
     * @param <V> the type of {@code PriorityQueue} value returned by primitive type deserializer
     */
    static final class AsPriorityQueue<V> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code PriorityQueue} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code PriorityQueue} deserializer
         */
        static <V> AsPriorityQueue<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsPriorityQueue<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code PriorityQueue} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsPriorityQueue(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new PriorityQueue<>(), containerClass, valueType);
        }

    }

    /**
     * JSON array to Java {@code EnumSet} deserializer.
     *
     * @param <V> the type of {@code EnumSet} value returned by primitive type deserializer
     */
    static final class AsEnumSet<V extends Enum<V>> extends ContainerCollectionFromArray<V> {

        /**
         * Get new instance of JSON array to Java {@code EnumSet} deserializer.
         *
         *@param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code EnumSet} deserializer
         */
        static <V extends Enum<V>> AsEnumSet<V> newInstance(final Class<Collection<V>> containerClass, final Type valueType) {
            return new AsEnumSet<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Java {@code EnumSet} deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of Collection elements
         */
        AsEnumSet(final Class<Collection<V>> containerClass, final Type valueType) {
            super(new LinkedList<>(), containerClass, valueType);
        }

        /**
         * Return target Java {@code List} value built from already added array elements.
         *
         * @param deserialization context
         * @return target Java {@code List} value
         */
        @Override
        public Collection<V> build() {
            return EnumSet.copyOf(getCollection());
        }

    }

}
