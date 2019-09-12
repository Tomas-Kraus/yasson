package org.eclipse.yasson.serializers.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Expedition {

    private String name;
    private Trainer trainer;
    private List<Pokemon> pokemons;

    public Expedition() {
        this.name = null;
        this.trainer = null;
        this.pokemons = null;
    }

    public Expedition(String name, Trainer trainer, Pokemon... pokemons) {
        this.name = name;
        this.trainer = trainer;
        this.pokemons = Arrays.asList(pokemons);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Expedition)) {
            return false;
        }
        Expedition expedition = (Expedition)other;
        return Objects.equals(name, expedition.name)
                && Objects.equals(trainer, expedition.trainer)
                && Objects.equals(pokemons, expedition.pokemons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, trainer);
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append('"').append("name").append('"').append(':').append('"').append(name).append('"').append(',');
        sb.append('"').append("trainer").append('"').append(':').append(trainer.toJson()).append(',');
        sb.append('"').append("pokemons").append('"').append(':').append('[');
        boolean first = true;
        for (Pokemon pokemon : pokemons) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append(pokemon.toJson());
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }



}
