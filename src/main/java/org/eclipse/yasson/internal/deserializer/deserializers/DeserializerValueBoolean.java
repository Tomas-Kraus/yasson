package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Boolean} value.
 */
public final class DeserializerValueBoolean extends Deserializer<Boolean> {

    static final Deserializer<Boolean> INSTANCE = new DeserializerValueBoolean();

    private DeserializerValueBoolean() {
    }

    @Override
    public Boolean deserialize(ParserContext uCtx) {
        return Boolean.parseBoolean(uCtx.getParser().getString());
    }

}
