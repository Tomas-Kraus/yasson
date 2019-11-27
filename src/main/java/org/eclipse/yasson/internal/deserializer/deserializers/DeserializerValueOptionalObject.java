package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import java.util.Optional;

public class DeserializerValueOptionalObject extends Deserializer<Optional<?>>{

    static final Deserializer<Optional<?>> INSTANCE = new DeserializerValueOptionalObject();

    private DeserializerValueOptionalObject(){
    }

    @Override
    public Optional<?> stringValue(ParserContext uCtx) { return Optional.of(uCtx.getParser().getString()); }

    @Override
    public Optional<?> numberValue(ParserContext uCtx) { return Optional.of(uCtx.getParser().getValue()); }

    @Override
    public Optional<?> trueValue(ParserContext uCtx) { return Optional.of(uCtx.getParser().getString()); }

    @Override
    public Optional<?> falseValue(ParserContext uCtx) { return Optional.of(uCtx.getParser().getString()); }

    @Override
    public Optional<?> nullValue(ParserContext uCtx) {
        return null;
    }
}
