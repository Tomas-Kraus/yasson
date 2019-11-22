package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Long} value.
 */
public final class DeserializerValueLong extends Deserializer<Long> {

    static final Deserializer<Long> INSTANCE = new DeserializerValueLong();

    private static final Long VALUE_TRUE = Long.valueOf(1L);
    
    private static final Long VALUE_FALSE = Long.valueOf(0L);

    private DeserializerValueLong() {
    }

    @Override
    public Long stringValue(ParserContext uCtx) {
        return Long.parseLong(uCtx.getParser().getString());
    }

    @Override
    public Long numberValue(ParserContext uCtx) {
        return Long.parseLong(uCtx.getParser().getString());
    }

    @Override
    public Long trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Long falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Long nullValue(ParserContext uCtx) {
        return null;
    }

}