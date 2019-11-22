package org.eclipse.yasson.internal.deserializer.deserializers;

import java.math.BigDecimal;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Object} value.
 */
public final class DeserializerValueObject extends Deserializer<Object> {

    static final Deserializer<Object> INSTANCE = new DeserializerValueObject();

    private DeserializerValueObject() {
    }

    @Override
    public Object stringValue(ParserContext uCtx) {
        return uCtx.getParser().getString();
    }

    @Override
    public Object numberValue(ParserContext uCtx) {
        return new BigDecimal(uCtx.getParser().getString());
    }

    @Override
    public Object trueValue(ParserContext uCtx) {
        return Boolean.TRUE;
    }

    @Override
    public Object falseValue(ParserContext uCtx) {
        return Boolean.FALSE;
    }

    @Override
    public Object nullValue(ParserContext uCtx) {
        return null;
    }

}
