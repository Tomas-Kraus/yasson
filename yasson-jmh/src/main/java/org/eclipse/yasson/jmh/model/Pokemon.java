/*******************************************************************************
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * Tomas Kraus
 ******************************************************************************/
package org.eclipse.yasson.jmh.model;

import java.util.Objects;

/**
 * Serialization and de-serialization test model: {@code Pokemon}.
 */
public final class Pokemon {
	public String name;
	public String type;
	public int cp;

    public Pokemon() {
		this.name = null;
		this.type = null;
		this.cp = -1;
	}

	public Pokemon(String name, String type, int cp) {
		this.name = name;
		this.type = type;
		this.cp = cp;
	}

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Pokemon)) {
            return false;
        }
        Pokemon pokemon = (Pokemon)other;
        return cp == pokemon.cp
                && Objects.equals(name, pokemon.name)
                && Objects.equals(type, pokemon.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cp, name, type);
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append('"').append("name").append('"').append(':').append('"').append(name).append('"').append(',');
        sb.append('"').append("type").append('"').append(':').append('"').append(type).append('"').append(',');
        sb.append('"').append("cp").append('"').append(':').append(cp);
        sb.append('}');
        return sb.toString();
    }

}
