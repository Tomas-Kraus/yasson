package org.eclipse.yasson.internal.deserializer.deserializers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.yasson.internal.deserializer.ParserContext;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.customization.Customization;

/**
 * JSON object to Java {@code HashMap} deserializer.
 */
class ContainerHashMapFromObject extends ContainerObject<String, Object, Map<String, Object>> {

    /** Current value type (the same for all @code Map} values). */
    private Class<?> valueType;

    /** Map components customizations. */
    private Customization customization;

    /** Map components class model. */
    private ClassModel classModel;

    /** Target Java {@code HashMap} being built from JSON array. */
    private final HashMap<String, Object> map;

    /**
     * Get new instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param cm Java class model
     * @param valueType target Java value type of array elements
     * @return new instance of JSON array to Java {@code ArrayList} deserializer
     */
    static final ContainerHashMapFromObject newInstance(ClassModel cm, Class<?> keyType, Class<?> valueType) {
        return new ContainerHashMapFromObject(valueType, cm);
    }

    /**
     * Creates an instance of JSON array to Java {@code HashMap} deserializer.
     *
     * @param valueType target Java value type of Collection elements
     * @param classModel Java class model of the container type
     */
    private ContainerHashMapFromObject(final Class<?> valueType, final ClassModel classModel) {
        super(classModel);
        this.valueType = valueType;
        this.map = new HashMap<>();
    }

    public void start(ParserContext uCtx, Type type, ContainerArray<?, ?> parent) {
        super.start(uCtx, type, parent);
        classModel = uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(valueType);
        customization = classModel.getClassCustomization();
    }

    /**
     * Add last parsed JSON value to this {@code Map}.
     *
     * @param value value already converted to target type
     */
    @Override
    public final void addValue(Object value) {
        map.put(getKey(), value);
        resetKey();
    }

    /**
     * Return target Java {@code Map} value built from already added [key,value] pairs.
     *
     * @param deserialization context
     * @return target Java {@code List} value
     */
    @Override
    public final Map<String, Object> build() {
        return map;
    }

    /**
     * Get current key type.
     *
     * @return current key type as {@code String.class}
     */
    @Override
    public final Class<String> keyType() {
        return String.class;
    }

    /**
     * Get map value type.
     *
     * @return map value type
     */
    @Override
    public final Class<? extends Object> valueType() {
        return valueType;
    }

    /**
     * Get map value customizations.
     *
     * @return map value customizations
     */
    @Override
    public Customization valueCustomization() {
        return customization;
    }

}
