package org.eclipse.yasson.internal.deserializer.deserializers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.yasson.internal.model.ClassModel;

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
     * @param target Java {@code List} instance
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    ContainerListFromArray(List<V> list, Class<?> valueType, final ClassModel classModel) {
        super(valueType, classModel);
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
         * @param cm Java class model
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code ArrayList} deserializer
         */
        static <V> AsArrayList<V> newInstance(ClassModel cm, Class<V> valueType) {
            return new AsArrayList<>(valueType, cm);
        }

        /**
         * Creates an instance of JSON array to Java {@code ArrayList} deserializer.
         *
         * @param valueType target Java value type of Collection elements
         * @param classModel Java class model of the container type
         */
        AsArrayList(Class<?> valueType, ClassModel classModel) {
            super(new ArrayList<>(), valueType, classModel);
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
         * @param cm Java class model
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to Java {@code LinkedList} deserializer
         */
        static <V> AsLinkedList<V> newInstance(ClassModel cm, Class<V> valueType) {
            return new AsLinkedList<>(valueType, cm);
        }

        /**
         * Creates an instance of JSON array to Java {@code LinkedList} deserializer.
         *
         * @param valueType target Java value type of Collection elements
         * @param classModel Java class model of the container type
         */
        AsLinkedList(Class<?> valueType, ClassModel classModel) {
            super(new LinkedList<>(), valueType, classModel);
        }

    }

}
