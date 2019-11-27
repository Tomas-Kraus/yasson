package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import javax.json.JsonArray;

public final class DeserializerValueJsonArray extends Deserializer<JsonArray>{

    static final Deserializer<JsonArray> INSTANCE = new DeserializerValueJsonArray();

    private DeserializerValueJsonArray(){
    }

    @Override
    public JsonArray stringValue(ParserContext uCtx) { return uCtx.getParser().getArray(); }

    @Override
    public JsonArray numberValue(ParserContext uCtx) { return uCtx.getParser().getArray(); }

    @Override
    public JsonArray trueValue(ParserContext uCtx) { return uCtx.getParser().getArray(); }

    @Override
    public JsonArray falseValue(ParserContext uCtx) { return uCtx.getParser().getArray(); }

    @Override
    public JsonArray nullValue(ParserContext uCtx) {
        return null;
    }
}
