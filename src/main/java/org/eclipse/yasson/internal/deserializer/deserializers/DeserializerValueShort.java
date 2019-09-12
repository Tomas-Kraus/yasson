package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Short} value.
 */
public final class DeserializerValueShort extends Deserializer<Short> {

    static final Deserializer<Short> INSTANCE = new DeserializerValueShort();

    private DeserializerValueShort() {
    }

    @Override
    public Short deserialize(ParserContext uCtx) {
        return Short.parseShort(uCtx.getParser().getString());
    }

}
