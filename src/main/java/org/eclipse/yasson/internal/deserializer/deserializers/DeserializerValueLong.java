package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Long} value.
 */
public final class DeserializerValueLong extends Deserializer<Long> {

    static final Deserializer<Long> INSTANCE = new DeserializerValueLong();

    private DeserializerValueLong() {
    }

    @Override
    public Long deserialize(ParserContext uCtx) {
        return Long.parseLong(uCtx.getParser().getString());
    }

}