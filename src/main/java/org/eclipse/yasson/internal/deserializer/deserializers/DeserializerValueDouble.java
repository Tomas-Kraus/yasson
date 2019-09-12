package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Double} value.
 */
public final class DeserializerValueDouble extends Deserializer<Double> {

    static final Deserializer<Double> INSTANCE = new DeserializerValueDouble();

    private DeserializerValueDouble() {
    }

    @Override
    public Double deserialize(ParserContext uCtx) {
        return Double.parseDouble(uCtx.getParser().getString());
    }

}
