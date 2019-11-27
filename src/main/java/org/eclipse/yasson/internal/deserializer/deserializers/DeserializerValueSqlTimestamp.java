package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

import java.sql.Timestamp;

public final class DeserializerValueSqlTimestamp extends Deserializer<Timestamp> {

    static final Deserializer<Timestamp> INSTANCE = new DeserializerValueSqlTimestamp();

    private DeserializerValueSqlTimestamp() {
    }

    @Override
    public Timestamp stringValue(ParserContext uCtx) { return new Timestamp(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public Timestamp numberValue(ParserContext uCtx) { return new Timestamp(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public Timestamp trueValue(ParserContext uCtx) { return new Timestamp(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public Timestamp falseValue(ParserContext uCtx) { return new Timestamp(Long.parseLong(uCtx.getParser().getString())); }

    @Override
    public Timestamp nullValue(ParserContext uCtx) { return new Timestamp(Long.parseLong(uCtx.getParser().getString())); }
}
