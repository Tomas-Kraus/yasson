package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Integer} value.
 */
public final class DeserializerValueInteger extends Deserializer<Integer> {

    static final Deserializer<Integer> INSTANCE = new DeserializerValueInteger();

    private static final Integer VALUE_TRUE = Integer.valueOf(1);
    
    private static final Integer VALUE_FALSE = Integer.valueOf(0);

    private DeserializerValueInteger() {
    }

    @Override
    public Integer stringValue(ParserContext uCtx) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

    @Override
    public Integer numberValue(ParserContext uCtx) {
        return Integer.parseInt(uCtx.getParser().getString());
    }

    @Override
    public Integer trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Integer falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Integer nullValue(ParserContext uCtx) {
        return null;
    }

}