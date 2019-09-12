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
public class NewArrayDeserializerTest {

    private Jsonb jsonbOld;
    private Jsonb jsonbNew;

    private String json = "[" +
            "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29," +
            "30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59," +
            "60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89," +
            "90,91,92,93,94,95,96,97,98,99" +
            "]";

    @Setup(Level.Trial)
    public void setUp() {
        jsonbNew = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));
        jsonbOld = JsonbBuilder.create(new JsonbConfig());
    }

    @Benchmark
    public Object testOld() {
        return jsonbOld.fromJson(json, int[].class);
    }

    @Benchmark
    public Object testNew() {
        return jsonbNew.fromJson(json, int[].class);
    }

}
