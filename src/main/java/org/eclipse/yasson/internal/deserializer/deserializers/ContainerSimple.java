package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;

/**
 * Simple container deserializer.
 */
public class ContainerSimple extends ContainerArray<Object, Object> {

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
     * Get current value type.
     *
     * @return never returns anything
     * @throws IllegalStateException on any attempt to call this method
     */
    public Type valueType() {
        throw new IllegalStateException("Value type is not supported in simple container");
    }

}
