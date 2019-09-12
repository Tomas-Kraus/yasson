package org.eclipse.yasson.internal.deserializer.deserializers;

import java.math.BigInteger;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link BigInteger} value.
 */
public final class DeserializerValueBigInteger extends Deserializer<BigInteger> {

    static final Deserializer<BigInteger> INSTANCE = new DeserializerValueBigInteger();

    private DeserializerValueBigInteger() {
    }

    @Override
    public BigInteger deserialize(ParserContext uCtx) {
        return new BigInteger(uCtx.getParser().getString());
    }

}