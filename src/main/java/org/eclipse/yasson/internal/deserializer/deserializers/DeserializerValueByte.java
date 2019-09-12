package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Byte} value.
 */
public final class DeserializerValueByte extends Deserializer<Byte> {

    static final Deserializer<Byte> INSTANCE = new DeserializerValueByte();

    private DeserializerValueByte() {
    }

    @Override
    public Byte deserialize(ParserContext uCtx) {
        return Byte.parseByte(uCtx.getParser().getString());
    }

}
