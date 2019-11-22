package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * Simple container deserializer.
 */
public class ContainerSimple extends Container<Void, Object, Object> {

    private Object value;

    @Override
    public void addValue(Object value) {
        this.value = value;

    }

    @Override
    public Object build() {
        return value;
    }

    /**
     * Get current key type.
     *
     * @return never returns anything
     * @throws IllegalStateException on any attempt to call this method
     */
    public Type keyType() {
        throw new IllegalStateException("Key type is not supported in simple container");
    }

    /**
     * Get current value type.
     *
     * @return never returns anything
     * @throws IllegalStateException on any attempt to call this method
     */
    public Type valueType() {
        throw new IllegalStateException("Value type is not supported in simple container");
    }

}
