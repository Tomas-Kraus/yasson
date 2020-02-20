package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;

import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Simple container deserializer.
 */
public class ContainerSimple extends ContainerArray<Object, Object> {

    private Object value;

    /** Array components customizations. */
    private final Customization customization;

    /**
     * Create s an instance of simple container deserializer.
     *
     * @param type target java value type
     * @param typeClass class of the container
     */
    @SuppressWarnings("unchecked")
    public ContainerSimple(final Type type, final Class<?> typeClass) {
        super((Class<Object>) typeClass);
        setRuntimeType(type);
        this.customization = null;
    }

    /**
     * Create s an instance of simple container deserializer.
     *
     * @param type target java value type
     * @param classModel Java class model of the container type
     */
    @SuppressWarnings("unchecked")
    public ContainerSimple(final Type type, final ClassModel classModel) {
        super((Class<Object>) classModel.getType());
        setRuntimeType(type);
        this.customization = classModel.getClassCustomization();
    }

    @Override
    public void addValue(Object value) {
        this.value = value;

    }

    @Override
    public Object build() {
        return value;
    }

    @Override
    public Type valueType() {
        return getRuntimeType();
    }

    @Override
    public Class<Object> valueClass() {
        return getContainerClass();
    }

    @Override
    public Customization valueCustomization() {
        return customization;
    }

}
