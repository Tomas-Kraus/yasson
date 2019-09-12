package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Float} value.
 */
public final class DeserializerValueFloat extends Deserializer<Float> {

    static final Deserializer<Float> INSTANCE = new DeserializerValueFloat();

    private DeserializerValueFloat() {
    }

    @Override
    public Float deserialize(ParserContext uCtx) {
        return Float.parseFloat(uCtx.getParser().getString());
    }

}
