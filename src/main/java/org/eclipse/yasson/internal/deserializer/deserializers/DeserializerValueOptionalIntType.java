package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import java.util.OptionalInt;

public class DeserializerValueOptionalIntType extends Deserializer<OptionalInt> {

    static final Deserializer<OptionalInt> INSTANCE = new DeserializerValueOptionalIntType();

    private DeserializerValueOptionalIntType(){
    }

    @Override
    public OptionalInt stringValue(ParserContext uCtx) { return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public OptionalInt numberValue(ParserContext uCtx) { return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public OptionalInt trueValue(ParserContext uCtx) { return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public OptionalInt falseValue(ParserContext uCtx) { return OptionalInt.of(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public OptionalInt nullValue(ParserContext uCtx) {
        return null;
    }
}
