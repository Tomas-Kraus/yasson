package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import javax.json.JsonValue;

public final class DeserializerValueJsonValue extends Deserializer<JsonValue> {

    static final Deserializer<JsonValue> INSTANCE = new DeserializerValueJsonValue();

    private static final JsonValue VALUE_TRUE = JsonValue.TRUE;

    private static final JsonValue VALUE_FALSE = JsonValue.FALSE;

    private static final JsonValue VALUE_NULL = JsonValue.NULL;

    private DeserializerValueJsonValue() {
    }

    @Override
    public JsonValue stringValue(ParserContext uCtx) { return uCtx.getParser().getValue(); }

    @Override
    public JsonValue numberValue(ParserContext uCtx) { return uCtx.getParser().getValue(); }

    @Override
    public JsonValue trueValue(ParserContext uCtx) {
        return VALUE_TRUE;
    }

    @Override
    public JsonValue falseValue(ParserContext uCtx) {
        return VALUE_FALSE;
    }

    @Override
    public JsonValue nullValue(ParserContext uCtx) {
        return VALUE_NULL;
    }

}