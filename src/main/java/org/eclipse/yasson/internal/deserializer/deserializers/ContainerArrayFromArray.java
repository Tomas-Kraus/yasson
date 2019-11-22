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

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.eclipse.yasson.internal.model.ClassModel;

/**
 * JSON array to Java array deserializer.
 *
 * @param <V> the type of array value returned by primitive type deserializer
 * @param <T> the type of returned array
 */
abstract class ContainerArrayFromArray<V, T> extends Container<Void, V, T> {

    /** Target array stored in List. */
    private final ArrayList<V> list;

    /**
     * Creates an instance of JSON array to Java array deserializer.
     */
    ContainerArrayFromArray() {
        this.list = new ArrayList<>();
    }

    /**
     * Add last parsed JSON value to this container.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(V value) {
        getList().add(value);
    }

    /**
     * Attempt to get current key type throws an exception for JSON array.
     *
     * @return never returns current key type
     */
    @Override
    public final Type keyType() {
        throw new IllegalStateException("Key type is not supported in array containers");
    }

    /**
     * Get target array stored in List.
     *
     * @return target array stored in List
     */
    ArrayList<V> getList() {
        return list;
    }

    /**
     * JSON array to objects array deserializer.
     */
    static final class ObjectArray<V> extends ContainerArrayFromArray<V, V[]> {

        /** Current value type (the same for all array elements). */
        private final Class<V> valueType;

        /**
         * Get new instance of JSON array to objects array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to objects array deserializer
         */
        static ContainerArrayFromArray.ObjectArray<?> newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectArray<>(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to objects array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements
         */
        @SuppressWarnings("unchecked")
        private ObjectArray(ClassModel cm, Class<?> valueType) {
            this.valueType = (Class<V>) valueType;
        }

        /**
         * Build target Java array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java array value
         */
        @Override
        public V[] build() {
            @SuppressWarnings("unchecked")
            final V[] array = (V[]) Array.newInstance(valueType, getList().size());
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type
         */
        @Override
        public Type valueType() {
            return valueType;
        }

    }

    /**
     * JSON array to byte array deserializer.
     */
    static final class PrimitiveByte extends ContainerArrayFromArray<Byte, byte[]> {

        /**
         * Get new instance of JSON array to byte array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to byte array deserializer
         */
        static ContainerArrayFromArray.PrimitiveByte newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveByte(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to byte array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveByte(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java byte array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java byte array value
         */
        @Override
        public byte[] build() {
            final byte[] array = new byte[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Byte.class}
         */
        @Override
        public Type valueType() {
            return Byte.class;
        }

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class ObjectByte extends ContainerArrayFromArray<Byte, Byte[]> {

        /**
         * Get new instance of JSON array to Byte array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to byte array deserializer
         */
        static ContainerArrayFromArray.ObjectByte newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectByte(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to Byte array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectByte(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Byte array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Byte array value
         */
        @Override
        public Byte[] build() {
            return getList().toArray(new Byte[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Byte.class}
         */
        @Override
        public Type valueType() {
            return Byte.class;
        }

    }

    /**
     * JSON array to short array deserializer.
     */
    static final class PrimitiveShort extends ContainerArrayFromArray<Short, short[]> {

        /**
         * Get new instance of JSON array to short array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to short array deserializer
         */
        static ContainerArrayFromArray.PrimitiveShort newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveShort(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to short array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveShort(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java short array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java short array value
         */
        @Override
        public short[] build() {
            final short[] array = new short[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Short.class}
         */
        @Override
        public Type valueType() {
            return Short.class;
        }

    }

    /**
     * JSON array to Short array deserializer.
     */
    static final class ObjectShort extends ContainerArrayFromArray<Short, Short[]> {

        /**
         * Get new instance of JSON array to Short array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Short array deserializer
         */
        static ContainerArrayFromArray.ObjectShort newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectShort(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to Short array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectShort(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Short array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Short array value
         */
        @Override
        public Short[] build() {
            return getList().toArray(new Short[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Short.class}
         */
        @Override
        public Type valueType() {
            return Short.class;
        }

    }

    /**
     * JSON array to int array deserializer.
     */
    static final class PrimitiveInteger extends ContainerArrayFromArray<Integer, int[]> {

        /**
         * Get new instance of JSON array to int array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to int array deserializer
         */
        static ContainerArrayFromArray.PrimitiveInteger newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveInteger(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to int array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveInteger(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java int array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java int array value
         */
        @Override
        public int[] build() {
            final int[] array = new int[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Integer.class}
         */
        @Override
        public Type valueType() {
            return Integer.class;
        }

    }

    /**
     * JSON array to Integer array deserializer.
     */
    static final class ObjectInteger extends ContainerArrayFromArray<Integer, Integer[]> {

        /**
         * Get new instance of JSON array to Integer array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Integer array deserializer
         */
        static ContainerArrayFromArray.ObjectInteger newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectInteger(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to Integer array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectInteger(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Integer array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Integer array value
         */
        @Override
        public Integer[] build() {
            return getList().toArray(new Integer[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Integer.class}
         */
        @Override
        public Type valueType() {
            return Integer.class;
        }

    }

    /**
     * JSON array to long array deserializer.
     */
    static final class PrimitiveLong extends ContainerArrayFromArray<Long, long[]> {

        /**
         * Get new instance of JSON array to long array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to long array deserializer
         */
        static ContainerArrayFromArray.PrimitiveLong newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveLong(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to long array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveLong(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java long array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java long array value
         */
        @Override
        public long[] build() {
            final long[] array = new long[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Long.class}
         */
        @Override
        public Type valueType() {
            return Long.class;
        }

    }

    /**
     * JSON array to Long array deserializer.
     */
    static final class ObjectLong extends ContainerArrayFromArray<Long, Long[]> {

        /**
         * Get new instance of JSON array to Long array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Long array deserializer
         */
        static ContainerArrayFromArray.ObjectLong newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectLong(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to Long array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectLong(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Long array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Long array value
         */
        @Override
        public Long[] build() {
            return getList().toArray(new Long[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Long.class}
         */
        @Override
        public Type valueType() {
            return Long.class;
        }

    }

    /**
     * JSON array to float array deserializer.
     */
    static final class PrimitiveFloat extends ContainerArrayFromArray<Float, float[]> {

        /**
         * Get new instance of JSON array to float array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to float array deserializer
         */
        static ContainerArrayFromArray.PrimitiveFloat newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveFloat(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to float array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveFloat(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java float array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java float array value
         */
        @Override
        public float[] build() {
            final float[] array = new float[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Float.class}
         */
        @Override
        public Type valueType() {
            return Float.class;
        }

    }

    /**
     * JSON array to Float array deserializer.
     */
    static final class ObjectFloat extends ContainerArrayFromArray<Float, Float[]> {

        /**
         * Get new instance of JSON array to Float array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Float array deserializer
         */
        static ContainerArrayFromArray.ObjectFloat newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectFloat(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to Float array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectFloat(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Float array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Float array value
         */
        @Override
        public Float[] build() {
            return getList().toArray(new Float[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Float.class}
         */
        @Override
        public Type valueType() {
            return Float.class;
        }

    }

    /**
     * JSON array to double array deserializer.
     */
    static final class PrimitiveDouble extends ContainerArrayFromArray<Double, double[]> {

        /**
         * Get new instance of JSON array to double array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to double array deserializer
         */
        static ContainerArrayFromArray.PrimitiveDouble newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.PrimitiveDouble(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to double array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveDouble(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java double array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java double array value
         */
        @Override
        public double[] build() {
            final double[] array = new double[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Double.class}
         */
        @Override
        public Type valueType() {
            return Double.class;
        }

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class ObjectDouble extends ContainerArrayFromArray<Double, Double[]> {

        /**
         * Get new instance of JSON array to Double array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Double array deserializer
         */
       static ContainerArrayFromArray.ObjectDouble newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectDouble(cm, valueType);
        }

       /**
        * Creates an instance of JSON array to Double array deserializer.
        *
        * @param cm Java class model (ignored)
        * @param valueType target Java value type of array elements (ignored)
        */
        private ObjectDouble(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java Double array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Double array value
         */
        @Override
        public Double[] build() {
            return getList().toArray(new Double[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code Double.class}
         */
        @Override
        public Type valueType() {
            return Double.class;
        }

    }

    /**
     * JSON array to BigInteger array deserializer.
     */
    static final class ObjectBigInteger extends ContainerArrayFromArray<BigInteger, BigInteger[]> {

       /**
        * Get new instance of JSON array to BigInteger array deserializer.
        *
        * @param cm Java class model (ignored)
        * @param valueType target Java value type of array elements (ignored)
        * @return new instance of JSON array to BigInteger array deserializer
        */
        static ContainerArrayFromArray.ObjectBigInteger newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectBigInteger(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to BigInteger array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectBigInteger(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java BigInteger array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java BigInteger array value
         */
        @Override
        public BigInteger[] build() {
            return getList().toArray(new BigInteger[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code BigInteger.class}
         */
        @Override
        public Type valueType() {
            return BigInteger.class;
        }

    }

   /**
    * JSON array to BigDecimal array deserializer.
    */
    static final class ObjectBigDecimal extends ContainerArrayFromArray<BigDecimal, BigDecimal[]> {

        /**
         * Get new instance of JSON array to BigDecimal array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to BigDecimal array deserializer
         */
        static ContainerArrayFromArray.ObjectBigDecimal newInstance(ClassModel cm, Class<?> valueType) {
            return new ContainerArrayFromArray.ObjectBigDecimal(cm, valueType);
        }

        /**
         * Creates an instance of JSON array to BigDecimal array deserializer.
         *
         * @param cm Java class model (ignored)
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectBigDecimal(ClassModel cm, Class<?> valueType) {
        }

        /**
         * Build target Java BigDecimal array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java BigDecimal array value
         */
        @Override
        public BigDecimal[] build() {
            return getList().toArray(new BigDecimal[getList().size()]);
        }

        /**
         * Get current value type.
         *
         * @return current value type as {@code BigDecimal.class}
         */
        @Override
        public Type valueType() {
            return BigDecimal.class;
        }

    }

}
