package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

class DeserializerFromNull extends Deserializer<Void> {

    static final DeserializerFromNull INSTANCE = new DeserializerFromNull();

    private DeserializerFromNull() {
    }

    @Override
    public Void deserialize(ParserContext uCtx) {
        return null;
    }

}
