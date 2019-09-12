package org.eclipse.yasson.jmh;

import org.eclipse.yasson.jmh.model.CollectionsData;
import org.eclipse.yasson.jmh.model.ScalarData;
import org.openjdk.jmh.annotations.*;

import javax.json.bind.JsonbConfig;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;


/**
 * Tests for collections processing performance.
 */
@BenchmarkMode(Mode.Throughput)
@Timeout(time = 20)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NewDeserializerTest {

    private Jsonb jsonbOld;
    private Jsonb jsonbNew;

    private String json = "{" +
            "    \"first\": \"first data\"," +
            "    \"second\": 2," +
            "    \"third\": true," +
            "    \"fourth\": false," +
            "    \"fifth\": null," +
            "    \"sixth\": \"first data\"," +
            "    \"seventh\": 2," +
            "    \"eighth\": true," +
            "    \"ninth\": false," +
            "    \"tenth\": null," +
            "    \"eleventh\": \"first data\"," +
            "    \"twelfth\": 2," +
            "    \"thirteenth\": true," +
            "    \"fourteenth\": false," +
            "    \"fifteenth\": null," +
            "    \"sixteenth\": \"first data\"," +
            "    \"seventeenth\": 2," +
            "    \"eighteenth\": true," +
            "    \"nineteenth\": false," +
            "    \"twentieth\": null," +
            "    \"twenty-first\": \"first data\"," +
            "    \"twenty-second\": 2," +
            "    \"twenty-third\": true," +
            "    \"twenty-fourth\": false," +
            "    \"twenty-fifth\": null," +
            "    \"twenty-sixth\": \"first data\"," +
            "    \"twenty-seventh\": 2," +
            "    \"twenty-eighth\": true," +
            "    \"twenty-ninth\": false," +
            "    \"thirtieth\": null," +
            "    \"thirty-first\": \"first data\"," +
            "    \"thirty-second\": 2," +
            "    \"thirty-third\": true," +
            "    \"thirty-fourth\": false," +
            "    \"thirty-fifth\": null," +
            "    \"thirty-sixth\": \"first data\"," +
            "    \"thirty-seventh\": 2," +
            "    \"thirty-eighth\": true," +
            "    \"thirty-ninth\": false," +
            "    \"fortieth\": null," +
            "    \"forty-first\": \"first data\"," +
            "    \"forty-second\": 2," +
            "    \"forty-third\": true," +
            "    \"forty-fourth\": false," +
            "    \"forty-fifth\": null," +
            "    \"forty-sixth\": \"first data\"," +
            "    \"forty-seventh\": 2," +
            "    \"forty-eighth\": true," +
            "    \"forty-ninth\": false," +
            "    \"fiftieth\": null" +
            "}";

    @Setup(Level.Trial)
    public void setUp() {
        jsonbNew = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));
        jsonbOld = JsonbBuilder.create(new JsonbConfig());
    }

    @Benchmark
    public Object testOld() {
        return jsonbOld.fromJson(json, Object.class);
    }

    @Benchmark
    public Object testNew() {
        return jsonbNew.fromJson(json, Object.class);
    }

}
