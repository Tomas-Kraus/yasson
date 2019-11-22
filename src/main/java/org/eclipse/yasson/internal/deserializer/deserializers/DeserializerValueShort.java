package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Short} value.
 */
public final class DeserializerValueShort extends Deserializer<Short> {

    static final Deserializer<Short> INSTANCE = new DeserializerValueShort();

    private static final Short VALUE_TRUE = Short.valueOf((short)1);
    
    private static final Short VALUE_FALSE = Short.valueOf((short)0);

    private DeserializerValueShort() {
    }

    @Override
    public Short stringValue(ParserContext uCtx) {
        return Short.parseShort(uCtx.getParser().getString());
    }

    @Override
    public Short numberValue(ParserContext uCtx) {
        return Short.parseShort(uCtx.getParser().getString());
    }

    @Override
    public Short trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public Short falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public Short nullValue(ParserContext uCtx) {
        return null;
    }

}
