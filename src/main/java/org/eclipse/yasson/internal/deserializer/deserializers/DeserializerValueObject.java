package org.eclipse.yasson.internal.deserializer.deserializers;

import java.math.BigDecimal;

import javax.json.stream.JsonParser;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Deserialize JSON {@code string} or {@code number} as {@link Object} value.
 */
public final class DeserializerValueObject extends Deserializer<Object> {

    static final Deserializer<Object> INSTANCE = new DeserializerValueObject();

    private DeserializerValueObject() {
    }

    @Override
    public Object deserialize(ParserContext uCtx) {
        return uCtx.getToken() == JsonParser.Event.VALUE_NUMBER
                ? new BigDecimal(uCtx.getParser().getString())
                : uCtx.getParser().getString();
    }

}
