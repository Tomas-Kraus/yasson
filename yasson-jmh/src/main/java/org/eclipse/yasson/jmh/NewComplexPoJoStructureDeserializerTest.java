package org.eclipse.yasson.jmh;

import java.util.concurrent.TimeUnit;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.eclipse.yasson.jmh.model.Trainer;
import org.eclipse.yasson.jmh.model.Pokemon;
import org.eclipse.yasson.jmh.model.Expedition;
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
public class NewComplexPoJoStructureDeserializerTest {

        private Jsonb jsonbOld;
        private Jsonb jsonbNew;
        private String json;

        @Setup(Level.Trial)
        public void setUp() {
            jsonbNew = JsonbBuilder.create(new JsonbConfig().setProperty("experimental.deserializer", true));
            jsonbOld = JsonbBuilder.create(new JsonbConfig());
            Expedition src = new Expedition("Kanto",
                    new Trainer("Ash Ketchum", 12),
                    new Pokemon("Pikachu", "electric", 518),
                    new Pokemon("Squirtle", "water", 421));
            json = src.toJson();
        }

        @Benchmark
        public Object testOld() {
            return jsonbOld.fromJson(json, Expedition.class);
        }

        @Benchmark
        public Object testNew() {
            return jsonbNew.fromJson(json, Expedition.class);
        }

}
