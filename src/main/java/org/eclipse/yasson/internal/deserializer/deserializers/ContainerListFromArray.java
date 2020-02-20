package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * JSON array to Java {@code List} deserializer.
 *
 * @param <V> the type of {@code List} value returned by primitive type deserializer
 */
abstract class ContainerListFromArray<V> extends ContainerCollectionFromArray<V> {

    /** Target Java {@code List} being built from JSON array. */
    private final List<V> list;

    /**
     * Creates an instance of JSON array to Java {@code List} deserializer.
     *
     * @param list target Java {@code List} instance
     * @param containerClass class of the container
     * @param valueType target Java value type of Collection elements
     */
    ContainerListFromArray(final List<V> list, final Class<Collection<V>> containerClass, final Type valueType) {
        super(containerClass, valueType);
        this.list = list;
    }

    /**
     * Add last parsed JSON value to this {@code List}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(V value) {
        list.add(value);

    }

    /**
     * Return target Java {@code List} value built from already added array elements.
     *
     * @param deserialization context
     * @return target Java {@code List} value
     */
    @Override
    public Collection<V> build() {
        return list;
    }

    /**
     * JSON array to Java {@code ArrayList} deserializer.
     *
     * @param <V> the type of {@code ArrayList} value returned by primitive type deserializer
     */
    static final class AsArrayList<V> extends ContainerListFromArray<V> {

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
    static final class AsLinkedList<V> extends ContainerListFromArray<V> {

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

}
