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

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.deserializer.ResolveType;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON array to Java array deserializer.
 *
 * @param <V> the type of array value returned by primitive type deserializer
 * @param <T> the type of returned array
 */
abstract class ContainerArrayFromArray<V, T> extends ContainerArray<V, T> {

    /** Target array stored in List. */
    private final ArrayList<V> list;

    /** Current value type (the same for all array elements). */
    private final Type valueType;

    /** Current value class (the same for all array elements). */
    private final Class<V> valueClass;

    /** Array components customizations. */
    private Customization customization;

    /** Array components class model. */
    private ClassModel valueClassModel;

    /**
     * Creates an instance of JSON array to Java array deserializer.
     *
     * @param containerClass class of the container
     * @param valueType target Java value type of the container elements
     */
    @SuppressWarnings("unchecked")
    ContainerArrayFromArray(final Class<T> containerClass, final Type valueType) {
        super(containerClass);
        this.list = new ArrayList<>();
        this.valueType = valueType;
        this.valueClass = (Class<V>) ResolveType.resolveGenericType(valueType);
    }

    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        valueClassModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueClass);
        customization = valueClassModel.getClassCustomization();
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

    @Override
    public Type valueType() {
        return valueType;
    }

    public Class<V> valueClass() {
        return valueClass;
    }

    @Override
    public Customization valueCustomization() {
        return customization;
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

        /**
         * Get new instance of JSON array to objects array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements
         * @return new instance of JSON array to objects array deserializer
         */
        static <V> ContainerArrayFromArray.ObjectArray<V>
        newInstance(final Class<V[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectArray<>(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to objects array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements
         */
        private ObjectArray(final Class<V[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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
            final V[] array = (V[]) Array.newInstance(valueClass(), getList().size());
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

    }

    /**
     * JSON array to byte array deserializer.
     */
    static final class PrimitiveByte extends ContainerArrayFromArray<Byte, byte[]> {

        /**
         * Get new instance of JSON array to byte array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to byte array deserializer
         */
        static ContainerArrayFromArray.PrimitiveByte
        newInstance(final Class<byte[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveByte(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to byte array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveByte(final Class<byte[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class ObjectByte extends ContainerArrayFromArray<Byte, Byte[]> {

        /**
         * Get new instance of JSON array to Byte array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to byte array deserializer
         */
        static ContainerArrayFromArray.ObjectByte
        newInstance(final Class<Byte[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectByte(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Byte array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectByte(final Class<Byte[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to short array deserializer.
     */
    static final class PrimitiveShort extends ContainerArrayFromArray<Short, short[]> {

        /**
         * Get new instance of JSON array to short array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to short array deserializer
         */
        static ContainerArrayFromArray.PrimitiveShort
        newInstance(final Class<short[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveShort(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to short array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveShort(final Class<short[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Short array deserializer.
     */
    static final class ObjectShort extends ContainerArrayFromArray<Short, Short[]> {

        /**
         * Get new instance of JSON array to Short array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Short array deserializer
         */
        static ContainerArrayFromArray.ObjectShort
        newInstance(final Class<Short[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectShort(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Short array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectShort(final Class<Short[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to int array deserializer.
     */
    static final class PrimitiveInteger extends ContainerArrayFromArray<Integer, int[]> {

        /**
         * Get new instance of JSON array to int array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to int array deserializer
         */
        static ContainerArrayFromArray.PrimitiveInteger
        newInstance(final Class<int[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveInteger(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to int array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveInteger(final Class<int[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Integer array deserializer.
     */
    static final class ObjectInteger extends ContainerArrayFromArray<Integer, Integer[]> {

        /**
         * Get new instance of JSON array to Integer array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Integer array deserializer
         */
        static ContainerArrayFromArray.ObjectInteger
        newInstance(final Class<Integer[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectInteger(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Integer array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectInteger(final Class<Integer[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to long array deserializer.
     */
    static final class PrimitiveLong extends ContainerArrayFromArray<Long, long[]> {

        /**
         * Get new instance of JSON array to long array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to long array deserializer
         */
        static ContainerArrayFromArray.PrimitiveLong
        newInstance(final Class<long[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveLong(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to long array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveLong(final Class<long[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Long array deserializer.
     */
    static final class ObjectLong extends ContainerArrayFromArray<Long, Long[]> {

        /**
         * Get new instance of JSON array to Long array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Long array deserializer
         */
        static ContainerArrayFromArray.ObjectLong
        newInstance(final Class<Long[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectLong(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Long array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectLong(final Class<Long[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to float array deserializer.
     */
    static final class PrimitiveFloat extends ContainerArrayFromArray<Float, float[]> {

        /**
         * Get new instance of JSON array to float array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to float array deserializer
         */
        static ContainerArrayFromArray.PrimitiveFloat
        newInstance(final Class<float[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveFloat(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to float array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveFloat(final Class<float[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Float array deserializer.
     */
    static final class ObjectFloat extends ContainerArrayFromArray<Float, Float[]> {

        /**
         * Get new instance of JSON array to Float array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Float array deserializer
         */
        static ContainerArrayFromArray.ObjectFloat
        newInstance(final Class<Float[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectFloat(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to Float array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectFloat(final Class<Float[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to double array deserializer.
     */
    static final class PrimitiveDouble extends ContainerArrayFromArray<Double, double[]> {

        /**
         * Get new instance of JSON array to double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to double array deserializer
         */
        static ContainerArrayFromArray.PrimitiveDouble
        newInstance(final Class<double[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveDouble(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveDouble(final Class<double[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class ObjectDouble extends ContainerArrayFromArray<Double, Double[]> {

        /**
         * Get new instance of JSON array to Double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Double array deserializer
         */
        static ContainerArrayFromArray.ObjectDouble
        newInstance(final Class<Double[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectDouble(containerClass, valueType);
        }

       /**
        * Creates an instance of JSON array to Double array deserializer.
        *
        * @param containerClass class of the container
        * @param valueType target Java value type of array elements (ignored)
        */
        private ObjectDouble(final Class<Double[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to BigInteger array deserializer.
     */
    static final class ObjectBigInteger extends ContainerArrayFromArray<BigInteger, BigInteger[]> {

       /**
        * Get new instance of JSON array to BigInteger array deserializer.
        *
        * @param containerClass class of the container
        * @param valueType target Java value type of array elements (ignored)
        * @return new instance of JSON array to BigInteger array deserializer
        */
        static ContainerArrayFromArray.ObjectBigInteger
        newInstance(final Class<BigInteger[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectBigInteger(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to BigInteger array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectBigInteger(final Class<BigInteger[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

   /**
    * JSON array to BigDecimal array deserializer.
    */
    static final class ObjectBigDecimal extends ContainerArrayFromArray<BigDecimal, BigDecimal[]> {

        /**
         * Get new instance of JSON array to BigDecimal array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to BigDecimal array deserializer
         */
        static ContainerArrayFromArray.ObjectBigDecimal
        newInstance(final Class<BigDecimal[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectBigDecimal(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to BigDecimal array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private ObjectBigDecimal(final Class<BigDecimal[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
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

    }

    /**
     * JSON array to double array deserializer.
     */
    static final class PrimitiveChar extends ContainerArrayFromArray<Character, char[]> {

        /**
         * Get new instance of JSON array to double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to double array deserializer
         */
        static ContainerArrayFromArray.PrimitiveChar
        newInstance(final Class<char[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.PrimitiveChar(containerClass, valueType);
        }

        /**
         * Creates an instance of JSON array to double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         */
        private PrimitiveChar(final Class<char[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
        }

        /**
         * Build target Java double array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java double array value
         */
        @Override
        public char[] build() {
            final char[] array = new char[getList().size()];
            for (int i = 0; i < getList().size(); i++) {
                array[i] = getList().get(i);
            }
            return array;
        }

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class ObjectChar extends ContainerArrayFromArray<Character, Character[]> {

        /**
         * Get new instance of JSON array to Double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Double array deserializer
         */
        static ContainerArrayFromArray.ObjectChar
        newInstance(final Class<Character[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.ObjectChar(containerClass, valueType);
        }

       /**
        * Creates an instance of JSON array to Double array deserializer.
        *
        * @param containerClass class of the container
        * @param valueType target Java value type of array elements (ignored)
        */
        private ObjectChar(final Class<Character[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
        }

        /**
         * Build target Java Double array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Double array value
         */
        @Override
        public Character[] build() {
            return getList().toArray(new Character[getList().size()]);
        }

    }

    /**
     * JSON array to Byte array deserializer.
     */
    static final class StringArray extends ContainerArrayFromArray<String, String[]> {

        /**
         * Get new instance of JSON array to Double array deserializer.
         *
         * @param containerClass class of the container
         * @param valueType target Java value type of array elements (ignored)
         * @return new instance of JSON array to Double array deserializer
         */
        static ContainerArrayFromArray.StringArray newInstance(final Class<String[]> containerClass, final Type valueType) {
            return new ContainerArrayFromArray.StringArray(containerClass, valueType);
        }

       /**
        * Creates an instance of JSON array to Double array deserializer.
        *
        * @param containerClass class of the container
        * @param valueType target Java value type of array elements (ignored)
        */
        private StringArray(final Class<String[]> containerClass, final Type valueType) {
            super(containerClass, valueType);
        }

        /**
         * Build target Java Double array value from array elements already stored in this container.
         *
         * @param deserialization context
         * @return target Java Double array value
         */
        @Override
        public String[] build() {
            return getList().toArray(new String[getList().size()]);
        }

    }

}
