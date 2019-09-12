package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.deserializer.ParserContext;

class DeserializerFromTrue {

    static final Map<Type, Deserializer<?>> BUILDERS = new HashMap<>(4);

    private static final String TRUE_STRING = Boolean.TRUE.toString();

    private DeserializerFromTrue() {
        throw new UnsupportedOperationException("Instances of DeserializerFromTrue are not allowed.");
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
            return Boolean.TRUE;
        }

    }

    static class AsString extends Deserializer<String> {

        static final AsString INSTANCE = new AsString();

        private AsString() {
        }

        @Override
        public String deserialize(ParserContext uCtx) {
            return TRUE_STRING;
        }

    }

}
