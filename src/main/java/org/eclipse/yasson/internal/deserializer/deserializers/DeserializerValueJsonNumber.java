package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import javax.json.Json;
import javax.json.JsonNumber;

public final class DeserializerValueJsonNumber extends Deserializer<JsonNumber> {

    static final Deserializer<JsonNumber> INSTANCE = new DeserializerValueJsonNumber();

    private DeserializerValueJsonNumber(){
    }

    @Override
    public JsonNumber stringValue(ParserContext uCtx) { return Json.createValue(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public JsonNumber numberValue(ParserContext uCtx) { return  Json.createValue(Integer.parseInt(uCtx.getParser().getString()));}

    @Override
    public JsonNumber trueValue(ParserContext uCtx) { return Json.createValue(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public JsonNumber falseValue(ParserContext uCtx) { return Json.createValue(Integer.parseInt(uCtx.getParser().getString())); }

    @Override
    public JsonNumber nullValue(ParserContext uCtx) { return null; }
}
