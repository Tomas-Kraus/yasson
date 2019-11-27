package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import java.util.OptionalDouble;

public final class DeserializerValueOptionalDoubleType extends Deserializer<OptionalDouble> {

    static final Deserializer<OptionalDouble> INSTANCE = new DeserializerValueOptionalDoubleType();

    private DeserializerValueOptionalDoubleType(){
    }

    @Override
    public OptionalDouble stringValue(ParserContext uCtx) { return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString())); }

    @Override
    public OptionalDouble numberValue(ParserContext uCtx) { return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString())); }

    @Override
    public OptionalDouble trueValue(ParserContext uCtx) { return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString())); }

    @Override
    public OptionalDouble falseValue(ParserContext uCtx) { return OptionalDouble.of(Double.parseDouble(uCtx.getParser().getString())); }

    @Override
    public OptionalDouble nullValue(ParserContext uCtx) {
        return null;
    }
}
