package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import java.util.OptionalLong;

public class DeserializerValueOptionalLongType extends Deserializer<OptionalLong> {

    static final Deserializer<OptionalLong> INSTANCE = new DeserializerValueOptionalLongType();

    private DeserializerValueOptionalLongType(){
    }

    @Override
    public OptionalLong stringValue(ParserContext uCtx) { return OptionalLong.of(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public OptionalLong numberValue(ParserContext uCtx) { return OptionalLong.of(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public OptionalLong trueValue(ParserContext uCtx) { return OptionalLong.of(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public OptionalLong falseValue(ParserContext uCtx) { return OptionalLong.of(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public OptionalLong nullValue(ParserContext uCtx) {
        return null;
    }
}
