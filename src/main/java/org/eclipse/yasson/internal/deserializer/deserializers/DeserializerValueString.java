package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link String} value.
 */
public final class DeserializerValueString extends Deserializer<String> {

    static final Deserializer<String> INSTANCE = new DeserializerValueString();

    private DeserializerValueString() {
    }

    @Override
    public String deserialize(ParserContext uCtx) {
        return uCtx.getParser().getString();
    }

}
