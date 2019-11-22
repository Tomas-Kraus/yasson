package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.JsonbContext;

/**
 * Primitive types deserializers selection utilities.
 */
public final class Deserializers {

    private static final Map<Type, Deserializer<?>> DESERIALIZERS = new HashMap<>(19);

    static {
        DESERIALIZERS.put(boolean.class, DeserializerValueBoolean.INSTANCE);
        DESERIALIZERS.put(Boolean.class, DeserializerValueBoolean.INSTANCE);
        DESERIALIZERS.put(String.class, DeserializerValueString.INSTANCE);
        DESERIALIZERS.put(byte.class, DeserializerValueByte.INSTANCE);
        DESERIALIZERS.put(Byte.class, DeserializerValueByte.INSTANCE);
        DESERIALIZERS.put(short.class, DeserializerValueShort.INSTANCE);
        DESERIALIZERS.put(Short.class, DeserializerValueShort.INSTANCE);
        DESERIALIZERS.put(int.class, DeserializerValueInteger.INSTANCE);
        DESERIALIZERS.put(Integer.class, DeserializerValueInteger.INSTANCE);
        DESERIALIZERS.put(long.class, DeserializerValueLong.INSTANCE);
        DESERIALIZERS.put(Long.class, DeserializerValueLong.INSTANCE);
        DESERIALIZERS.put(float.class, DeserializerValueFloat.INSTANCE);
        DESERIALIZERS.put(Float.class, DeserializerValueFloat.INSTANCE);
        DESERIALIZERS.put(double.class, DeserializerValueDouble.INSTANCE);
        DESERIALIZERS.put(Double.class, DeserializerValueDouble.INSTANCE);
        DESERIALIZERS.put(BigInteger.class, DeserializerValueBigInteger.INSTANCE);
        DESERIALIZERS.put(BigDecimal.class, DeserializerValueBigDecimal.INSTANCE);
        DESERIALIZERS.put(Number.class, DeserializerValueBigDecimal.INSTANCE);
        DESERIALIZERS.put(Object.class, DeserializerValueObject.INSTANCE);
    }

    private final Map<Type, Deserializer<?>> deserializers;

    /**
     * Creates an instance of primitive types deserializers selectior.
     *
     * @param jsonbContext current JSON-B context
     */
    public Deserializers(JsonbContext jsonbContext) {
        deserializers = new HashMap<>(DESERIALIZERS);
    }

    /**
     * Get deserializer of JSON {@code string} or {@code number} value.
     *
     * @param type type of value to be returned
     * @return deserializer of JSON {@code string} or {@code number} value
     */
    public Deserializer<?> deserializer(Type type) {
        return deserializers.get(type);
    }

}
