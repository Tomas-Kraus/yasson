package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;

import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * Simple container deserializer.
 */
public class ContainerSimple extends ContainerArray<Object, Object> {

    private Object value;

    /** Current value type (the same for all array elements). */
    private final Class<Object> valueType;

    /** Array components customizations. */
    private Customization customization;

    /**
     * Create s an instance of simple container deserializer.
     *
     * @param type container type
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    @SuppressWarnings("unchecked")
    public ContainerSimple(final Type type, final Class<?> valueType, ClassModel classModel) {
        super(classModel);
        setRuntimeType(type);
        this.valueType = (Class<Object>) valueType;
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
        return valueType;
    }

    @Override
    public Customization valueCustomization() {
        return customization;
    }

}
