package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Integer} value.
 */
public final class DeserializerValueInteger extends Deserializer<Integer> {

    static final Deserializer<Integer> INSTANCE = new DeserializerValueInteger();

    private DeserializerValueInteger() {
    }

    @Override
    public Integer deserialize(ParserContext uCtx) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

}