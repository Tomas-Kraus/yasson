package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import javax.json.Json;
import javax.json.JsonString;

public final class DeserializerValueJsonStringType extends Deserializer<JsonString> {

    static final Deserializer<JsonString> INSTANCE = new DeserializerValueJsonStringType();

    private DeserializerValueJsonStringType() {
    }

    @Override
    public JsonString stringValue(ParserContext uCtx) { return Json.createValue(uCtx.getParser().getString()); }

    @Override
    public JsonString numberValue(ParserContext uCtx) { return  Json.createValue(uCtx.getParser().getString());}

    @Override
    public JsonString trueValue(ParserContext uCtx) {
        return Json.createValue(uCtx.getParser().getString());
    }

    @Override
    public JsonString falseValue(ParserContext uCtx) {
        return Json.createValue(uCtx.getParser().getString());
    }

    @Override
    public JsonString nullValue(ParserContext uCtx) {
        return null;
    }
}
