package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.deserializer.ParserContext;

class DeserializerFromFalse {

    static final Map<Type, Deserializer<?>> BUILDERS = new HashMap<>(4);

    private static final String FALSE_STRING = Boolean.FALSE.toString();

    private DeserializerFromFalse() {
        throw new UnsupportedOperationException("Instances of DeserializerFromFalse are not allowed.");
    }

    static {
        BUILDERS.put(boolean.class, AsBoolean.INSTANCE);
        BUILDERS.put(Boolean.class, AsBoolean.INSTANCE);
        BUILDERS.put(String.class, AsString.INSTANCE);
        BUILDERS.put(Object.class, AsBoolean.INSTANCE);
    }

    static class AsBoolean extends Deserializer<Boolean> {

        static final AsBoolean INSTANCE = new AsBoolean();

        private AsBoolean() {
        }

        @Override
        public Boolean deserialize(ParserContext uCtx) {
            return Boolean.FALSE;
        }

    }

    static class AsString extends Deserializer<String> {

        static final AsString INSTANCE = new AsString();

        private AsString() {
        }

        @Override
        public String deserialize(ParserContext uCtx) {
            return FALSE_STRING;
        }

    }

}
