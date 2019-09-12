package org.eclipse.yasson.internal.deserializer.deserializers;

import java.math.BigDecimal;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link BigDecimal} value.
 */
public final class DeserializerValueBigDecimal extends Deserializer<BigDecimal> {

    static final Deserializer<BigDecimal> INSTANCE = new DeserializerValueBigDecimal();

    private DeserializerValueBigDecimal() {
    }

    @Override
    public BigDecimal deserialize(ParserContext uCtx) {
        return new BigDecimal(uCtx.getParser().getString());
    }

}
