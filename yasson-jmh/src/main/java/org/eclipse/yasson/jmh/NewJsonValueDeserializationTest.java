package org.eclipse.yasson.jmh;

import java.util.concurrent.TimeUnit;

import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.eclipse.yasson.jmh.model.PoJoWithAllTypes;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Tests for PoJo processing performance.
 */
@BenchmarkMode(Mode.Throughput)
@Timeout(time = 20)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NewJsonValueDeserializationTest {

    private Jsonb jsonbOld;
    private Jsonb jsonbNew;
    private String json;

    @Setup(Level.Trial)
    public void setUp() {
        jsonbNew = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));
        jsonbOld = JsonbBuilder.create(new JsonbConfig());
        json = "{\"jsonArray\":[{\"strVal\":\"string value\",\"numVal\":2.0,\"nullVal\":null,\"boolVal\":true,\"innerJsonObject\":{\"innerStr\":\"string val\",\"innerNum\":11.1},\"innerArrayObject\":[null,false,11,10,\"array STR value\",{\"innerStr\":\"string val\",\"innerNum\":11.1}]},true,{\"innerStr\":\"string val\",\"innerNum\":11.1},101.0,10],\"jsonObject\":{\"strVal\":\"string value\",\"numVal\":2.0,\"nullVal\":null,\"boolVal\":true,\"innerJsonObject\":{\"innerStr\":\"string val\",\"innerNum\":11.1},\"innerArrayObject\":[null,false,11,10,\"array STR value\",{\"innerStr\":\"string val\",\"innerNum\":11.1}]}}";

    }

    @Benchmark
    public Object testOld() {
        return jsonbOld.fromJson(json, JsonValue.class);
    }

    @Benchmark
    public Object testNew() {
        return jsonbNew.fromJson(json, JsonValue.class);
    }
}
